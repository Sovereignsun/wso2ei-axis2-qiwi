package ru.kubankredit.qiwi.methods.qiwi.setPublicKey;

import org.apache.axiom.om.OMElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SetPublicKeySerializer {

    final protected String thisClassName = this.getClass().getName();

    public SetPublicKeyRequest serialize(OMElement payload) throws AbstractException {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new AbstractException("ParserConfigurationException: " + e.getMessage());
        }
        Document document = null;
        try {
            document = builder.parse(new ByteArrayInputStream(payload.toString().getBytes()));
        } catch (SAXException e) {
            throw new AbstractException("SAXException: " + e.getMessage());
        } catch (IOException e) {
            throw new AbstractException("IOException: " + e.getMessage());
        }

        Element root = document.getDocumentElement();
        String userPassword = root.getElementsByTagName("UserPassword").item(0).getTextContent();
        String publicKey = root.getElementsByTagName("PublicKey").item(0).getTextContent();

        Core.lm.debug(thisClassName, methodName, "User Password: " + userPassword);
        Core.lm.debug(thisClassName, methodName, "Public Key: " + publicKey);

        SetPublicKeyRequest setPublicKeyRequest = new SetPublicKeyRequest();

        setPublicKeyRequest.setUserPassword(userPassword);
        setPublicKeyRequest.setPublicKey(publicKey);

        return setPublicKeyRequest;
    }

}
