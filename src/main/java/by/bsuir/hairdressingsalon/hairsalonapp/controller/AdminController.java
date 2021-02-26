package by.bsuir.hairdressingsalon.hairsalonapp.controller;

import by.bsuir.hairdressingsalon.hairsalonapp.entity.*;
import by.bsuir.hairdressingsalon.hairsalonapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@PreAuthorize(value = "hasAuthority('ADMIN')")
public class AdminController {

    private final EmployeeService employeeService;
    private final SalonProcedureService procedureService;
    private final GenderService genderService;
    private final AppointmentService appointmentService;
    private final CustomerService customerService;

    @Autowired
    public AdminController(EmployeeService employeeService,
                           SalonProcedureService procedureService,
                           GenderService genderService,
                           AppointmentService appointmentService,
                           CustomerService customerService) {
        this.employeeService = employeeService;
        this.procedureService = procedureService;
        this.genderService = genderService;
        this.appointmentService = appointmentService;
        this.customerService = customerService;
    }

    @GetMapping
    public String showAdminControlPage(@AuthenticationPrincipal Customer admin, Model model) {
        model.addAttribute("admin", admin);
        return "admin/admin-control";
    }

    @GetMapping("/employees")
    public String showEmployeeManagementPage(@AuthenticationPrincipal Customer admin, Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);

        return "admin/employee-management";
    }

    @GetMapping("/employees/new")
    public String showEmployeeCreationForm(@AuthenticationPrincipal Customer admin, Model model) {
        Employee employee = new Employee();
        List<SalonProcedure> procedures = procedureService.getAllProcedures();
        List<Gender> genders = genderService.getAllGenders();
        model.addAttribute("employee", employee);
        model.addAttribute("procedures", procedures);
        model.addAttribute("genders", genders);

        return "admin/new-employee";
    }

    @PostMapping("/employees/save")
    public String createEmployee(Employee employee, BindingResult bindingResult, Model model) {
        employeeService.save(employee);

        return "redirect:/admin/employees";
    }

    @GetMapping("/employees/edit/{id}")
    public String showEmployeeEditForm(@PathVariable Long id, Model model) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        List<SalonProcedure> procedures = procedureService.getAllProcedures();
        List<Gender> genders = genderService.getAllGenders();

        employee.ifPresent(value -> model.addAttribute("employee", value));
        model.addAttribute("procedures", procedures);
        model.addAttribute("genders", genders);

        return "admin/employee-edit";
    }

    @PostMapping("/employees/update/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @Validated Employee updatedEmployee,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
            List<SalonProcedure> procedures = procedureService.getAllProcedures();
            List<Gender> genders = genderService.getAllGenders();

            model.addAttribute("procedures", procedures);
            model.addAttribute("genders", genders);
            return "admin/employee-edit";
        }

        updatedEmployee.setId(id);
        employeeService.save(updatedEmployee);

        return "redirect:/admin/employees";
    }

    @GetMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, Model model) {
        employeeService.deleteById(id);

        return "redirect:/admin/employees";
    }

    @GetMapping("/appointments")
    public String showAppointmentsManagementPage(@AuthenticationPrincipal Customer admin, Model model) {
        List<ProcedureAppointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);

        return "admin/appointment-management";
    }

    @GetMapping("/appointments/edit/{aId}/customer/{cId}")
    public String showAppointmentEditForm(@PathVariable(name = "aId") Long appointmentId,
                                          @PathVariable(name = "cId") Long customerId,
                                          Model model) {
        ProcedureAppointment appointment = appointmentService
                .getAppointmentById(appointmentId)
                .orElseThrow();
        Customer customer = appointment.getSignedUpCustomer();

        List<SalonProcedure> procedures = procedureService.getAllProcedures();
        List<Employee> employees = employeeService.getAllEmployees();

        model.addAttribute("appointment", appointment);
        model.addAttribute("procedures", procedures);
        model.addAttribute("employees", employees);
        model.addAttribute("customer", customer);

        return "admin/appointment-edit-admin";
    }

    @PostMapping("/appointments/update/{id}")
    public String updateAppointment(@PathVariable Long id,
                                    ProcedureAppointment updatedAppointment,
                                    @RequestParam(value = "customerId") Long customerId,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
            return "admin/appointment-edit-admin";
        }

        updatedAppointment.setId(id);

        Customer customer = customerService.getCustomerById(customerId).orElseThrow();
        updatedAppointment.setSignedUpCustomer(customer);

        appointmentService.save(updatedAppointment);

        return "redirect:/admin/appointments";
    }

    @GetMapping("/appointments/cancel/{id}")
    public String cancelAppointment(@AuthenticationPrincipal Customer customer, @PathVariable Long id) {
        appointmentService.cancelAppointment(customer, id);

        return "redirect:/admin/appointments";
    }
}
