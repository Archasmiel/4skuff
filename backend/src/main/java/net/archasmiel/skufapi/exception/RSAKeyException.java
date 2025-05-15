package net.archasmiel.skufapi.exception;

public class RSAKeyException extends RuntimeException {

  public RSAKeyException(String details) {
    super("RSA Key Exception: " + details);
  }
}
