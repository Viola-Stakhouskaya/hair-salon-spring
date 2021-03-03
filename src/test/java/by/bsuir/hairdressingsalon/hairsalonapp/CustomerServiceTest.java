package by.bsuir.hairdressingsalon.hairsalonapp;

import by.bsuir.hairdressingsalon.hairsalonapp.entity.Customer;
import by.bsuir.hairdressingsalon.hairsalonapp.entity.Role;
import by.bsuir.hairdressingsalon.hairsalonapp.repository.CustomerRepository;
import by.bsuir.hairdressingsalon.hairsalonapp.service.CustomerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void createUserSuccessTest() {
        Customer customer = new Customer();
        customer.setLogin("viola_st");
        customer.setName("Viola");

        customerService.save(customer);

        verify(customerRepository, times(1)).save(customer);

        assertTrue(customer.isActive());
        assertEquals(customer.getName(), "Viola");
        assertTrue(CoreMatchers.is(customer.getRoles()).matches(Collections.singleton(Role.USER)));
    }

    @Test
    void createUserFailTest() {
        String login = "viola_st";
        String email = "viola@email.com";

        Customer customer = new Customer();
        customer.setLogin(login);
        customer.setEmail(email);

        Mockito.doReturn(Optional.of(new Customer()))
               .when(customerRepository)
               .findByLoginOrEmail(login, email);

        boolean isCustomerCreated = customerService.save(customer);

        assertFalse(isCustomerCreated);
    }
}
