package net.archasmiel.skufapi.exception.token

import net.archasmiel.skufapi.exception.ApiException

class JwtTokenException(
    msg: String = "JWT token error"
) : ApiException(
    "INVALID_JWT_TOKEN",
    msg
)