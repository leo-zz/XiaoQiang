package com.xiaoqiang.client.entity;

import java.util.List;

public class ExceptionInfoEntity {
    StackTraceElement[] simplileStackElements;
    String exceptionMessage;
    String exceptionClassName;
    List<ExceptionInfoEntity> suppressedExceptions;
    ExceptionInfoEntity causeException;

    public StackTraceElement[] getSimplileStackElements() {
        return simplileStackElements;
    }

    public void setSimplileStackElements(StackTraceElement[] simplileStackElements) {
        this.simplileStackElements = simplileStackElements;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public void setExceptionClassName(String exceptionClassName) {
        this.exceptionClassName = exceptionClassName;
    }

    public List<ExceptionInfoEntity> getSuppressedExceptions() {
        return suppressedExceptions;
    }

    public void setSuppressedExceptions(List<ExceptionInfoEntity> suppressedExceptions) {
        this.suppressedExceptions = suppressedExceptions;
    }

    public ExceptionInfoEntity getCauseException() {
        return causeException;
    }

    public void setCauseException(ExceptionInfoEntity causeException) {
        this.causeException = causeException;
    }
}
