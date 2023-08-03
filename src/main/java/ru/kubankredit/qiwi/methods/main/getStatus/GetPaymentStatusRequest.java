package ru.kubankredit.qiwi.methods.main.getStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetPaymentStatus")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetPaymentStatusRequest {

    @XmlElement(name = "pmntId")
    private String pmntId;
    @XmlElement(name = "extId")
    private String extId;
    @XmlElement(name = "serviceId")
    private String serviceId;
    @XmlElement(name = "terminal")
    private String terminal;

    public String getPmntId() {
        return pmntId;
    }

    public void setPmntId(String pmntId) {
        this.pmntId = pmntId;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }
}
