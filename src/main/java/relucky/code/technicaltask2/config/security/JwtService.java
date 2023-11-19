package relucky.code.technicaltask2.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import relucky.code.technicaltask2.domain.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final UserRepository userRepository;
}
