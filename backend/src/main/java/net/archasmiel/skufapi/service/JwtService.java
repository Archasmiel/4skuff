package net.archasmiel.skufapi.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.archasmiel.skufapi.exception.RSAKeyException;
import net.archasmiel.skufapi.domain.model.User;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.function.Function;

@Service
@Getter
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.token.expiration}")
    private long jwtExpirationMs;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    private PrivateKey getSigningKey() {
        if (privateKey != null) {
            return privateKey;
        }
        try (InputStream keyStream = getClass().getResourceAsStream("/keys/private.pem")) {
            if (keyStream == null) {
                throw new RSAKeyException("File with private key not found");
            }
            String privateKeyContent = new String(keyStream.readAllBytes());
            privateKeyContent =
                    privateKeyContent
                            .replace("-----BEGIN PRIVATE KEY-----", "")
                            .replace("-----END PRIVATE KEY-----", "")
                            .replaceAll("\\s", "");

            byte[] keyBytes = Base64.getDecoder().decode(privateKeyContent);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (InvalidKeySpecException e) {
            throw new RSAKeyException("Invalid private key");
        } catch (NoSuchAlgorithmException e) {
            throw new RSAKeyException("Algorithm for key not found");
        } catch (IOException e) {
            throw new RSAKeyException("Failed to read private key");
        }
    }

    private PublicKey getVerificationKey() {
        if (publicKey != null) {
            return publicKey;
        }
        try (InputStream keyStream = getClass().getResourceAsStream("/keys/public.pem")) {
            if (keyStream == null) {
                throw new RSAKeyException("File with private key not found");
            }
            String publicKeyContent = new String(keyStream.readAllBytes());
            publicKeyContent =
                    publicKeyContent
                            .replace("-----BEGIN PUBLIC KEY-----", "")
                            .replace("-----END PUBLIC KEY-----", "")
                            .replaceAll("\\s", "");

            byte[] keyBytes = Base64.getDecoder().decode(publicKeyContent);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (InvalidKeySpecException e) {
            throw new RSAKeyException("Invalid private key");
        } catch (NoSuchAlgorithmException e) {
            throw new RSAKeyException("Algorithm for key not found");
        } catch (IOException e) {
            throw new RSAKeyException("Failed to read private key");
        }
    }

    public String generateToken(UserDetails userDetails) {
        JwtClaims claims = new JwtClaims();
        claims.setSubject(userDetails.getUsername());
        claims.setIssuedAt(NumericDate.now());
        claims.setExpirationTime(
                NumericDate.fromMilliseconds(System.currentTimeMillis() + jwtExpirationMs));

        if (userDetails instanceof User customUserDetails) {
            claims.setClaim("id", customUserDetails.getId());
            claims.setClaim("email", customUserDetails.getEmail());
            claims.setClaim("role", customUserDetails.getRole());
        }

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(getSigningKey());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        try {
            return jws.getCompactSerialization();
        } catch (JoseException e) {
            throw new RuntimeException("Failed to generate JWT", e);
        }
    }

    public JwtClaims extractAllClaims(String token) throws InvalidJwtException {
        JwtConsumer jwtConsumer =
                new JwtConsumerBuilder()
                        .setVerificationKey(getVerificationKey()) // Your existing getVerificationKey()
                        .build();

        return jwtConsumer.processToClaims(token);
    }

    private NumericDate extractExpiration(String token) {
        try {
            JwtClaims claims = extractAllClaims(token);
            return claims.getExpirationTime();
        } catch (MalformedClaimException | InvalidJwtException e) {
            throw new RuntimeException("Invalid token");
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).isBefore(NumericDate.now());
    }

    public String extractUsername(String token) {
        try {
            JwtClaims claims = extractAllClaims(token);
            return claims.getSubject();
        } catch (MalformedClaimException | InvalidJwtException e) {
            throw new RuntimeException("Invalid token");
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}
