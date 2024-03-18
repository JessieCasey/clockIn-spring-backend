package com.clockin.app.auth.service;

import com.clockin.app.auth.magicLinkToken.MagicLinkToken;
import com.clockin.app.auth.magicLinkToken.MagicLinkTokenRepository;
import com.clockin.app.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static com.clockin.app.common.ApplicationConstants.Web.Security.JwtClaims.EXPIRATION_IN_MS;
import static com.clockin.app.common.ApplicationConstants.Web.Security.JwtClaims.SECRET_KEY;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MagicLinkTokenRepository magicLinkTokenRepository;


    public boolean validateMagicLinkToken(String token) {
        Optional<MagicLinkToken> tokenEntry = magicLinkTokenRepository.findByToken(token);
        if (tokenEntry.isEmpty()) {
            return false;
        }
        MagicLinkToken magicLinkToken = tokenEntry.get();
        return magicLinkToken.getExpirationTimestamp() >= Instant.now().toEpochMilli();
    }

    public String generateJwtToken(User user) {
        try {
            Claims claims = Jwts.claims()
                    .subject(user.getEmail())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + EXPIRATION_IN_MS))
                    .add("userId", user.getId())
                    .build(); // Add additional claims as needed

            // Create JWT using HS256 algorithm and secret key
            return Jwts.builder()
                    .claims(claims)
                    .signWith(SECRET_KEY)
                    .compact();
        } catch (Exception e) {
            log.error("Error generating JWT token", e);
            return null;
        }
    }



}
