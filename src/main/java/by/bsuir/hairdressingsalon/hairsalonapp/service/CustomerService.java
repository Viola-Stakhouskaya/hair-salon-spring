package by.bsuir.hairdressingsalon.hairsalonapp.service;

import by.bsuir.hairdressingsalon.hairsalonapp.entity.Customer;
import by.bsuir.hairdressingsalon.hairsalonapp.entity.Role;
import by.bsuir.hairdressingsalon.hairsalonapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByLogin(username);
    }

    public Optional<Customer> findByLoginOrEmail(String login, String email) {
        return customerRepository.findByLoginOrEmail(login, email);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer save(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRoles(Collections.singleton(Role.USER));
        // todo: Set gender
        customer.setActive(true);

        customerRepository.save(customer);
        return customer;
    }

    public void updateProfile(Customer original, Customer updated) {
        if (!original.getName().equals(updated.getName())) {
            original.setName(updated.getName());
        }

        if (!original.getSurname().equals(updated.getSurname())) {
            original.setSurname(updated.getSurname());
        }

        if (!original.getLogin().equals(updated.getLogin())) {
            original.setLogin(updated.getLogin());
        }

        if (!updated.getPassword().isEmpty() && !original.getPassword().equals(updated.getPassword())) {
            original.setPassword(passwordEncoder.encode(updated.getPassword()));
        }

        if (!original.getEmail().equals(updated.getEmail())) {
            original.setEmail(updated.getEmail());
        }

        customerRepository.save(original);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
