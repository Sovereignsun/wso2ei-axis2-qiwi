package ru.kubankredit.qiwi.methods.qiwi.setPublicKey;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SetPublicKey")
@XmlAccessorType(XmlAccessType.FIELD)
public class SetPublicKeyRequest {
    @XmlElement(name = "UserPassword")
    private String userPassword;

    @XmlElement(name = "PublicKey")
    private String publicKey;

    // Getters and setters

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
