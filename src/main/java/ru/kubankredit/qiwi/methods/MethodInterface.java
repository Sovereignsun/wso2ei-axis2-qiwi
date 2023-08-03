package ru.kubankredit.qiwi.methods;

import org.apache.axiom.om.OMElement;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;

/**
 * <h1>Интерфейс обработчика методов для ПЦ операций</h1>
 * Интерфейс содержит метод process, который необходим для каждый операции
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @implNote Добавить метод process с @Override аннотацией при имплементации
 * @implSpec @Override process
 * @since 2023-07-24
 */
public interface MethodInterface {

    /**
     * Выполняет логику обработки требуемого метода
     *
     * @param omElement  Запрос в XML формате в виде OMElement из ПЦ
     * @param service_id Идентификатор сервиса в QIWI
     * @return Ответ в виде OMElement
     * @author Листопадов Александр Сергеевич
     * @since 2023-07-24
     */
    OMElement process(OMElement omElement, String service_id, String terminal_id) throws AbstractException;

}
