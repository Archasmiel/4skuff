package net.archasmiel.skufapi.api.exception.user

import net.archasmiel.skufapi.api.exception.ApiException

class GoogleUserExistException(
    usernameOrEmail: String,
    isEmail: Boolean
) : ApiException(
    "GOOGLE_USER_EXIST",
    "user by ${if (isEmail) "email" else "username"}: $usernameOrEmail"
)