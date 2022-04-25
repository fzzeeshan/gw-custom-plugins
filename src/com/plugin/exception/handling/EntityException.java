package com.plugin.exception.handling;

/**
 * Created with IntelliJ IDEA.
 * User: zacky
 * Date: 31/8/21
 * Time: 9:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class EntityException extends Exception {

    public EntityException(Throwable cause) {
        super(cause);
    }

    public EntityException() {

    }

    public EntityException(String message) {
        super(message);
    }

    public EntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
