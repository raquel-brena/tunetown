package com.imd.backend.infra.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtService {
    private static final String SECRET_KEY = "PLACEHOLDER";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    public String generateAccessToken(TuneUserDetails userDetails) {
        return JWT.create()
                .withIssuedAt(generateCreationDate())
                .withExpiresAt(generateExpirationDate())
                .withSubject(userDetails.getUsername())
                .sign(ALGORITHM);
    }

    public String extractUsername(String token) {
        return decodeToken(token).getSubject();
    }

    private DecodedJWT decodeToken(String token) {
        return JWT.require(ALGORITHM)
                .build()
                .verify(token);
    }

    private Instant generateCreationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).toInstant();
    }

    private Instant generateExpirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).toInstant().plusMillis(EXPIRATION_TIME);
    }
}

