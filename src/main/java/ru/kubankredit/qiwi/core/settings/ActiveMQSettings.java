package ru.kubankredit.qiwi.core.settings;

public class ActiveMQSettings {
    private String url;
    private String username;
    private String password;
    private Integer consumeInterval;
    private Integer consumeMessagesPerCall;
    private Boolean consumeActive;
    private String paymentStatusQueueName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getConsumeInterval() {
        return consumeInterval;
    }

    public void setConsumeInterval(Integer consumeInterval) {
        this.consumeInterval = consumeInterval;
    }

    public Boolean getConsumeActive() {
        return consumeActive;
    }

    public void setConsumeActive(Boolean consumeActive) {
        this.consumeActive = consumeActive;
    }

    public Integer getConsumeMessagesPerCall() {
        return consumeMessagesPerCall;
    }

    public void setConsumeMessagesPerCall(Integer consumeMessagesPerCall) {
        this.consumeMessagesPerCall = consumeMessagesPerCall;
    }

    public String getPaymentStatusQueueName() {
        return paymentStatusQueueName;
    }

    public void setPaymentStatusQueueName(String queueName) {
        this.paymentStatusQueueName = queueName;
    }
}
