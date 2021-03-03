package by.bsuir.hairdressingsalon.hairsalonapp;

import by.bsuir.hairdressingsalon.hairsalonapp.config.BeanMaker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// @ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
// @MockBean(BeanMaker.class)
@SpringBootTest
public class UnitTest {

    @Autowired
    private BeanMaker beanMaker;

    @Test
    void passwordEncoderTest() {
        PasswordEncoder passwordEncoder = beanMaker.getPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("test");

        Assertions.assertTrue(passwordEncoder.matches("test", encodedPassword));
    }
}
