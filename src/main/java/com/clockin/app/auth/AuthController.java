package com.clockin.app.auth;

import com.clockin.app.auth.service.AuthService;
import com.clockin.app.user.User;
import com.clockin.app.user.dto.UserRequestDTO;
import com.clockin.app.user.dto.UserResponseDTO;
import com.clockin.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    public final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRequestDTO user) {
        User newUser = User.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .minutesSaved(0)
                .build();
        userService.registerUser(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestParam String email) {
        userService.initiateLogin(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login/confirm")
    public ResponseEntity<Map<String, String>> confirmLogin(@RequestParam String email, @RequestParam String token) {
        try {
            boolean isValidToken = authService.validateMagicLinkToken(token); // Implement your validation logic

            if (!isValidToken) {
                log.info("Token is expired");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            User user = userService.getUserFromToken(token); // Implement if you store user information in the token
            String jwtToken = authService.generateJwtToken(user);

            return ResponseEntity.ok(Collections.singletonMap("access_token", jwtToken));
        } catch (Exception e) {
            log.error("Error during login confirmation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfileByToken(@RequestHeader("Authorization") String token) {
        log.info("getUserProfileByToken: " + token);
        return ResponseEntity.ok(new UserResponseDTO());
    }


}
