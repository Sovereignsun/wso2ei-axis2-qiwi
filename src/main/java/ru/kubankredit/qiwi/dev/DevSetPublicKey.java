package ru.kubankredit.qiwi.dev;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMXMLBuilderFactory;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;
import ru.kubankredit.qiwi.emulators.QiwiHTTPEmulator;
import ru.kubankredit.qiwi.methods.qiwi.setPublicKey.*;

import java.io.ByteArrayInputStream;

public class DevSetPublicKey {

    public static void main(String[] args) {

        final String thisClassName = DevGetBalance.class.getName();
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        Core.lm.generateGUID();

        String xmlData = "<SetPublicKey>\n" +
                "  <UserPassword>h8mJ35Rr51</UserPassword>\n" +
                "  <PublicKey>MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3bXVsHI4LjHe05gAr+1DeKzQjRsmb/eRsCqdSNletOJkRzwBHMY0brx+utqkNFjUifLMGLJAaX+PVnj07kDXTjpdFO9aFXnh/KCkr3zXgRr7FY2pMEsr3dhK1QIYrkHUW+fgbH4qRU7vucGEIm2GyWHFEtqYIz0mQ0e/oDQUIgwIDAQAB</PublicKey>\n" +
                "</SetPublicKey>";

        OMFactory requestFactory = OMAbstractFactory.getOMFactory();
        OMElement requestData = OMXMLBuilderFactory.createOMBuilder(requestFactory, new ByteArrayInputStream(xmlData.getBytes())).getDocumentElement();

        SetPublicKeySerializer setPublicKeySerializer = new SetPublicKeySerializer();
        SetPublicKeyResponse result = new SetPublicKeyResponse();
        SetPublicKeyParser setPublicKeyParser = new SetPublicKeyParser();
        SetPublicKeyPayload setPublicKeyPayload = new SetPublicKeyPayload();
        QiwiHTTPEmulator qiwiHttpEmulator = new QiwiHTTPEmulator();

        SetPublicKeyRequest setPublicKeyRequest = null;
        try {
            setPublicKeyRequest = setPublicKeySerializer.serialize(requestData);
        } catch (AbstractException e) {
            result.setResultCode(-1);
            result.setResultMessage(e.getMessage());
            Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
            return;
        }

        String XMLPayloadString = setPublicKeyPayload.buildXMLPayload(setPublicKeyRequest);

        Core.lm.debug(thisClassName, methodName, "XMLPayloadString: " + XMLPayloadString);

        try {
            String QiwiResponse = qiwiHttpEmulator.setPublicKey();
            result = setPublicKeyParser.ParseResponse(QiwiResponse);
            Core.lm.debug(thisClassName, methodName, result.getResultCode() + ": " + result.getResultMessage());
        } catch (Exception e) {
            result.setResultCode(-1);
            result.setResultMessage(e.getMessage());
            Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
        }

    }

}
