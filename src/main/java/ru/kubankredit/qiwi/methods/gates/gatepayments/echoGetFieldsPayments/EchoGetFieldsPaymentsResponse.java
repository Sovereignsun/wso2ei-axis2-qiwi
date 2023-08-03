package ru.kubankredit.qiwi.methods.gates.gatepayments.echoGetFieldsPayments;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "echoGetFieldsPayments")
@XmlAccessorType(XmlAccessType.FIELD)
public class EchoGetFieldsPaymentsResponse {

    @XmlElement(name = "error")
    private int error;
    @XmlElement(name = "errorMessage")
    private String errorMessage;
    @XmlElement(name = "xml")
    private String xml;
    @XmlElement(name = "row")
    private List<EchoGetFieldsPaymentsResponse.Field> fields;

    public String getFieldRegisteredNameByName(String name) {
        for (EchoGetFieldsPaymentsResponse.Field field : fields) {
            if (field.getF_name().equalsIgnoreCase(name)) {
                return field.getF_name_in_reestr();
            }
        }
        return null;
    }

    public EchoGetFieldsPaymentsResponse.Field findFieldByName(String name) {
        for (EchoGetFieldsPaymentsResponse.Field field : fields) {
            if (field.getF_name().equalsIgnoreCase(name)) {
                return field;
            }
        }
        return null;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<EchoGetFieldsPaymentsResponse.Field> getFields() {
        return fields;
    }

    public void setFields(List<EchoGetFieldsPaymentsResponse.Field> fields) {
        this.fields = fields;
    }

    public static class Field {
        @XmlElement(name = "k_id")
        private String k_id;
        @XmlElement(name = "f_name_in_reestr")
        private String f_name_in_reestr;
        @XmlElement(name = "f_name")
        private String f_name;
        @XmlElement(name = "obligatory")
        private Boolean obligatory;
        @XmlElement(name = "active")
        private Boolean active;
        @XmlElement(name = "description")
        private String description;
        @XmlElement(name = "table_name")
        private String table_name;
        @XmlElement(name = "field_name")
        private String field_name;
        @XmlElement(name = "visible")
        private Boolean visible;
        @XmlElement(name = "f_regexp")
        private Boolean f_regexp;

        public String getK_id() {
            return k_id;
        }

        public void setK_id(String k_id) {
            this.k_id = k_id;
        }

        public String getF_name_in_reestr() {
            return f_name_in_reestr;
        }

        public void setF_name_in_reestr(String f_name_in_reestr) {
            this.f_name_in_reestr = f_name_in_reestr;
        }

        public String getF_name() {
            return f_name;
        }

        public void setF_name(String f_name) {
            this.f_name = f_name;
        }

        public Boolean getObligatory() {
            return obligatory;
        }

        public void setObligatory(Boolean obligatory) {
            this.obligatory = obligatory;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTable_name() {
            return table_name;
        }

        public void setTable_name(String table_name) {
            this.table_name = table_name;
        }

        public String getField_name() {
            return field_name;
        }

        public void setField_name(String field_name) {
            this.field_name = field_name;
        }

        public Boolean getVisible() {
            return visible;
        }

        public void setVisible(Boolean visible) {
            this.visible = visible;
        }

        public Boolean getF_regexp() {
            return f_regexp;
        }

        public void setF_regexp(Boolean f_regexp) {
            this.f_regexp = f_regexp;
        }
    }

}
