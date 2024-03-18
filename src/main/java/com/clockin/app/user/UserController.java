package com.clockin.app.user;


import com.clockin.app.common.mapper.UserMapper;
import com.clockin.app.user.dto.UserResponseDTO;
import com.clockin.app.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        User userById = userService.getUserById(userId);
        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTO(userById);
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfileByToken(@RequestHeader("Authorization") String token) {
        User userById = userService.getUserProfileByToken(token);
        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTO(userById);
        return ResponseEntity.ok(userResponseDTO);
    }

}
