package net.archasmiel.skufapi.exception.token

import net.archasmiel.skufapi.exception.ApiException

class GoogleTokenException(
    msg: String = "Google token error"
) : ApiException(
    "INVALID_GOOGLE_TOKEN",
    msg
)