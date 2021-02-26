package by.bsuir.hairdressingsalon.hairsalonapp.repository;

import by.bsuir.hairdressingsalon.hairsalonapp.entity.Customer;
import by.bsuir.hairdressingsalon.hairsalonapp.entity.Employee;
import by.bsuir.hairdressingsalon.hairsalonapp.entity.ProcedureAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<ProcedureAppointment, Long> {

    List<ProcedureAppointment> findProcedureAppointmentsByPerformingEmployee(Employee employee);

    List<ProcedureAppointment> findProcedureAppointmentsBySignedUpCustomer(Customer customer);
}
