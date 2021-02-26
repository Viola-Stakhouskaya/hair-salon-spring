package by.bsuir.hairdressingsalon.hairsalonapp.repository;

import by.bsuir.hairdressingsalon.hairsalonapp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
