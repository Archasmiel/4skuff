package net.archasmiel.skufapi.util

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import lombok.RequiredArgsConstructor
import net.archasmiel.skufapi.exception.token.GoogleTokenException
import org.springframework.stereotype.Component
import java.io.IOException
import java.security.GeneralSecurityException

@Component
@RequiredArgsConstructor
class GoogleTokenVerifier(
    private val verifierObj: GoogleIdTokenVerifier
) {

    @Throws(GoogleTokenException::class)
    fun extractToken(googleToken: String): GoogleIdToken {
        try {
            val idToken = verifierObj.verify(googleToken)
                ?: throw GoogleTokenException("Token wrong, malformed or expired. Also check system time.")
            return idToken
        } catch (ex: GeneralSecurityException) {
            throw GoogleTokenException("Server security problem")
        } catch (ex: IOException) {
            throw GoogleTokenException("Server security problem")
        }
    }

}