package net.archasmiel.skufapi.exception.user

class EmailNotFoundException: Exception {

    constructor(email: String):
            super("Not found email: $email")

}