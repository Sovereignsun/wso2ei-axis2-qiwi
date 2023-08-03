package ru.kubankredit.qiwi.methods.main;

import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.enums.EnumOnlineStatus;
import ru.kubankredit.qiwi.db.entities.setOnlineStatus.SetOnlineStatusRequest;
import ru.kubankredit.qiwi.db.entities.setOnlineStatus.SetOnlineStatusResponse;
import ru.kubankredit.qiwi.db.methods.SetOnlineStatus;

public class SetOnlineStatusDB {

    @SuppressWarnings("UnusedReturnValue")
    public static Integer setOnlineStatus(String sessionId, Integer pmntId, EnumOnlineStatus enumOnlineStatus, String errorText, String errorCode) {
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        final String thisClassName = SetOnlineStatusDB.class.getName();

        SetOnlineStatusRequest setStatusRequest = new SetOnlineStatusRequest();

        setStatusRequest.setSessionId(sessionId);
        setStatusRequest.setPaymentId(pmntId);
        setStatusRequest.setOnlineStatus(enumOnlineStatus);
        setStatusRequest.setErrorText(errorText);
        setStatusRequest.setErrorCode(errorCode);

        Core.lm.info(thisClassName, methodName, "setOnlineStatus: \n" + setStatusRequest);

        SetOnlineStatusResponse setStatusResponse = SetOnlineStatus.execute(setStatusRequest);
        Core.lm.info(thisClassName, methodName, "Execute Result = " + setStatusResponse.getResult());

        return setStatusResponse.getResult();
    }
}
