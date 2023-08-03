package ru.kubankredit.qiwi.methods.main.possPayment;

import org.apache.axiom.om.OMElement;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.enums.EnumResult;
import ru.kubankredit.qiwi.core.enums.EnumStatus;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;
import ru.kubankredit.qiwi.core.http.HTTPModule;
import ru.kubankredit.qiwi.emulators.QiwiHTTPEmulator;
import ru.kubankredit.qiwi.methods.MethodInterface;
import ru.kubankredit.qiwi.methods.gates.gatepayments.echoGetFieldsPayments.EchoGetFieldsPaymentsResponse;
import ru.kubankredit.qiwi.methods.main.GetPaymentFields;
import ru.kubankredit.qiwi.methods.qiwi.checkPaymentRequisites.CheckPaymentRequisitesPayload;
import ru.kubankredit.qiwi.methods.qiwi.checkPaymentRequisites.CheckPaymentRequisitesResponse;
import ru.kubankredit.qiwi.methods.qiwi.checkPaymentRequisites.CheckPaymentRequisitesResponseParser;

/**
 * <h1>Класс обработки операции предварительной проверки возможности создания платежа</h1>
 * Содержит интерфейсный метод для выполнения требуемой логики для операции
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-24
 */
public class PossPaymentProcess implements MethodInterface {

    final protected String thisClassName = this.getClass().getName();

    @Override
    public OMElement process(OMElement omElement, String service_id, String terminal_id) throws AbstractException {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        final PossPaymentRequestParser possPaymentRequestParser = new PossPaymentRequestParser();
        final PossPaymentXMLParser possPaymentXmlParser = new PossPaymentXMLParser();
        final CheckPaymentRequisitesResponseParser checkPaymentRequisitesResponseParser = new CheckPaymentRequisitesResponseParser();
        final PossPaymentXMLPayload possPaymentXmlPayload = new PossPaymentXMLPayload();

        PossPaymentRequest possPaymentRequest = possPaymentRequestParser.parseRequest(omElement);
        possPaymentRequest = possPaymentXmlParser.ParseRequest(possPaymentRequest);

        Core.setCurrentSessionId(possPaymentRequest.getDataValueByName("SESSION_ID"));

        possPaymentRequest.setTerminal(terminal_id);
        possPaymentRequest.setServiceId(service_id);

        CheckPaymentRequisitesPayload checkPaymentRequisitesPayload = new CheckPaymentRequisitesPayload();

        EchoGetFieldsPaymentsResponse echoGetFieldsPaymentsResponse = new GetPaymentFields().getPaymentsFields(possPaymentRequest);

        String XMLPayloadString = checkPaymentRequisitesPayload.buildXMLPayload(possPaymentRequest, echoGetFieldsPaymentsResponse);

        Core.lm.trace(thisClassName, methodName, "XMLPayloadString: " + XMLPayloadString);

        String QiwiResponse;

        if (!Core.DeveloperMode) {
            HTTPModule httpModule = new HTTPModule();
            QiwiResponse = httpModule.HTTPPost(XMLPayloadString, true);
        } else {
            QiwiResponse = new QiwiHTTPEmulator().checkPaymentRequisites(false);
        }

        CheckPaymentRequisitesResponse checkPaymentRequisitesResponse = checkPaymentRequisitesResponseParser.parseResponse(QiwiResponse);

        EnumResult enumResult = EnumResult.getResultByCode(checkPaymentRequisitesResponse.getResult());
        EnumStatus enumStatus = EnumStatus.getStatusByCode(checkPaymentRequisitesResponse.getStatus());

        Core.lm.info(thisClassName, methodName, "Результата обработки от Киви: " + enumResult.getCode() + " - " + enumResult.getText());
        Core.lm.info(thisClassName, methodName, "Описание результата обработки от Киви: " + enumResult.getComment());
        Core.lm.info(thisClassName, methodName, "Статус ответа от Киви: " + enumStatus.getName() + " - " + enumStatus.getIsFinal());

        if (checkPaymentRequisitesResponse.isFatal() || enumResult.getSeverity() == 1) {
            throw new AbstractException(enumResult.getText(), enumResult.getComment(), enumResult.getCode());
        }

        return possPaymentXmlPayload.formPayload(possPaymentRequest, checkPaymentRequisitesResponse);
    }

}
