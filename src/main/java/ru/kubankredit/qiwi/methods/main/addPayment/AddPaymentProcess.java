package ru.kubankredit.qiwi.methods.main.addPayment;

import org.apache.axiom.om.OMElement;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.enums.EnumQueueType;
import ru.kubankredit.qiwi.core.enums.EnumResult;
import ru.kubankredit.qiwi.core.enums.EnumStatus;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;
import ru.kubankredit.qiwi.core.http.HTTPModule;
import ru.kubankredit.qiwi.emulators.QiwiHTTPEmulator;
import ru.kubankredit.qiwi.methods.MethodInterface;
import ru.kubankredit.qiwi.methods.gates.gatepayments.echoGetFieldsPayments.EchoGetFieldsPaymentsResponse;
import ru.kubankredit.qiwi.methods.main.GetPaymentFields;
import ru.kubankredit.qiwi.methods.main.SetExternalIdDB;
import ru.kubankredit.qiwi.methods.main.SetOnlineStatusDB;
import ru.kubankredit.qiwi.methods.main.getStatus.GetPaymentStatusRequest;
import ru.kubankredit.qiwi.methods.qiwi.authorizePayment.AuthorizePaymentPayload;
import ru.kubankredit.qiwi.methods.qiwi.authorizePayment.AuthorizePaymentResponse;
import ru.kubankredit.qiwi.methods.qiwi.authorizePayment.AuthorizePaymentResponseParser;
import ru.kubankredit.qiwi.methods.qiwi.confirmPayment.ConfirmPaymentPayload;
import ru.kubankredit.qiwi.methods.qiwi.confirmPayment.ConfirmPaymentResponse;
import ru.kubankredit.qiwi.methods.qiwi.confirmPayment.ConfirmPaymentResponseParser;
import ru.kubankredit.qiwi.methods.qiwi.getPaymentStatus.GetPaymentStatusPayload;

/**
 * <h1>Класс обработки операции создания платежа</h1>
 * Содержит интерфейсный метод для выполнения требуемой логики для операции
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-24
 */
public class AddPaymentProcess implements MethodInterface {

    final protected String thisClassName = this.getClass().getName();

    @Override
    public OMElement process(OMElement omElement, String service_id, String terminal_id) throws AbstractException {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        // Create a new thread for executing process task asynchronously
        Thread processThread = new Thread(() -> {
            // Perform the add payment task
            PerformAddPaymentTask(omElement, service_id, terminal_id);
        });

        // Start the new thread
        processThread.start();

        return null;
    }

    private void PerformAddPaymentTask(OMElement omElement, String service_id, String terminal_id) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        try {

            final AddPaymentRequestParser addPaymentRequestParser = new AddPaymentRequestParser();
            final AddPaymentXMLParser addPaymentXmlParser = new AddPaymentXMLParser();
            final AuthorizePaymentResponseParser authorizePaymentResponseParser = new AuthorizePaymentResponseParser();
            final ConfirmPaymentResponseParser confirmPaymentResponseParser = new ConfirmPaymentResponseParser();

            AddPaymentRequest addPaymentRequest = addPaymentRequestParser.parseRequest(omElement);
            addPaymentRequest = addPaymentXmlParser.ParseRequest(addPaymentRequest);

            Core.setCurrentSessionId(addPaymentRequest.getDataValueByName("SESSION_ID"));

            addPaymentRequest.setTerminal(terminal_id);
            addPaymentRequest.setServiceId(service_id);

            AuthorizePaymentPayload authorizePaymentPayload = new AuthorizePaymentPayload();

            EchoGetFieldsPaymentsResponse echoGetFieldsPaymentsResponse = new GetPaymentFields().getPaymentsFields(addPaymentRequest);

            String authorizePaymentPayloadString = authorizePaymentPayload.buildXMLPayload(addPaymentRequest, echoGetFieldsPaymentsResponse);

            Core.lm.trace(thisClassName, methodName, "AuthorizePaymentPayloadString: " + authorizePaymentPayloadString);

            String qiwiResponse;

            if (!Core.DeveloperMode) {
                HTTPModule httpModule = new HTTPModule();
                qiwiResponse = httpModule.HTTPPost(authorizePaymentPayloadString, true);
            } else {
                qiwiResponse = new QiwiHTTPEmulator().authorizePayment(false);
            }

            AuthorizePaymentResponse authorizePaymentResponse = authorizePaymentResponseParser.parseResponse(qiwiResponse);

            EnumResult enumResult = EnumResult.getResultByCode(authorizePaymentResponse.getResult());
            EnumStatus enumStatus = EnumStatus.getStatusByCode(authorizePaymentResponse.getStatus());

            Core.lm.info(thisClassName, methodName, "Результата обработки от Киви: " + enumResult.getCode() + " - " + enumResult.getText());
            Core.lm.info(thisClassName, methodName, "Описание результата обработки от Киви: " + enumResult.getComment());
            Core.lm.info(thisClassName, methodName, "Статус ответа от Киви: " + enumStatus.getName() + " - " + enumStatus.getIsFinal());

            if (authorizePaymentResponse.isFatal() || enumResult.getSeverity() == 1) {
                SetOnlineStatusDB.setOnlineStatus(
                        addPaymentRequest.findDataByName("SESSION_ID").getValue(),
                        Integer.parseInt(addPaymentRequest.findAttributeByName("K_ID").getValue()),
                        enumStatus.getEnumOnlineStatus(),
                        enumResult.getComment(),
                        String.valueOf(enumResult.getCode())
                );
                throw new AbstractException(enumResult.getText(), enumResult.getComment(), enumResult.getCode());
            }

            SetExternalIdDB.setExternalId(
                    addPaymentRequest.findDataByName("SESSION_ID").getValue(),
                    Integer.parseInt(addPaymentRequest.findAttributeByName("K_ID").getValue()),
                    authorizePaymentResponse.getUid()
            );

            ConfirmPaymentPayload confirmPaymentPayload = new ConfirmPaymentPayload();

            String confirmPaymentPayloadString = confirmPaymentPayload.buildXMLPayload(addPaymentRequest);

            Core.lm.trace(thisClassName, methodName, "ConfirmPaymentPayloadString: " + confirmPaymentPayloadString);

            if (!Core.DeveloperMode) {
                HTTPModule httpModule = new HTTPModule();
                qiwiResponse = httpModule.HTTPPost(confirmPaymentPayloadString, true);
            } else {
                qiwiResponse = new QiwiHTTPEmulator().confirmPayment(false);
            }

            ConfirmPaymentResponse confirmPaymentResponse = confirmPaymentResponseParser.parseResponse(qiwiResponse);

            enumResult = EnumResult.getResultByCode(confirmPaymentResponse.getResult());
            enumStatus = EnumStatus.getStatusByCode(confirmPaymentResponse.getStatus());

            Core.lm.info(thisClassName, methodName, "Результата обработки от Киви: " + enumResult.getCode() + " - " + enumResult.getText());
            Core.lm.info(thisClassName, methodName, "Описание результата обработки от Киви: " + enumResult.getComment());
            Core.lm.info(thisClassName, methodName, "Статус ответа от Киви: " + enumStatus.getName() + " - " + enumStatus.getIsFinal());

            if (confirmPaymentResponse.isFatal() || enumResult.getSeverity() == 1) {
                SetOnlineStatusDB.setOnlineStatus(
                        addPaymentRequest.findDataByName("SESSION_ID").getValue(),
                        Integer.parseInt(addPaymentRequest.findAttributeByName("K_ID").getValue()),
                        enumStatus.getEnumOnlineStatus(),
                        enumResult.getComment(),
                        String.valueOf(enumResult.getCode())
                );
                throw new AbstractException(enumResult.getText(), enumResult.getComment(), enumResult.getCode());
            }

            if (enumStatus == EnumStatus.COMPLETED) {
                SetOnlineStatusDB.setOnlineStatus(
                        addPaymentRequest.findDataByName("SESSION_ID").getValue(),
                        Integer.parseInt(addPaymentRequest.findAttributeByName("K_ID").getValue()),
                        enumStatus.getEnumOnlineStatus(),
                        enumResult.getComment(),
                        String.valueOf(enumResult.getCode())
                );
            }

            if (!enumStatus.isFinal()) {
                GetPaymentStatusRequest getPaymentStatusRequest = new GetPaymentStatusRequest();
                getPaymentStatusRequest.setTerminal(terminal_id);
                getPaymentStatusRequest.setServiceId(service_id);
                getPaymentStatusRequest.setPmntId(addPaymentRequest.findAttributeByName("K_ID").getValue());
                getPaymentStatusRequest.setExtId(authorizePaymentResponse.getUid());

                GetPaymentStatusPayload getPaymentStatusPayload = new GetPaymentStatusPayload();

                String getPaymentStatusPayloadString = getPaymentStatusPayload.buildXMLPayload(getPaymentStatusRequest);

                Core.activeMQCore.addToQueue(getPaymentStatusPayloadString, EnumQueueType.GET_PAYMENT_STATUS);
            }

        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "PerformAddPaymentTask Exception: " + e.getMessage());
        }
    }

}
