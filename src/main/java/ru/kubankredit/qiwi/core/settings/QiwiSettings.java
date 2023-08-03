package ru.kubankredit.qiwi.core.settings;

public class QiwiSettings {

    private String url;
    private Integer connectionTimeout;
    private Integer readTimeout;
    private String login;
    private String privateKeyPath;
    private Boolean keyIsResource;
    private String publicKeyStoreType;
    private String signAlgorithm;
    private String software;
    private String terminalId;
    private String userAgent;
    private String digitalSignAlgorithm;
    private String fixedAmount;

    private Boolean devMode;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public Boolean getKeyIsResource() {
        return keyIsResource;
    }

    public void setKeyIsResource(Boolean keyIsResource) {
        this.keyIsResource = keyIsResource;
    }

    public String getPublicKeyStoreType() {
        return publicKeyStoreType;
    }

    public void setPublicKeyStoreType(String publicKeyStoreType) {
        this.publicKeyStoreType = publicKeyStoreType;
    }

    public String getSignAlgorithm() {
        return signAlgorithm;
    }

    public void setSignAlgorithm(String signAlgorithm) {
        this.signAlgorithm = signAlgorithm;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getDigitalSignAlgorithm() {
        return digitalSignAlgorithm;
    }

    public void setDigitalSignAlgorithm(String digitalSignAlgorithm) {
        this.digitalSignAlgorithm = digitalSignAlgorithm;
    }

    public String getFixedAmount() {
        return fixedAmount;
    }

    public void setFixedAmount(String fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    public Boolean getDevMode() {
        return devMode;
    }

    public void setDevMode(Boolean devMode) {
        this.devMode = devMode;
    }
}
