package net.archasmiel.skufapi.exception.token

class BearerTokenException: Exception {

    constructor(message: String):
            super("Invalid bearer token: $message")

}