package com.clockin.app.auth.magicLinkToken;

import com.clockin.app.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MagicLinkTokenRepository extends MongoRepository<MagicLinkToken, String> {
    Optional<MagicLinkToken> findByToken(String token);
}
