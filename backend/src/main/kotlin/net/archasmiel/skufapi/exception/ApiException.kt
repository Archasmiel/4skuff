package net.archasmiel.skufapi.exception

abstract class ApiException(
    val errorCode: String,
    val msg: String,
    cause: Throwable? = null
) : RuntimeException(
    msg,
    cause
)