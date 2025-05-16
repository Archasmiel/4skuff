package net.archasmiel.skufapi.exception.security

class RSAKeyException: Exception {

    constructor(message: String):
            super("RSA Key Exception: $message")

}