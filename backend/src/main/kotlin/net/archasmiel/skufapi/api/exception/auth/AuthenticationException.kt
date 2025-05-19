package net.archasmiel.skufapi.api.exception.auth

import net.archasmiel.skufapi.api.exception.ApiException

class AuthenticationException(
    errorCode: String,
    msg: String = "Authentication error"
) : ApiException(
    errorCode,
    msg
)