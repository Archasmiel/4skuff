package net.archasmiel.skufapi.api.exception.token

import net.archasmiel.skufapi.api.exception.ApiException

class RsaKeyException(
    msg: String = "RSA key fatal error"
) : ApiException(
    "RSA_KEY_EXCEPTION",
    msg
)