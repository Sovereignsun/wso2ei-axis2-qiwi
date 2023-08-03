package ru.kubankredit.qiwi.methods.main.getCharges;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "GetCharges")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetChargesRequest {

    @XmlElement(name = "serviceId")
    private String serviceId;
    @XmlElement(name = "terminal")
    private String terminal;
    @XmlElement(name = "xml")
    private String xml;
    @XmlElement(name = "dataList")
    private List<Data> dataList;
    @XmlElement(name = "fields")
    private List<Field> fields;

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

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
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

    public static class Field {
        @XmlElement(name = "fieldName")
        private String fieldName;
        @XmlElement(name = "fieldValue")
        private String fieldValue;
        @XmlElement(name = "fieldId")
        private String fieldId;

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }

        public String getFieldId() {
            return fieldId;
        }

        public void setFieldId(String fieldId) {
            this.fieldId = fieldId;
        }
    }

}
