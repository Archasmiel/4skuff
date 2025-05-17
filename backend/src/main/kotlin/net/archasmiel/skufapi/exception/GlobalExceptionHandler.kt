package net.archasmiel.skufapi.exception

import net.archasmiel.skufapi.api.exception.ErrorResponse
import net.archasmiel.skufapi.exception.auth.AuthenticationException
import net.archasmiel.skufapi.exception.token.RsaKeyException
import net.archasmiel.skufapi.exception.token.BearerTokenException
import net.archasmiel.skufapi.exception.token.GoogleTokenException
import net.archasmiel.skufapi.exception.token.JwtTokenException
import net.archasmiel.skufapi.exception.user.GoogleUserExistException
import net.archasmiel.skufapi.exception.user.ResourceNotFoundException
import net.archasmiel.skufapi.exception.user.UserExistException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    fun makeErrorResponse(
        status: HttpStatus,
        e: ApiException
    ): ResponseEntity<ErrorResponse> = ResponseEntity.status(status).body(
        ErrorResponse(e.errorCode, e.msg)
    )

    // Authentication-related exceptions
    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(e: AuthenticationException): ResponseEntity<ErrorResponse> =
        makeErrorResponse(HttpStatus.UNAUTHORIZED, e)

    @ExceptionHandler(BearerTokenException::class)
    fun handleBearerTokenException(e: BearerTokenException): ResponseEntity<ErrorResponse> =
        makeErrorResponse(HttpStatus.UNAUTHORIZED, e)

    @ExceptionHandler(GoogleTokenException::class)
    fun handleGoogleTokenException(e: GoogleTokenException): ResponseEntity<ErrorResponse> =
        makeErrorResponse(HttpStatus.UNAUTHORIZED, e)

    @ExceptionHandler(JwtTokenException::class)
    fun handleJwtTokenException(e: JwtTokenException): ResponseEntity<ErrorResponse> =
        makeErrorResponse(HttpStatus.UNAUTHORIZED, e)

    // Security configuration exception
    @ExceptionHandler(RsaKeyException::class)
    fun handleRsaKeyException(e: RsaKeyException): ResponseEntity<ErrorResponse> =
        makeErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, e)

    // Business logic exceptions
    @ExceptionHandler(GoogleUserExistException::class)
    fun handleGoogleUserExistException(e: GoogleUserExistException): ResponseEntity<ErrorResponse> =
        makeErrorResponse(HttpStatus.CONFLICT, e)

    @ExceptionHandler(UserExistException::class)
    fun handleUserExistException(e: UserExistException): ResponseEntity<ErrorResponse> =
        makeErrorResponse(HttpStatus.CONFLICT, e)

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(e: ResourceNotFoundException): ResponseEntity<ErrorResponse> =
        makeErrorResponse(HttpStatus.NOT_FOUND, e)

}