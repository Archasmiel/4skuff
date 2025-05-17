package net.archasmiel.skufapi.exception.token

import net.archasmiel.skufapi.exception.ApiException

class RsaKeyException(
    msg: String = "RSA key fatal error"
) : ApiException(
    "RSA_KEY_EXCEPTION",
    msg
)