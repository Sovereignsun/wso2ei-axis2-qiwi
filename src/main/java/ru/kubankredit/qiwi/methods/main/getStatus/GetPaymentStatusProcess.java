package ru.kubankredit.qiwi.methods.main.getStatus;

import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.enums.EnumResult;
import ru.kubankredit.qiwi.core.enums.EnumStatus;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;
import ru.kubankredit.qiwi.core.http.HTTPModule;
import ru.kubankredit.qiwi.emulators.QiwiHTTPEmulator;
import ru.kubankredit.qiwi.methods.main.SetOnlineStatusDB;
import ru.kubankredit.qiwi.methods.qiwi.getPaymentStatus.GetPaymentStatusResponse;
import ru.kubankredit.qiwi.methods.qiwi.getPaymentStatus.GetPaymentStatusResponseParser;
import ru.kubankredit.qiwi.methods.qiwi.getPaymentStatus.GetPaymentStatusXMLParser;

public class GetPaymentStatusProcess {

    final protected String thisClassName = this.getClass().getName();

    public void process(String xmlRequest) throws AbstractException {

        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        GetPaymentStatusXMLParser getPaymentStatusXMLParser = new GetPaymentStatusXMLParser();
        GetPaymentStatusRequest getPaymentStatusRequest = getPaymentStatusXMLParser.parseResponse(xmlRequest);
        GetPaymentStatusResponseParser getPaymentStatusResponseParser = new GetPaymentStatusResponseParser();

        String qiwiResponse;

        if (!Core.DeveloperMode) {
            HTTPModule httpModule = new HTTPModule();
            qiwiResponse = httpModule.HTTPPost(xmlRequest, true);
        } else {
            qiwiResponse = new QiwiHTTPEmulator().getPaymentStatus(false, getPaymentStatusRequest.getPmntId());
        }

        GetPaymentStatusResponse getPaymentStatusResponse = getPaymentStatusResponseParser.parseResponse(qiwiResponse);

        EnumResult enumResult = EnumResult.getResultByCode(getPaymentStatusResponse.getResult());
        EnumStatus enumStatus = EnumStatus.getStatusByCode(getPaymentStatusResponse.getStatus());

        Core.lm.info(thisClassName, methodName, "Результата обработки от Киви: " + enumResult.getCode() + " - " + enumResult.getText());
        Core.lm.info(thisClassName, methodName, "Описание результата обработки от Киви: " + enumResult.getComment());
        Core.lm.info(thisClassName, methodName, "Статус ответа от Киви: " + enumStatus.getName() + " - " + enumStatus.getIsFinal());

        if (getPaymentStatusResponse.isFatal() || enumResult.getSeverity() == 1) {
            SetOnlineStatusDB.setOnlineStatus(
                    Core.getSessionId().get(),
                    Integer.parseInt(getPaymentStatusRequest.getPmntId()),
                    enumStatus.getEnumOnlineStatus(),
                    enumResult.getComment(),
                    String.valueOf(enumResult.getCode())
            );
            throw new AbstractException(enumResult.getText(), enumResult.getComment(), enumResult.getCode());
        }

        if (enumStatus == EnumStatus.COMPLETED) {
            SetOnlineStatusDB.setOnlineStatus(
                    Core.getSessionId().get(),
                    Integer.parseInt(getPaymentStatusRequest.getPmntId()),
                    enumStatus.getEnumOnlineStatus(),
                    enumResult.getComment(),
                    String.valueOf(enumResult.getCode())
            );
        } else {
            throw new AbstractException(enumStatus.getName(), enumResult.getComment(), enumResult.getCode());
        }
    }
}
