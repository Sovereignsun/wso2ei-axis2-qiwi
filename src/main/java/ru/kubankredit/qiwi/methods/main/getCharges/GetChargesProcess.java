package ru.kubankredit.qiwi.methods.main.getCharges;

import org.apache.axiom.om.OMElement;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.enums.EnumResult;
import ru.kubankredit.qiwi.core.enums.EnumStatus;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;
import ru.kubankredit.qiwi.core.http.HTTPModule;
import ru.kubankredit.qiwi.emulators.QiwiHTTPEmulator;
import ru.kubankredit.qiwi.methods.MethodInterface;
import ru.kubankredit.qiwi.methods.gates.gatecontract.echoGetFieldsReestrChrg.EchoGetFieldsReestrChrgResponse;
import ru.kubankredit.qiwi.methods.main.GetReestrChrgFields;
import ru.kubankredit.qiwi.methods.qiwi.checkPaymentRequisites.CheckPaymentRequisitesPayload;
import ru.kubankredit.qiwi.methods.qiwi.checkPaymentRequisites.CheckPaymentRequisitesResponse;
import ru.kubankredit.qiwi.methods.qiwi.checkPaymentRequisites.CheckPaymentRequisitesResponseParser;

/**
 * <h1>Класс обработки операции запроса начислений</h1>
 * Содержит интерфейсный метод для выполнения требуемой логики для операции
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-24
 */
public class GetChargesProcess implements MethodInterface {

    final protected String thisClassName = this.getClass().getName();

    @Override
    public OMElement process(OMElement omElement, String service_id, String terminal_id) throws AbstractException {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        final GetChargesRequestParser getChargesRequestParser = new GetChargesRequestParser();
        final GetChargesXMLParser getChargesXmlParser = new GetChargesXMLParser();
        final CheckPaymentRequisitesResponseParser checkPaymentRequisitesResponseParser = new CheckPaymentRequisitesResponseParser();
        final GetChargesXMLPayload getChargesXmlPayload = new GetChargesXMLPayload();

        GetChargesRequest getChargesRequest = getChargesRequestParser.parseRequest(omElement);
        getChargesRequest = getChargesXmlParser.ParseRequest(getChargesRequest);

        Core.setCurrentSessionId(getChargesRequest.getDataValueByName("SESSION_ID"));

        getChargesRequest.setTerminal(terminal_id);
        getChargesRequest.setServiceId(service_id);

        CheckPaymentRequisitesPayload checkPaymentRequisitesPayload = new CheckPaymentRequisitesPayload();

        EchoGetFieldsReestrChrgResponse echoGetFieldsReestrChrgResponse = new GetReestrChrgFields().getReestrChrgFields(getChargesRequest);

        String XMLPayloadString = checkPaymentRequisitesPayload.buildXMLPayload(getChargesRequest, echoGetFieldsReestrChrgResponse);

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

        return getChargesXmlPayload.formPayload(getChargesRequest, checkPaymentRequisitesResponse);
    }


}
