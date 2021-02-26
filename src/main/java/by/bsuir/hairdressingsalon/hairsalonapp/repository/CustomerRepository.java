package by.bsuir.hairdressingsalon.hairsalonapp.repository;

import by.bsuir.hairdressingsalon.hairsalonapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // @EntityGraph(attributePaths = {"scheduledAppointments", "roles"})
    Customer findByLogin(String login);

    Optional<Customer> findByLoginOrEmail(String login, String email);

}
