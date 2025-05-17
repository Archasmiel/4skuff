package net.archasmiel.skufapi.exception.user

import net.archasmiel.skufapi.exception.ApiException

class UserExistException(
    usernameOrEmail: String,
    isEmail: Boolean
) : ApiException(
    "USER_EXIST",
    "user by ${if (isEmail) "email" else "username"}: $usernameOrEmail"
)