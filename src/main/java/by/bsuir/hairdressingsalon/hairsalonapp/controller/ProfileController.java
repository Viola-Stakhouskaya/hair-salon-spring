package by.bsuir.hairdressingsalon.hairsalonapp.controller;

import by.bsuir.hairdressingsalon.hairsalonapp.entity.Customer;
import by.bsuir.hairdressingsalon.hairsalonapp.entity.Employee;
import by.bsuir.hairdressingsalon.hairsalonapp.entity.ProcedureAppointment;
import by.bsuir.hairdressingsalon.hairsalonapp.entity.SalonProcedure;
import by.bsuir.hairdressingsalon.hairsalonapp.service.AppointmentService;
import by.bsuir.hairdressingsalon.hairsalonapp.service.CustomerService;
import by.bsuir.hairdressingsalon.hairsalonapp.service.EmployeeService;
import by.bsuir.hairdressingsalon.hairsalonapp.service.SalonProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/profile")
@PreAuthorize(value = "hasAuthority('USER')")
public class ProfileController {

    private final CustomerService customerService;
    private final AppointmentService appointmentService;
    private final SalonProcedureService procedureService;
    private final EmployeeService employeeService;

    @Autowired
    public ProfileController(CustomerService customerService,
                             AppointmentService appointmentService,
                             SalonProcedureService procedureService,
                             EmployeeService employeeService) {
        this.customerService = customerService;
        this.appointmentService = appointmentService;
        this.procedureService = procedureService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String displayCustomerProfilePage(@AuthenticationPrincipal Customer customer, Model model) {
        List<ProcedureAppointment> appointmentsForCustomer = appointmentService.getAppointmentsForCustomer(customer);

        Customer customerData = customerService.findByLoginOrEmail(customer.getLogin(), null).orElseThrow();
        model.addAttribute("customer", customerData);
        model.addAttribute("appointmentsForCustomer", appointmentsForCustomer);

        return "customer/profile";
    }

    @GetMapping("/edit")
    public String showEmployeeEditForm(@AuthenticationPrincipal Customer customer, Model model) {
        model.addAttribute("customer", customer);
        return "customer/profile-edit";
    }

    @PostMapping
    public String updateProfile(@RequestParam(name = "passwordConfirmation") String passwordConfirmation,
                                @AuthenticationPrincipal Customer customer,
                                @Validated Customer updated,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasFieldErrors("name") ||
                bindingResult.hasFieldErrors("surname") ||
                bindingResult.hasFieldErrors("email") ||
                bindingResult.hasFieldErrors("login")) {
            return "customer/profile-edit";
        }

        if (!passwordConfirmation.isBlank()
                && customer.getPassword() != null
                && !customer.getPassword().equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "customer/profile-edit";
        }

        customerService.updateProfile(customer, updated);

        model.addAttribute("customer", updated);
        return "redirect:/profile";
    }

    @GetMapping("/appointments/edit/{id}")
    public String showAppointmentEditForm(@PathVariable Long id, Model model) {
        ProcedureAppointment appointment = appointmentService
                .getAppointmentById(id)
                .orElseThrow();
        List<SalonProcedure> procedures = procedureService.getAllProcedures();
        List<Employee> employees = employeeService.getAllEmployees();

        model.addAttribute("appointment", appointment);
        model.addAttribute("procedures", procedures);
        model.addAttribute("employees", employees);

        return "customer/appointment-edit";
    }

    @PostMapping("/appointments/update/{id}")
    public String updateAppointment(@AuthenticationPrincipal Customer customer,
                                    @PathVariable Long id,
                                    @Validated ProcedureAppointment updatedAppointment,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
            return "customer/appointment-edit";
        }

        updatedAppointment.setId(id);
        updatedAppointment.setSignedUpCustomer(customer);

        appointmentService.save(updatedAppointment);

        return "redirect:/profile";
    }

    @GetMapping("/appointments/cancel/{id}")
    public String cancelAppointment(@AuthenticationPrincipal Customer customer, @PathVariable Long id) {
        appointmentService.cancelAppointment(customer, id);

        return "redirect:/profile";
    }
}
