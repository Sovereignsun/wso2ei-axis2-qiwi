package ru.kubankredit.qiwi.methods.main;

import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.db.entities.setExternalId.SetExternalIdRequest;
import ru.kubankredit.qiwi.db.entities.setExternalId.SetExternalIdResponse;
import ru.kubankredit.qiwi.db.methods.SetExternalId;

public class SetExternalIdDB {

    @SuppressWarnings("UnusedReturnValue")
    public static Integer setExternalId(String sessionId, Integer pmntId, String extId) {
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        final String thisClassName = SetExternalIdDB.class.getName();

        SetExternalIdRequest setExternalIdRequest = new SetExternalIdRequest();

        setExternalIdRequest.setSessionId(sessionId);
        setExternalIdRequest.setPaymentId(pmntId);
        setExternalIdRequest.setExtId(extId);

        Core.lm.info(thisClassName, methodName, "setExternalIdRequest: \n" + setExternalIdRequest);

        SetExternalIdResponse setExternalIdResponse = SetExternalId.execute(setExternalIdRequest);
        Core.lm.info(thisClassName, methodName, "Execute Result = " + setExternalIdResponse.getResult());

        return setExternalIdResponse.getResult();
    }
}
