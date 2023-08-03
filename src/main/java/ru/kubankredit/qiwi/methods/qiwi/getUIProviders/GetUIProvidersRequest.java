package ru.kubankredit.qiwi.methods.qiwi.getUIProviders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetUIProvidersRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetUIProvidersRequest {

    @XmlElement(name = "terminal")
    private String terminal;

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

}
