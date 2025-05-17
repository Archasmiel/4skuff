package net.archasmiel.skufapi.exception.auth

import net.archasmiel.skufapi.exception.ApiException

class AuthenticationException(
    errorCode: String,
    msg: String = "Authentication error"
) : ApiException(
    errorCode,
    msg
)