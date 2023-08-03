package ru.kubankredit.qiwi.db.methods;

import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.db.ConnectionManager;
import ru.kubankredit.qiwi.db.entities.setExternalId.SetExternalIdRequest;
import ru.kubankredit.qiwi.db.entities.setExternalId.SetExternalIdResponse;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

/**
 * <h1>Установить внешний идентификатор платежа из внешней системы</h1>
 * Класс для вызова метода установки внешнего идентификатор платежа в ПЦ
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @apiNote Метод <b>execute</b> можно вызывать напрямую из класса
 * @since 2023-07-27
 */
public class SetExternalId {

    protected static final String thisClassName = SetExternalId.class.getName();

    /**
     * Установить внешний идентификатор платежа из внешней системы
     *
     * @param setStatusRequest Объект содержащий данные для вызова метода
     * @return Объект с результатом выполнения метода
     * @apiNote Успешно = 0, не успех = 1
     */
    public static SetExternalIdResponse execute(SetExternalIdRequest setStatusRequest) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        SetExternalIdResponse setStatusResponse = new SetExternalIdResponse();
        try (ConnectionManager connectionManager = new ConnectionManager()) {

            Connection connection = connectionManager.getConnection();

            // Create a CallableStatement object
            String sql = "{? = call PMNT$PAYMENTS_LIB.SET_EXT_ID_BY_ID(?, ?, ?)}";
            CallableStatement statement = connection.prepareCall(sql);

            // Register the output parameter
            statement.registerOutParameter(1, Types.NUMERIC);

            statement.setString(2, setStatusRequest.getSessionId());
            statement.setInt(3, setStatusRequest.getPaymentId());
            statement.setString(4, setStatusRequest.getExtId());

            // Call the method
            statement.execute();

            // Get the result
            int returnValue = statement.getInt(1);

            setStatusResponse.setResult(returnValue);

            // Close the statement
            statement.close();
        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
            setStatusResponse.setResult(1);
        }

        return setStatusResponse;
    }

    /*public static void main(String[] args) {
        SetExternalIdRequest setExternalId = new SetExternalIdRequest();

        setExternalId.setSessionId("4197C302-E0B5-4C2E-B084-5E3996227DED");
        setExternalId.setPaymentID(171681);
        setExternalId.setExtId("135234352135125634621235136534621");

        SetExternalIdResponse setStatusResponse = SetExternalId.execute(setExternalId);
        System.out.println("Execute Result = " + setStatusResponse.getResult());
    }*/

}
