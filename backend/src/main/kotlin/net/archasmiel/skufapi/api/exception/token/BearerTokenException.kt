package net.archasmiel.skufapi.api.exception.token

import net.archasmiel.skufapi.api.exception.ApiException

class BearerTokenException(
    msg: String = "Bearer token error"
) : ApiException(
    "INVALID_BEARER_TOKEN",
    msg
)