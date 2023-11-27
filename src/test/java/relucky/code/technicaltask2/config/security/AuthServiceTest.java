package relucky.code.technicaltask2.config.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import relucky.code.technicaltask2.common.exception.EmailAlreadyRegistered;
import relucky.code.technicaltask2.common.payload.request.AuthRequest;
import relucky.code.technicaltask2.common.payload.response.TokenResponse;
import relucky.code.technicaltask2.domain.dto.UserDTO;
import relucky.code.technicaltask2.domain.entity.User;
import relucky.code.technicaltask2.domain.mapper.UserMapper;
import relucky.code.technicaltask2.domain.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager manager;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testRegisterWhenUserNotRegisteredThenUserRegistered() {
        // Arrange
        UserDTO dto = new UserDTO("John Doe", "john.doe@example.com", 30, "password");
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setAge(dto.age());
        user.setPassword(dto.password());

        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(userMapper.toModel(dto)).thenReturn(user);

        // Act
        Map<String, Object> result = authService.register(dto);

        // Assert
        assertEquals(user, result.get("user"));
        verify(userRepository).findByEmail(dto.email());
        verify(userMapper).toModel(dto);
    }

    @Test
    public void testRegisterWhenUserAlreadyRegisteredThenEmailAlreadyRegistered() {
        // Arrange
        UserDTO dto = new UserDTO("John Doe", "john.doe@example.com", 30, "password");
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setAge(dto.age());
        user.setPassword(dto.password());

        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(EmailAlreadyRegistered.class, () -> authService.register(dto));
        verify(userRepository).findByEmail(dto.email());
        verify(userMapper, never()).toModel(dto);
    }
}