package net.archasmiel.skufapi.api.exception.token

import net.archasmiel.skufapi.api.exception.ApiException

class GoogleTokenException(
    msg: String = "Google token error"
) : ApiException(
    "INVALID_GOOGLE_TOKEN",
    msg
)