package ru.kubankredit.qiwi.serializers;

import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * <h1>Сериализитор ответов сервиса для XML данных</h1>
 * Содержит метод serializeXMLPayload, который подчищает XML и добавляет инструкции
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-12
 */
public class PayloadSerializer {

    /**
     * Метод позволяет форматировать и серилиазовать XML документ и преобразовать в строку
     *
     * @param document Документ XML
     * @return Строка
     * @throws Exception Исключение во время обработки
     */
    public String serializeXMLPayload(Document document) throws Exception {
        // Create Transformer
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // Set properties for formatting the XML
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "WINDOWS-1251");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        // Create StreamResult
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

        // Transform DOM to XML
        transformer.transform(new DOMSource(document), result);

        // Return the XML Document
        return writer.toString();
    }

}
