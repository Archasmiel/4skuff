package net.archasmiel.skufapi.exception.token

class JwtTokenException: Exception {

    constructor(message: String):
            super("JWT token exception: $message")

}