package net.archasmiel.skufapi.exception;

public class GoogleTokenException extends Exception {

    public GoogleTokenException(String details) {
        super("Critical exception in Google token verification: " + details);
    }

    public GoogleTokenException(GoogleTokenException e) {
        super(e.getMessage());
    }
}
