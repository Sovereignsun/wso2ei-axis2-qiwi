package ru.kubankredit.qiwi.methods.qiwi.getPaymentStatus;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetPaymentStatusResponse {

    // Признак фатальной ошибки
    @XmlAttribute(name = "fatal")
    private boolean fatal;

    // Код ошибки проведения платежа
    @XmlAttribute(name = "result")
    private int result;

    // Текущий статус платежа
    @XmlAttribute(name = "status")
    private int status;

    // Идентификатор транзакции в КИВИ
    @XmlAttribute(name = "uid")
    private String uid;

    // экстра-поля ответа
    @XmlElement(name = "extras")
    private List<Extra> extras;

    public boolean isFatal() {
        return fatal;
    }

    public void setFatal(boolean fatal) {
        this.fatal = fatal;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<Extra> getExtras() {
        return extras;
    }

    public void setExtras(List<Extra> extras) {
        this.extras = extras;
    }

    public static class Extra {
        @XmlAttribute(name = "Name")
        private String name;

        @XmlValue
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
}
