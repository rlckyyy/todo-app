package relucky.code.technicaltask2.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import relucky.code.technicaltask2.common.payload.request.AuthRequest;
import relucky.code.technicaltask2.config.security.AuthService;
import relucky.code.technicaltask2.domain.dto.UserDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    ResponseEntity<?> register(
            @RequestBody UserDTO userDTO
    ) {
        return ResponseEntity
                .status(201)
                .body(authService.register(userDTO));
    }

    @PostMapping("/refresh-token")
    ResponseEntity<?> refresh(
            @RequestHeader("refresh_token") String refreshToken
    ) {
        return
                ResponseEntity
                        .ok(authService.refresh(refreshToken));
    }

    @PostMapping
    ResponseEntity<?> auth(
            @RequestBody AuthRequest authRequest
    ) {
        return ResponseEntity
                .ok(authService.auth(authRequest));
    }
}
