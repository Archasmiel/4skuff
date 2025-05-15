package net.archasmiel.skufapi.exception;

public class GoogleTokenVerificationException extends Exception {

  public GoogleTokenVerificationException(String details) {
    super("Critical exception in Google token verification: " + details);
  }

  public GoogleTokenVerificationException(GoogleTokenVerificationException e) {
    super(e.getMessage());
  }
}
