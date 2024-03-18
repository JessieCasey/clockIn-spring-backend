package com.clockin.app.user.service;

import com.clockin.app.auth.JwtUtils;
import com.clockin.app.auth.magicLinkToken.MagicLinkToken;
import com.clockin.app.auth.magicLinkToken.MagicLinkTokenRepository;
import com.clockin.app.exception.wrappers.TokenNotFoundException;
import com.clockin.app.exception.wrappers.UserAlreadyExistException;
import com.clockin.app.exception.wrappers.UserNotExistException;
import com.clockin.app.user.User;
import com.clockin.app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static com.clockin.app.common.ApplicationConstants.Web.Configuration.DOMAIN_NAME;
import static com.clockin.app.common.ApplicationConstants.Web.Security.TokenClaims.EXPIRATION_TIME;
import static com.clockin.app.common.ApplicationConstants.Web.Security.TokenClaims.TOKEN_LENGTH;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MagicLinkTokenRepository magicLinkTokenRepository;
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }



    public void registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("Email already registered");
        }
        MagicLinkToken magicLinkToken = generateMagicLinkToken(user.getEmail());
        user.setTokenId(magicLinkToken.getId());
        userRepository.save(user);
        sendEmailWithMagicLink(magicLinkToken);
    }

    private static String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    private void sendEmailWithMagicLink(MagicLinkToken linkToken) {
        String link = String.format("%s/authorization/confirm?email=%s&token=%s", DOMAIN_NAME, linkToken.getUserEmail(), linkToken.getToken());
        String emailBody = "Click the link below to complete your registration:\n" + link;
        System.out.println(emailBody);
    }

    public User getUserFromToken(String token) {
        Optional<MagicLinkToken> byToken = magicLinkTokenRepository.findByToken(token);
        if (byToken.isPresent()) {
            return userRepository.findByEmail(byToken.get().getUserEmail());
        } else {
            throw new TokenNotFoundException("Token is not found");
        }
    }

    public String getEmailFromToken(String token) {
        Optional<MagicLinkToken> tokenEntry = magicLinkTokenRepository.findByToken(token);
        if (tokenEntry.isEmpty()) {
            return null;
        }

        MagicLinkToken magicLinkToken = tokenEntry.get();
        return magicLinkToken.getUserEmail(); // Replace with appropriate user identifier if different
    }

    public void initiateLogin(String email) {
        User user = findUserByEmail(email);
        if (user == null) {
            throw new UserNotExistException("User is not found");
        }
        MagicLinkToken magicLinkToken = generateMagicLinkToken(user.getEmail()); // Replace with actual token generation
        sendEmailWithMagicLink(magicLinkToken); // Implement email sending mechanism
    }

    private MagicLinkToken generateMagicLinkToken(String userEmail) {
        String token = generateToken();
        long currentTimestamp = Instant.now().toEpochMilli();
        Long expirationTimestamp = currentTimestamp + EXPIRATION_TIME;

        MagicLinkToken magicLinkToken = MagicLinkToken.builder().token(token).userEmail(userEmail).creationTimestamp(currentTimestamp).expirationTimestamp(expirationTimestamp).isUsed(false).build();

        return magicLinkTokenRepository.save(magicLinkToken); // Replace with your actual repository access
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotExistException("User is not found by id: " + userId));
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUserProfileByToken(String token) {
        String userIdFromToken = JwtUtils.extractUserIdFromToken(token);
        return getUserById(userIdFromToken);
    }
}
