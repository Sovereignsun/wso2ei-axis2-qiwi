package ru.kubankredit.qiwi.methods.main.addPayment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "AddPayment")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddPaymentRequest {

    @XmlElement(name = "serviceId")
    private String serviceId;
    @XmlElement(name = "terminal")
    private String terminal;
    @XmlElement(name = "xml")
    private String xml;
    @XmlElement(name = "dataList")
    private List<Data> dataList;
    @XmlElement(name = "row")
    private List<Attribute> attributes;

    public Data findDataByName(String name) {
        for (Data data : dataList) {
            if (data.getName().equalsIgnoreCase(name)) {
                return data;
            }
        }
        return null;
    }

    public String getDataValueByName(String name) {
        for (Data data : dataList) {
            if (data.getName().equalsIgnoreCase(name)) {
                return data.value;
            }
        }
        return null;
    }

    public String getAttributeValueByName(String name) {
        for (Attribute attribute : attributes) {
            if (attribute.getName().equalsIgnoreCase(name)) {
                return attribute.value;
            }
        }
        return null;
    }

    public Attribute findAttributeByName(String name) {
        for (Attribute attribute : attributes) {
            if (attribute.getName().equalsIgnoreCase(name)) {
                return attribute;
            }
        }
        return null;
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

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public static class Data {

        @XmlElement(name = "name")
        private String name;
        @XmlElement(name = "value")
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    public static class Attribute {
        @XmlElement(name = "name")
        private String name;
        @XmlElement(name = "value")
        private String value;
        @XmlElement(name = "required")
        private Boolean required;

        public String getName() {
            return name;
        }

        public void setName(String fieldName) {
            this.name = fieldName;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String fieldValue) {
            this.value = fieldValue;
        }

        public Boolean getRequired() {
            return required;
        }

        public void setRequired(Boolean required) {
            this.required = required;
        }
    }

}
