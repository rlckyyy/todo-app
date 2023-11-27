package relucky.code.technicaltask2.domain.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import relucky.code.technicaltask2.common.enums.Role;
import relucky.code.technicaltask2.domain.entity.User;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testFindByEmail_WhenEmailExists_ShouldReturnUser() {
        //given
        User user = new User(1L, "Yernur","reluckytryhrd@gmail.com",18,"Ernur777", Role.USER, null);
        userRepository.save(user);
        //when
        Optional<User> foundUser = userRepository.findByEmail("reluckytryhrd@gmail.com");
        //then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("reluckytryhrd@gmail.com");
        assertThat(foundUser.get().getName()).isEqualTo("Yernur");
    }

    @Test
    void  testFindByEmail_WhenEmailDontExist_ShouldReturnEmptyOptional(){
        // given null user email
        Optional<User> foundUser = userRepository.findByEmail("bekagay@gmail.com");
        // when trynna take null user value
        //then should be empty list
        assertThat(foundUser).isNotPresent();
    }
}