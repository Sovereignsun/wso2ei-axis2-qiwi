package ru.kubankredit.qiwi.core.exceptions;

public class AbstractException extends Exception {

    private final String message;
    private final String detailMessage;
    private final int code;

    public AbstractException(String message) {
        this(message, "", -1);
    }

    public AbstractException(String message, int code) {
        this(message, "", code);
    }

    public AbstractException(String message, String detailMessage) {
        this(message, detailMessage, -1);
    }

    public AbstractException(String message, String detailMessage, int code) {
        this.message = message;
        this.detailMessage = detailMessage;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public int getCode() {
        return code;
    }

}

