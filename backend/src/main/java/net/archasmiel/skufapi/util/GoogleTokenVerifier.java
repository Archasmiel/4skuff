package net.archasmiel.skufapi.util;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import net.archasmiel.skufapi.exception.GoogleTokenException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
@RequiredArgsConstructor
public class GoogleTokenVerifier {

    private final GoogleIdTokenVerifier verifier;

    /**
     * Check Google IdToken and get {@link GoogleIdToken} from it.
     *
     * @param googleToken token as {@link String}
     * @return info about token as {@link GoogleIdToken}
     * @throws GoogleTokenException throws if null token or verification exceptions
     */
    public GoogleIdToken getToken(final String googleToken)
            throws GoogleTokenException {
        try {
            GoogleIdToken idToken = verifier.verify(googleToken);
            if (idToken == null) {
                throw new GoogleTokenException("Token wrong, malformed or expired");
            }
            return idToken;
        } catch (GeneralSecurityException e) {
            throw new GoogleTokenException("Server security problem");
        } catch (IOException e) {
            throw new GoogleTokenException("Server I/O exception");
        }
    }

}
