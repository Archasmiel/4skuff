package net.archasmiel.skufapi.api.exception.token

import net.archasmiel.skufapi.api.exception.ApiException

class JwtTokenException(
    msg: String = "JWT token error"
) : ApiException(
    "INVALID_JWT_TOKEN",
    msg
)