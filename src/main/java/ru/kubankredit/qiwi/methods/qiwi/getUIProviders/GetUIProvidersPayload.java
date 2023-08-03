package ru.kubankredit.qiwi.methods.qiwi.getUIProviders;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.serializers.PayloadSerializer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class GetUIProvidersPayload {

    final protected String thisClassName = this.getClass().getName();

    protected PayloadSerializer payloadSerializer = new PayloadSerializer();

    public String buildXMLPayload(GetUIProvidersRequest getUIProvidersRequest) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();

        try {
            // Initialize the parameters from the configuration file
            String software = Core.settings.getQiwiSettings().getSoftware();
            String terminalId = Core.settings.getQiwiSettings().getTerminalId();

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

            // Create the "client" element with terminal and software attributes
            Element client = document.createElement("client");
            if (getUIProvidersRequest.getTerminal() != null) {
                client.setAttribute("terminal", getUIProvidersRequest.getTerminal());
            } else {
                client.setAttribute("terminal", terminalId);
            }
            client.setAttribute("software", software);
            request.appendChild(client);

            // Create the "providers" element
            Element providers = document.createElement("providers");
            request.appendChild(providers);

            // Create the "getUIProviders" element
            Element getUIProviders = document.createElement("getUIProviders");
            providers.appendChild(getUIProviders);

            document.getDocumentElement().normalize();

            // Return the serialized XML Payload
            return payloadSerializer.serializeXMLPayload(document);

        } catch (Exception e) {
            Core.lm.error(thisClassName,methodName, "Exception: "+e.getMessage());
        }

        return null;
    }

    /* Example XML Payload

     <?xml version="1.0" encoding="utf-8"?>
    <request>
        <client terminal="10788430" software="Dealer v0"/>
        <providers>
            <getUIProviders/>
        </providers>
    </request>

    */
}

