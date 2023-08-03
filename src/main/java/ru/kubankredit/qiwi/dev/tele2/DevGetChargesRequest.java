package ru.kubankredit.qiwi.dev.tele2;

import org.apache.axiom.om.OMElement;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.methods.main.getCharges.GetChargesProcess;
import ru.kubankredit.qiwi.responses.UniformResponse;
import ru.kubankredit.qiwi.serializers.XMLBeautifier;

public class DevGetChargesRequest {

    public static void main(String[] args) {

        final String QIWI_SERVICE_ID = "42";
        final String QIWI_TERMINAL_ID = "10788430";

        final UniformResponse uniformResponse = new UniformResponse();
        final String thisClassName = DevGetChargesRequest.class.getName();
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        Core.lm.generateGUID();

        OMElement response = null;

        OMElement requestData = Tele2GetChargesRequest.getRequest();

        Core.lm.debug(thisClassName, methodName, "RequestData: \n" + requestData);
        Core.lm.debug(thisClassName, methodName, "Qiwi Provider Service ID: " + QIWI_SERVICE_ID);

        // Инициализируем обьект для текущей операции
        GetChargesProcess getChargesProcess = new GetChargesProcess();

        // Вызываем метод для исполнения логики для текущей оперsации
        OMElement processResponse = null;
        try {
            processResponse = getChargesProcess.process(requestData, QIWI_SERVICE_ID, QIWI_TERMINAL_ID);
            if (processResponse != null) {
                response = uniformResponse.respond(UniformResponse.OperationName.GetCharges, null, null, true, processResponse);
            } else {
                response = uniformResponse.respond(UniformResponse.OperationName.GetCharges, null, null, true);
            }
        } catch (Exception e) {
            response = uniformResponse.respond(UniformResponse.OperationName.GetCharges, "Error", e.getMessage(), false);
        }

        Core.lm.debug(thisClassName, methodName, "Response: \n" + XMLBeautifier.IndentXML(response.toString()));
    }

}
