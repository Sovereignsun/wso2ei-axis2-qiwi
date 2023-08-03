package ru.kubankredit.qiwi.db.methods;

import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.db.ConnectionManager;
import ru.kubankredit.qiwi.db.entities.setOnlineStatus.SetOnlineStatusRequest;
import ru.kubankredit.qiwi.db.entities.setOnlineStatus.SetOnlineStatusResponse;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

/**
 * <h1>Установить онлайн статус платежа</h1>
 * Класс для вызова метода установки онлайн статуса платежа в ПЦ
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @apiNote Метод <b>execute</b> можно вызывать напрямую из класса
 * @since 2023-07-27
 */
public class SetOnlineStatus {

    protected static final String thisClassName = SetOnlineStatus.class.getName();

    /**
     * Установить онлайн статус платежа
     *
     * @param setStatusRequest Объект содержащий данные для вызова метода
     * @return Объект с результатом выполнения метода
     * @apiNote Успешно = 0, не успех > 0
     */
    public static SetOnlineStatusResponse execute(SetOnlineStatusRequest setStatusRequest) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        SetOnlineStatusResponse setStatusResponse = new SetOnlineStatusResponse();
        try (ConnectionManager connectionManager = new ConnectionManager()) {

            Connection connection = connectionManager.getConnection();

            // Create a CallableStatement object
            String sql = "{? = call PMNT$PAYMENTS_LIB.SET_ONLINE_STATUS(?, ?, ?, ?, ?)}";
            CallableStatement statement = connection.prepareCall(sql);

            // Register the output parameter
            statement.registerOutParameter(1, Types.NUMERIC);

            statement.setString(2, setStatusRequest.getSessionId());
            statement.setInt(3, setStatusRequest.getPaymentId());
            statement.setInt(4, setStatusRequest.getOnlineStatus().getStatus());
            statement.setString(5, setStatusRequest.getErrorCode());
            statement.setString(6, setStatusRequest.getErrorText());

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
        SetOnlineStatusRequest setOnlineStatusRequest = new SetOnlineStatusRequest();

        setOnlineStatusRequest.setSessionId("4197C302-E0B5-4C2E-B084-5E3996227DED");
        setOnlineStatusRequest.setPaymentId(171681);
        setOnlineStatusRequest.setOnlineStatus(EnumOnlineStatus.ONLINE_PMNT_GOOD);
        setOnlineStatusRequest.setErrorText("Test");
        setOnlineStatusRequest.setErrorCode("0");

        SetOnlineStatusResponse setOnlineStatusResponse = SetOnlineStatus.execute(setOnlineStatusRequest);
        System.out.println("Execute Result = " + setOnlineStatusResponse.getResult());
    }*/

}
