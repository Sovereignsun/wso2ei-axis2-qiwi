package ru.kubankredit.qiwi.methods.qiwi.setPublicKey;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ru.kubankredit.qiwi.core.Core;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

public class SetPublicKeyParser {

    final protected String thisClassName = this.getClass().getName();

    public SetPublicKeyResponse ParseResponse(String setPublicKeyResponseXML) {

        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        SetPublicKeyResponse setPublicKeyResponseEntity = new SetPublicKeyResponse();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(setPublicKeyResponseXML.getBytes("WINDOWS-1251")));

            Element root = document.getDocumentElement();

            Integer resultCode = null;
            String resultMessage = null;

            // Check if the 'persons' tag exists
            NodeList personsNodeList = root.getElementsByTagName("persons");
            if (personsNodeList.getLength() > 0) {
                // Parsing when 'persons' tag exists
                Element setPublicKeyElement = (Element) root.getElementsByTagName("setPublicKey").item(0);
                resultCode = Integer.valueOf(setPublicKeyElement.getAttribute("result"));
                resultMessage = setPublicKeyElement.getAttribute("result-description");
            } else {
                // Parsing when 'persons' tag does not exist
                resultCode = Integer.valueOf(root.getAttribute("result"));
                resultMessage = root.getTextContent().trim();
            }

            setPublicKeyResponseEntity.setResultCode(resultCode);
            setPublicKeyResponseEntity.setResultMessage(resultMessage);

        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
        }

        return setPublicKeyResponseEntity;
    }

    /*public static void main(String[] args) {
        SetPublicKeyParser setPublicKeyParser = new SetPublicKeyParser();
        HTTPModuleEmulator HTTPModuleEmulator = new HTTPModuleEmulator();

        SetPublicKeyResponse responseEntity = setPublicKeyParser.ParseSetPublicKeyResponse(HTTPModuleEmulator.setPublicKeyMethod());

        System.out.println(responseEntity.getResultCode()+": "+responseEntity.getResultMessage());
    }*/
}
