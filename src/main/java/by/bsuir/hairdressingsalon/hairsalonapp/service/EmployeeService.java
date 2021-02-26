package by.bsuir.hairdressingsalon.hairsalonapp.service;

import by.bsuir.hairdressingsalon.hairsalonapp.entity.Employee;
import by.bsuir.hairdressingsalon.hairsalonapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AppointmentService appointmentService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           AppointmentService appointmentService) {
        this.employeeRepository = employeeRepository;
        this.appointmentService = appointmentService;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
}
