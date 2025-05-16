package net.archasmiel.skufapi.exception.user

class UsernameNotFoundException: Exception {

    constructor(username: String):
            super("Not found username: $username")

}