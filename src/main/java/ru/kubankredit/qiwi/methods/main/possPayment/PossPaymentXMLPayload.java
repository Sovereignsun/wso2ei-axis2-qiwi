package ru.kubankredit.qiwi.methods.main.possPayment;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMXMLBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;
import ru.kubankredit.qiwi.methods.qiwi.checkPaymentRequisites.CheckPaymentRequisitesResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.List;


/**
 * <h1>Класс формирования данных для внесения в xml элемент ответа на запрос GetCharges</h1>
 * Содержит метод formPayload для формирования списка полей
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-25
 */
public class PossPaymentXMLPayload {

    /**
     * Сформировать Payload для PossPayment для вставки в элемент XML
     *
     * @param possPaymentRequest             Объект PossPaymentRequest
     * @param checkPaymentRequisitesResponse Объект CheckPaymentRequisitesResponse
     * @return OMElement с XML данными
     * @throws AbstractException Исключение обёртка других исключений
     * @author Листопадов Александр Сергеевич
     * @since 2023-07-25
     */
    public OMElement formPayload(PossPaymentRequest possPaymentRequest, CheckPaymentRequisitesResponse checkPaymentRequisitesResponse) throws AbstractException {

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // Create the root element 'return'
            Element returnElement = document.createElement("return");
            document.appendChild(returnElement);

            // Create the 'rows' element
            Element rowsElement = document.createElement("rows");
            returnElement.appendChild(rowsElement);

            // Create the 'row' element
            Element rowElement = document.createElement("row");
            rowsElement.appendChild(rowElement);

            // List all PossPaymentRequest attributes  and add them to the response
            /*List<PossPaymentRequest.Attribute> attributes = possPaymentRequest.getAttributes();
            for (PossPaymentRequest.Attribute attribute : attributes) {
                Element idElement = document.createElement(attribute.getName().toLowerCase());
                Text idText = document.createTextNode(attribute.getValue());
                idElement.appendChild(idText);
                rowElement.appendChild(idElement);
            }*/

            // List all CheckPaymentRequisitesResponse extra fields and add them to the response
            List<CheckPaymentRequisitesResponse.Extra> extras = checkPaymentRequisitesResponse.getExtras();
            for (CheckPaymentRequisitesResponse.Extra extra : extras) {
                Element idElement = document.createElement(extra.getName().toLowerCase());
                Text idText = document.createTextNode(extra.getValue());
                idElement.appendChild(idText);
                rowElement.appendChild(idElement);
            }

            // Convert the DOM to String
            String xmlString = Core.convertDocumentToString(document);

            // Convert the String to OMElement
            OMFactory responseFactory = OMAbstractFactory.getOMFactory();

            return OMXMLBuilderFactory.createOMBuilder(responseFactory, new ByteArrayInputStream(xmlString.getBytes())).getDocumentElement();

        } catch (Exception e) {
            throw new AbstractException("Не удалось сформировать ответ для PossPayment");
        }
    }
}
