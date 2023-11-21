package relucky.code.technicaltask2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import relucky.code.technicaltask2.common.enums.Role;
import relucky.code.technicaltask2.domain.entity.User;
import relucky.code.technicaltask2.domain.repository.UserRepository;

@SpringBootApplication
public class TechnicalTask2Application {

    public static void main(String[] args) {
        SpringApplication.run(TechnicalTask2Application.class, args);
    }
}
