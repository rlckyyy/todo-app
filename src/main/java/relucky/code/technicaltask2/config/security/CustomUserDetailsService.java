package relucky.code.technicaltask2.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import relucky.code.technicaltask2.common.exception.InvalidCredentialsException;
import relucky.code.technicaltask2.domain.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var optional = userRepository.findByEmail(email);
        if (optional.isEmpty()){
            throw new InvalidCredentialsException();
        }
        return optional.get();
    }
}
