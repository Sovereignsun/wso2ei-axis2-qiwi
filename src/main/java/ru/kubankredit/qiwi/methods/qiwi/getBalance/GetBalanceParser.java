package ru.kubankredit.qiwi.methods.qiwi.getBalance;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ru.kubankredit.qiwi.core.Core;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

public class GetBalanceParser {

    final protected String thisClassName = this.getClass().getName();

    public GetBalanceResponse ParseResponse(String getBalanceResponseXML) {

        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        GetBalanceResponse getBalanceResponseEntity = new GetBalanceResponse();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(getBalanceResponseXML.getBytes("WINDOWS-1251")));

            Element root = document.getDocumentElement();

            Integer resultCode = null;
            String resultMessage = null;
            String balance = null;
            String agentId = null;
            String treeBalance = null;
            String overdraft = null;

            resultCode = Integer.valueOf(root.getAttribute("result"));

            // Get the 'agents' element
            Element agentsElement = (Element) root.getElementsByTagName("agents").item(0);

            // Get the 'getBalance' element
            NodeList balanceNodeList = agentsElement.getElementsByTagName("getBalance");

            if (balanceNodeList.getLength() > 0) {
                // Parsing when 'getBalance' tag exists
                agentId = root.getElementsByTagName("agent-id").item(0).getTextContent();
                balance = root.getElementsByTagName("balance").item(0).getTextContent();
                treeBalance = root.getElementsByTagName("tree-balance").item(0).getTextContent();
                overdraft = root.getElementsByTagName("overdraft").item(0).getTextContent();
            } else {
                // Parsing when 'getBalance' tag does not exist
                resultCode = Integer.valueOf(root.getAttribute("result"));
                resultMessage = root.getTextContent().trim();
            }

            getBalanceResponseEntity.setResultCode(resultCode);
            getBalanceResponseEntity.setResultMessage(resultMessage);
            getBalanceResponseEntity.setAgentID(agentId);
            getBalanceResponseEntity.setBalance(balance);
            getBalanceResponseEntity.setTreeBalance(treeBalance);
            getBalanceResponseEntity.setOverdraft(overdraft);

        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
        }

        return getBalanceResponseEntity;
    }

    /*public static void main(String[] args) {
        SetPublicKeyParser setPublicKeyParser = new SetPublicKeyParser();
        HTTPModuleEmulator HTTPModuleEmulator = new HTTPModuleEmulator();

        SetPublicKeyResponse responseEntity = setPublicKeyParser.ParseSetPublicKeyResponse(HTTPModuleEmulator.setPublicKeyMethod());

        System.out.println(responseEntity.getResultCode()+": "+responseEntity.getResultMessage());
    }*/
}
