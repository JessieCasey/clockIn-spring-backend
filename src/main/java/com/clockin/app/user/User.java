package com.clockin.app.user;

import com.clockin.app.card.Card;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import static com.clockin.app.common.ApplicationConstants.Web.Security.TokenClaims.LOGIN_ATTEMPTS;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    String id; // Unique identifier for the user

    String username;

    @Indexed(name = "email_index", unique = true)
    String email;

    String tokenId;

    List<Card> cards;

    int minutesSaved;

    byte loginAttempts; // Number of login attempts
    Long lastLoginAttemptTimestamp; // Timestamp of the last login attempt

    boolean blocked; // Indicates if the user is currently blocked
    Long blockExpirationTimestamp; // Timestamp when the block expires (e.g., after 24 hours)

    public void incrementLoginAttempts() {
        this.loginAttempts++;
    }

    public boolean checkLoginAttempts() {
        return this.loginAttempts < LOGIN_ATTEMPTS;
    }
}

