package net.archasmiel.skufapi.exception.token

class GoogleTokenException: Exception {

    constructor(message: String):
            super("Google token exception: $message")

}