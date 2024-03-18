package com.clockin.app.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Contains various constants used in the messenger application.
 */
@UtilityClass
public class ApplicationConstants {

    /**
     * Inner utility class for constants related to resetting passwords.
     */
    @UtilityClass
    public class PasswordRecovery {
        public static final String RESET_PASSWORD_LINK = "http://167.172.106.186/users/reset-password?token=";
        public static final String SEND_TO_EMAIL_GENERAL = "info.callidus.eu@gmail.com";
        public static final String LETTER_SUBJECT = "Password reset";
        public static final String ACCOUNT_SUBJECT = "New account created";
    }

    /**
     * Inner utility class for validation-related constants.
     */
    @UtilityClass
    public class Validation {
        /**
         * A regular expression for validating email addresses.
         */
        public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        public static final Pattern EMAIL_PATTERN = Pattern.compile(Validation.EMAIL_REGEX);
        public static final String DATE_PATTERN = "dd.MM.yyyy";
        public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    }

    /**
     * Inner utility class for web-related constants.
     */
    @UtilityClass
    public class Web {
        @UtilityClass
        public class Configuration {
            public static final String DOMAIN_NAME = "http://localhost:5173";
        }

        /**
         * Inner utility class for dto validation.
         */
        @UtilityClass
        public class DataValidation {
            public static final int MIN_SIZE_OF_PASSWORD = 6;
            public static final int MAX_SIZE_OF_PASSWORD = 255;
            public static final int MIN_SIZE_OF_LOGIN = 2;
            public static final int MAX_SIZE_OF_LOGIN = 50;
            public static final int MAX_SIZE_OF_USERNAME = 100;
            public static final int MAX_SIZE_OF_COMPANY_NAME = 100;
        }

        @UtilityClass
        public class TimeZone {
            public static final ZoneId GLOBAL_TIME_ZONE_ID = ZoneId.of("Europe/Warsaw");
        }

        @UtilityClass
        public class MessageResponse {
            public static final String OBJECT_EXPIRED = "is expired at";
            public static final String OBJECT_WARNING = "is about to expire at";
        }

        /**
         * Const's for basic authentication.
         */
        @UtilityClass
        public class BasicAuthentication {
            public static final String AUTHORIZATION_HEADER = "Authorization";
            public static final String AUTHORIZATION_PREFIX = "Bearer ";
        }

        /**
         * Inner utility class for constants related to security part.
         */
        @UtilityClass
        public class Security {
            @UtilityClass
            public class TokenClaims {
                //                public static final long EXPIRATION_TIME = TimeUnit.DAYS.toMillis(24);  // expiration time (e.g., 24 days)
                public static final long EXPIRATION_TIME = TimeUnit.DAYS.toMillis(3);  // expiration time (e.g., 24 days)

                public static final int TOKEN_LENGTH = 32; // Length of the token in bytes
                public static final byte LOGIN_ATTEMPTS = 5; // Length of the token in bytes
            }

            /**
             * Inner utility class for constants related to security jwt claims part.
             */
            @UtilityClass
            public class JwtClaims {

                //                public static final String SECRET_KEY_STRING = "YourSecretKeyHere"; // Replace with your actual secret key
//                public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build(); // super secure
                private static final String SECRET_KEY_STRING = "YourSecretKeyString";
                public static final SecretKey SECRET_KEY = generateSecretKey(SECRET_KEY_STRING);

                private static SecretKey generateSecretKey(String secretKeyString) {
                    try {
                        // Use SHA-256 hash function to generate a consistent key based on the provided string
                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        byte[] bytes = digest.digest(secretKeyString.getBytes(StandardCharsets.UTF_8));

                        // Truncate the bytes to fit the required key length for HMAC-SHA256
                        byte[] truncatedBytes = Arrays.copyOf(bytes, 32); // 32 bytes for HMAC-SHA256
                        return new SecretKeySpec(truncatedBytes, "HmacSHA256");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        // Handle exception appropriately
                        return null;
                    }
                }

                public static final int SIZE_OF_BLACKLIST = 40;
                public static final int EXPIRATION_IN_MS = 3600000; // 1 hour
            }

        }
    }
}
