package relucky.code.technicaltask2.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import relucky.code.technicaltask2.common.enums.Role;
import relucky.code.technicaltask2.common.payload.request.AuthRequest;
import relucky.code.technicaltask2.common.payload.response.TokenResponse;
import relucky.code.technicaltask2.domain.dto.UserDTO;
import relucky.code.technicaltask2.domain.entity.User;
import relucky.code.technicaltask2.domain.mapper.UserMapper;
import relucky.code.technicaltask2.domain.repository.UserRepository;

import java.util.Map;

import static relucky.code.technicaltask2.common.enums.TokenType.ACCESS;
import static relucky.code.technicaltask2.common.enums.TokenType.REFRESH;
import static relucky.code.technicaltask2.config.security.MapUtils.toClaims;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager manager;
    private final CustomUserDetailsService userDetailsService;

    public Map<String, Object> save(UserDTO dto) {
        var model = userMapper.toModel(dto);
        model.setPassword(passwordEncoder.encode(dto.getPassword()));
        model.setRole(Role.USER);
        var user = userRepository.save(model);
        return Map.of("user", user, "tokens", tokenResponse(user));
    }

    public Map<String, Object> refresh(String refreshToken) {
        var email = jwtService.extractUsername(refreshToken , REFRESH);
        var user = userDetailsService.loadUserByUsername(email);
        return Map.of("tokens" ,tokenResponse((User) user));
    }

    public Map<String, Object> auth(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken ur = new
                UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password());
        var user = (User) manager.authenticate(ur).getPrincipal();
        return Map.of("tokens", tokenResponse(user));
    }

    private TokenResponse tokenResponse(User user) {
        return new TokenResponse(
                jwtService.generateToken(ACCESS, toClaims(user, ACCESS), user.getEmail()),
                jwtService.generateToken(REFRESH, toClaims(user, REFRESH), user.getEmail())
        );
    }
}
