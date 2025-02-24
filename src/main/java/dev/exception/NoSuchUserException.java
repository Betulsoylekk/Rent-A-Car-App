package dev.exception;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException() {
        super("NoSuchUserException");
    }
}
