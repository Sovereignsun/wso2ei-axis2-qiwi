package ru.kubankredit.qiwi.serializers;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

import static ru.kubankredit.qiwi.serializers.XSLTRemoveNamespaces.REMOVE_XML_NAMESPACES;

/**
 * <h1>Класс форматирования XML</h1>
 * Содержит метод форматирования XML документа
 */
public class XMLBeautifier {

    /**
     * Метод позволяет форматировать XML документ и привести в читаемый вид
     *
     * @param xmlString Документ в формате XML в виде строки
     * @return Строка
     */
    public static String IndentXML(String xmlString) {

        try {
            // Create a new DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            // Create a DocumentBuilder to parse the XML string
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlString)));

            // Normalize the Document
            document.getDocumentElement().normalize();

            // Create a new Transformer to pretty print the XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            // Convert the Document to a DOMSource for transformation
            DOMSource source = new DOMSource(document);

            // Create a string writer to store the pretty printed XML
            StringWriter writer = new StringWriter();

            // Transform the DOMSource into a StreamResult
            StreamResult result = new StreamResult(writer);

            // Apply the transformation to pretty print the XML
            transformer.transform(source, result);

            // Get the formatted XML string
            return writer.toString();
        } catch (Exception e) {
            return xmlString;
        }
    }

    /**
     * Метод позволяет убрать все namespaces из XML документа
     *
     * @param xmlString Документ в формате XML в виде строки
     * @return Строка
     */
    public static String removeNamespaces(String xmlString) {
        try {
            // Create a new DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            // Create a DocumentBuilder to parse the XML string
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlString)));

            // Normalize the Document
            document.getDocumentElement().normalize();

            // Create a new Transformer to pretty print the XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            StreamSource xslt = new StreamSource(new StringReader(REMOVE_XML_NAMESPACES));
            Transformer transformer = transformerFactory.newTransformer(xslt);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            // Convert the Document to a DOMSource for transformation
            DOMSource source = new DOMSource(document);

            // Create a string writer to store the pretty printed XML
            StringWriter writer = new StringWriter();

            // Transform the DOMSource into a StreamResult
            StreamResult result = new StreamResult(writer);

            // Apply the transformation to pretty print the XML
            transformer.transform(source, result);

            // Get the formatted XML string
            return writer.toString();
        } catch (Exception e) {
            return xmlString;
        }
    }

}
