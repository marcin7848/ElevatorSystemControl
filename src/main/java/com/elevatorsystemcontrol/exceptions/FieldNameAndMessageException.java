package com.elevatorsystemcontrol.exceptions;

/*
 * Exception class for ControllerAdvisor.
 * Allows throwing an exception with a @param fieldName and @param errorMessage
 * */
public class FieldNameAndMessageException extends RuntimeException {
    public FieldNameAndMessageException(String fieldName, String errorMessage) {
        super(String.format("%s %s", fieldName, errorMessage));
    }
}
