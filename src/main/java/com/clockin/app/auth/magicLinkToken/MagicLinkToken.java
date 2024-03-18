package com.clockin.app.auth.magicLinkToken;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "magic_link_tokens") // Specify collection name
public class MagicLinkToken {

    @Id
    String id;

    String token;

    String userEmail; // Replace with appropriate user identifier type (e.g., email, username)

    Long creationTimestamp;
    Long expirationTimestamp;

    boolean isUsed; // Flag to indicate if the token has been used

}
