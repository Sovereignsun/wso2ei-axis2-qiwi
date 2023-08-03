package ru.kubankredit.qiwi.methods.qiwi.setPublicKey;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.crypt.HashingModule;
import ru.kubankredit.qiwi.core.logging.LogModule;
import ru.kubankredit.qiwi.serializers.PayloadSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SetPublicKeyPayload {

    final protected String thisClassName = this.getClass().getName();
    protected final PayloadSerializer payloadSerializer = new PayloadSerializer();
    protected LogModule lm = new LogModule();

    public String buildXMLPayload(SetPublicKeyRequest setPublicKeyRequestXML) {

        try {
            HashingModule hashingModule = new HashingModule();

            // Initialize the parameters from the configuration file
            String personLogin = Core.settings.getQiwiSettings().getLogin();
            String signAlgorithm = Core.settings.getQiwiSettings().getSignAlgorithm();
            String publicKeyStoreType = Core.settings.getQiwiSettings().getPublicKeyStoreType();

            // Create a new DocumentBuilder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            // Create a new Document
            Document document = dBuilder.newDocument();

            // Set the XML document declaration
            document.setXmlVersion("1.0");
            document.setXmlStandalone(true);

            // Create the root element
            Element request = document.createElement("request");
            document.appendChild(request);

            // Create the auth element
            Element authElement = document.createElement("auth");
            request.appendChild(authElement);

            // Set the attributes for the auth element
            authElement.setAttribute("login", personLogin);
            authElement.setAttribute("sign", hashingModule.HashMD5(setPublicKeyRequestXML.getUserPassword()));
            authElement.setAttribute("signAlg", signAlgorithm);

            // Create the persons element
            Element personsElement = document.createElement("persons");
            request.appendChild(personsElement);

            // Create the setPublicKey element
            Element setPublicKeyElement = document.createElement("setPublicKey");
            personsElement.appendChild(setPublicKeyElement);

            // Create the store-type element
            Element storeTypeElement = document.createElement("store-type");
            storeTypeElement.appendChild(document.createTextNode(publicKeyStoreType));
            setPublicKeyElement.appendChild(storeTypeElement);

            // Create the pubkey element
            Element pubkeyElement = document.createElement("pubkey");
            pubkeyElement.appendChild(document.createTextNode(setPublicKeyRequestXML.getPublicKey()));
            setPublicKeyElement.appendChild(pubkeyElement);

            document.getDocumentElement().normalize();

            // Return the serialized XML Payload
            return payloadSerializer.serializeXMLPayload(document);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* Example XML Payload

     <?xml version="1.0" encoding="windows-1251"?>
     <request>
     <auth login="dev0901" sign="fcdda257539b54290b389c7bcfe738c7" signAlg="MD5"/>
     <persons>
        <setPublicKey>
          <store-type>1</store-type>
          <pubkey>MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3bXVsHI4LjHe05gAr+1DeKzQjRsmb/eRsCqdSNletOJkRzwBHMY0brx+utqkNFjUifLMGLJAaX+PVnj07kDXTjpdFO9aFXnh/KCkr3zXgRr7FY2pMEsr3dhK1QIYrkHUW+fgbH4qRU7vucGEIm2GyWHFEtqYIz0mQ0e/oDQUIgwIDAQAB</pubkey>
        </setPublicKey>
      </persons>
    </request>

    */
}

