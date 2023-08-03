package ru.kubankredit.qiwi.methods.qiwi.getBalance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetBalanceRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetBalanceRequest {

    @XmlElement(name = "terminal")
    private String terminal;

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }
}
