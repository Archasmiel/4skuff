package net.archasmiel.skufapi.exception.auth

class AuthException: Exception {

    constructor(message: String):
            super("Verification exception: $message")

}