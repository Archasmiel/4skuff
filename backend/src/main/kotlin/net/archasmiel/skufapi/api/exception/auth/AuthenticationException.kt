package net.archasmiel.skufapi.api.exception.auth

import net.archasmiel.skufapi.api.exception.ApiException

class AuthenticationException(
    msg: String = "Authentication error"
) : ApiException(
    "AUTH_FAILED",
    msg
)