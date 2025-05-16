package net.archasmiel.skufapi.exception.user

class GoogleUserExistsException: Exception {

    constructor(usernameOrEmail: String, isEmail: Boolean):
            super("User already exists by ${if (isEmail) "email" else "username"}: $usernameOrEmail")

}