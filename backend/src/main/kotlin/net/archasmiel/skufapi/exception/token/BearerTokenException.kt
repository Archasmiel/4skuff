package net.archasmiel.skufapi.exception.token

import net.archasmiel.skufapi.exception.ApiException

class BearerTokenException(
    msg: String = "Bearer token error"
) : ApiException(
    "INVALID_BEARER_TOKEN",
    msg
)