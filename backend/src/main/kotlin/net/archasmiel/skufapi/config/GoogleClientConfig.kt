package net.archasmiel.skufapi.config

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import lombok.Getter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
@Getter
class GoogleClientConfig(
    @Value("\${spring.security.oauth2.client.registration.google.client-id}")
    private val googleClientId: String,

    @Value("\${spring.security.oauth2.client.registration.google.client-secret}")
    private val googleClientSecret: String
) {

    @Bean
    fun googleIdTokenVerifier(): GoogleIdTokenVerifier =
        GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setAudience(listOf(googleClientId))
            .build()

}