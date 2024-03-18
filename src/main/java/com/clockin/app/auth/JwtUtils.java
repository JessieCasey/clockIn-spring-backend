package com.clockin.app.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import static com.clockin.app.common.ApplicationConstants.Web.Security.JwtClaims.SECRET_KEY;

@Slf4j
public class JwtUtils {

    public static String extractUserIdFromToken(String token) {
        token = extractTokenFromHeader(token);
        Jws<Claims> claimsJws = Jwts.parser().verifyWith(SECRET_KEY)
                .build().parseSignedClaims(token);

        String userId = claimsJws.getPayload().get("userId", String.class);
        return userId;
    }

    private static String extractTokenFromHeader(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }
        return null;
    }
}
