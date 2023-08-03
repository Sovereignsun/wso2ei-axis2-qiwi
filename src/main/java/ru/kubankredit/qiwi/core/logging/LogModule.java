package ru.kubankredit.qiwi.core.logging;

import org.apache.log4j.Logger;

import java.util.UUID;

public final class LogModule {

    private final String LOGGER_NAME = "ru.kubankredit.QIWIService";
    private final Logger logger = Logger.getLogger(LOGGER_NAME);
    private String GUID;

    public Logger getLogger() {
        return logger;
    }

    public String getGUID() {
        return GUID;
    }

    public void generateGUID() {
        this.GUID = UUID.randomUUID().toString();
    }

    public void warning(String clazzName, String msg) {
        logger.warn("[" + GUID + ":" + clazzName + "]: " + msg);
    }

    public void warning(String clazzName, String methodName, String msg) {
        logger.warn("[" + GUID + ":" + clazzName + "." + methodName + "]: " + msg);
    }

    public void error(String clazzName, String msg) {
        logger.error("[" + GUID + ":" + clazzName + "]: " + msg);
    }

    public void error(String clazzName, String methodName, String msg) {
        logger.error("[" + GUID + ":" + clazzName + "." + methodName + "]: " + msg);
    }

    public void trace(String clazzName, String msg) {
        logger.trace("[" + GUID + ":" + clazzName + "]: " + msg);
    }

    public void trace(String clazzName, String methodName, String msg) {
        logger.trace("[" + GUID + ":" + clazzName + "." + methodName + "]: " + msg);
    }

    public void debug(String clazzName, String msg) {
        logger.debug("[" + GUID + ":" + clazzName + "]: " + msg);
    }

    public void debug(String clazzName, String methodName, String msg) {
        logger.debug("[" + GUID + ":" + clazzName + "." + methodName + "]: " + msg);
    }

    public void info(String clazzName, String msg) {
        logger.info("[" + GUID + ":" + clazzName + "]: " + msg);
    }

    public void info(String clazzName, String methodName, String msg) {
        logger.info("[" + GUID + ":" + clazzName + "." + methodName + "]: " + msg);
    }

    public void log(String clazzName, String methodName, String msg, Level level) {
        switch (level) {
            case TRACE:
                logger.trace("[" + GUID + ":" + clazzName + "." + methodName + "]: " + msg);
                break;
            case DEBUG:
                logger.debug("[" + GUID + ":" + clazzName + "." + methodName + "]: " + msg);
                break;
            case INFO:
                logger.info("[" + GUID + ":" + clazzName + "." + methodName + "]: " + msg);
                break;
            case WARN:
                logger.warn("[" + GUID + ":" + clazzName + "." + methodName + "]: " + msg);
                break;
            case ERROR:
                logger.error("[" + GUID + ":" + clazzName + "." + methodName + "]: " + msg);
                break;
        }
    }

    public enum Level {TRACE,DEBUG, INFO, WARN, ERROR}

}
