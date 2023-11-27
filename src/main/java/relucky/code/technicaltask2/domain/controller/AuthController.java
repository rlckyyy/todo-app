package relucky.code.technicaltask2.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import relucky.code.technicaltask2.common.payload.request.AuthRequest;
import relucky.code.technicaltask2.config.security.AuthService;
import relucky.code.technicaltask2.domain.dto.UserDTO;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    ResponseEntity<?> register(
            @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body("Validation failed: " + bindingResult.getAllErrors());
        }
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
