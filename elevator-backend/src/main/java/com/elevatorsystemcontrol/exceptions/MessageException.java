package com.elevatorsystemcontrol.exceptions;

/**
 * Exception class for ControllerAdvisor
 * Allows throwing an exception with a default message passed in @param errorMessage
 */
public class MessageException extends RuntimeException {

    public MessageException(String errorMessage) {
        super(errorMessage);
    }

}
