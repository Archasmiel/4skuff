package net.archasmiel.skufapi.exception;

public class AuthException extends Exception {

  public AuthException(String details) {
    super("Verification exception: " + details);
  }
}
