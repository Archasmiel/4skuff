package net.archasmiel.skufapi.exception.user

class GoogleUserNotFoundException: Exception {

    constructor(email: String):
            super("Not found google user: $email")

}