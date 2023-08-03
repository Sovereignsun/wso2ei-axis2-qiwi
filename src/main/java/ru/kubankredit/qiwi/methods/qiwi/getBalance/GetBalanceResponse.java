package ru.kubankredit.qiwi.methods.qiwi.getBalance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetBalanceResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetBalanceResponse {

    @XmlElement(name = "resultMessage")
    private String resultMessage;

    @XmlElement(name = "resultCode")
    private Integer resultCode;

    @XmlElement(name = "agentID")
    private String agentID;

    @XmlElement(name = "balance")
    private String balance;

    @XmlElement(name = "treeBalance")
    private String treeBalance;

    @XmlElement(name = "overdraft")
    private String overdraft;

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTreeBalance() {
        return treeBalance;
    }

    public void setTreeBalance(String treeBalance) {
        this.treeBalance = treeBalance;
    }

    public String getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(String overdraft) {
        this.overdraft = overdraft;
    }

    @Override
    public String toString() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<GetBalanceResponse>" +
                "<resultMessage>" + resultMessage + "</resultMessage>" +
                "<resultCode>" + resultCode + "</resultCode>" +
                "<agentID>" + agentID + "</agentID>" +
                "<balance>" + balance + "</balance>" +
                "<treeBalance>" + treeBalance + "</treeBalance>" +
                "<overdraft>" + overdraft + "</overdraft>" +
                "</GetBalanceResponse>";
    }
}
