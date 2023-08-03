package ru.kubankredit.qiwi.dev.tele2;

import org.apache.axiom.om.OMElement;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;
import ru.kubankredit.qiwi.methods.main.addPayment.AddPaymentProcess;
import ru.kubankredit.qiwi.responses.UniformResponse;
import ru.kubankredit.qiwi.serializers.XMLBeautifier;

public class DevAddPaymentRequest {

    public static void main(String[] args) {

        final String QIWI_SERVICE_ID = "42";
        final String QIWI_TERMINAL_ID = "10788430";

        final UniformResponse uniformResponse = new UniformResponse();
        final String thisClassName = DevAddPaymentRequest.class.getName();
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        Core.lm.generateGUID();

        OMElement response = null;

        OMElement requestData = Tele2AddPaymentRequest.getRequest();

        Core.lm.debug(thisClassName, methodName, "RequestData: \n" + requestData);
        Core.lm.debug(thisClassName, methodName, "Qiwi Provider Service ID: " + QIWI_SERVICE_ID);

        // Инициализируем обьект Process для текущей операции
        AddPaymentProcess addPaymentProcess = new AddPaymentProcess();

        try {
            // Вызываем метод для исполнения логики для текущей операции
            addPaymentProcess.process(requestData, QIWI_SERVICE_ID, QIWI_TERMINAL_ID);
        } catch (AbstractException e) {
            response = uniformResponse.respond(UniformResponse.OperationName.AddPayment, e.getMessage(), e.getDetailMessage(), false);
            Core.lm.debug(thisClassName, methodName, "Response: \n" + XMLBeautifier.IndentXML(response.toString()));
        }

        response = uniformResponse.respond(UniformResponse.OperationName.AddPayment, null, null, true);

        Core.lm.debug(thisClassName, methodName, "Response: \n" + XMLBeautifier.IndentXML(response.toString()));
    }

}
