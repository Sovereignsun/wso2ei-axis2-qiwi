package ru.kubankredit.qiwi.db;

import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;

import java.sql.*;

/**
 * <h1>Менеджер соединения с БД Oracle</h1>
 * Класс для организации соединения с базой данных Oracle в удобном JDBC формате
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @apiNote Имеет имплементацию с AutoCloseable и может использоваться в try-with-resources
 * @since 2023-07-27
 */
public class ConnectionManager implements AutoCloseable {

    protected final String thisClassName = this.getClass().getName();
    private final Connection connection;

    /**
     * Конструктор соединения с БД Oracle
     *
     * @throws AbstractException Абстрактное исключение, объединяющая в себе несколько ошибок
     */
    public ConnectionManager() throws AbstractException {

        String methodName = new Object() {
        }.getClass().getName();

        try {
            // Get Oracle connection settings
            String url = Core.settings.getOracleSettings().getUrl();
            String username = Core.settings.getOracleSettings().getUsername();
            String password = Core.settings.getOracleSettings().getPassword();

            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Connect to the database
            connection = DriverManager.getConnection(url, username, password);

            DatabaseMetaData metaData = connection.getMetaData();
            Core.lm.trace(thisClassName, methodName, "Current schema name: " + metaData.getUserName());

        } catch (SQLException e) {
            throw new AbstractException(e.getMessage(), e.getSQLState());
        } catch (ClassNotFoundException e) {
            throw new AbstractException(e.getMessage(), "ClassNotFoundException");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Метод позволяет пересесть в базе данных на другую схему
     *
     * @param schemaName Наименование схемы, на которую необходимо пересесть
     * @apiNote Убедитесь, что схема присутствует в подключенной БД
     */
    public void changeCurrentSchema(String schemaName) throws AbstractException {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            String currentSchema = metaData.getUserName();

            // Create a Statement object
            Statement statement = connection.createStatement();

            // Call a method from a package
            statement.execute("ALTER SESSION SET CURRENT_SCHEMA=" + schemaName);

            Core.lm.debug(thisClassName, methodName, "Current schema changed from \"" + currentSchema + "\" to \"" + metaData.getUserName() + "\"");

            // Close the resources
            statement.close();
        } catch (SQLException e) {
            throw new AbstractException(e.getMessage(), e.getSQLState());
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
