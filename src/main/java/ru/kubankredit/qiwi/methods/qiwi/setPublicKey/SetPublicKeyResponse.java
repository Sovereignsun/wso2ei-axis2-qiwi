package ru.kubankredit.qiwi.methods.qiwi.setPublicKey;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SetPublicKeyResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class SetPublicKeyResponse {

    @XmlElement(name = "resultCode")
    private Integer resultCode;
    @XmlElement(name = "resultMessage")
    private String resultMessage;

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

    @Override
    public String toString() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<SetPublicKeyResponse>" +
                "<resultMessage>" + resultMessage + "</resultMessage>" +
                "<resultCode>" + resultCode + "</resultCode>" +
                "</SetPublicKeyResponse>";
    }
}
