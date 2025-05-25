package net.archasmiel.skufapi.api.exception.user

import net.archasmiel.skufapi.api.exception.ApiException

class UserExistException(
    usernameOrEmail: String,
    isEmail: Boolean
) : ApiException(
    "USER_EXIST",
    "user by ${if (isEmail) "email" else "username"}: $usernameOrEmail"
)