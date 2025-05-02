package org.example.order_service.controller;

import lombok.RequiredArgsConstructor;
import org.example.order_service.dto.UserDTO;
import org.example.order_service.entity.primary.User;
import org.example.order_service.security.UserAuthProvider;
import org.example.order_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDTO request) {
        var user = userService.login(request);
        var token = userAuthProvider.createToken(user.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO request) {
        var user = userService.register(request);
        var token = userAuthProvider.createToken(user.getUsername());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkAuth(@AuthenticationPrincipal User user) {
        if (user == null) return new ResponseEntity<>("Вы не авторизованы", HttpStatus.UNAUTHORIZED);
        return ResponseEntity.ok(user.getUsername());
    }

}
