package dev.exception;

public class NoSuchBookingException extends RuntimeException {
    public NoSuchBookingException() {
        super("NoSuchBookingException");
    }
}
