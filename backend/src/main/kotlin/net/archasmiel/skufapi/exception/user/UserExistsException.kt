package net.archasmiel.skufapi.exception.user

class UserExistsException: Exception {

    constructor(usernameOrEmail: String, isEmail: Boolean):
            super("User already exists by ${if (isEmail) "email" else "username"}: $usernameOrEmail")

}