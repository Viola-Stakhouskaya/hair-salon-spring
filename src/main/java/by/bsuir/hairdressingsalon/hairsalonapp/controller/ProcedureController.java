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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/procedure")
public class ProcedureController {

    private final AppointmentService appointmentService;
    private final SalonProcedureService procedureService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;

    @Autowired
    public ProcedureController(AppointmentService appointmentService,
                               SalonProcedureService procedureService,
                               EmployeeService employeeService,
                               CustomerService customerService) {
        this.appointmentService = appointmentService;
        this.procedureService = procedureService;
        this.employeeService = employeeService;
        this.customerService = customerService;
    }

    @GetMapping("/signup")
    public String showProcedureSignUpForm(@AuthenticationPrincipal Customer customer, Model model) {
        List<SalonProcedure> procedures = procedureService.getAllProcedures();
        List<Employee> employees = employeeService.getAllEmployees();
        ProcedureAppointment procedureAppointment = new ProcedureAppointment();

        model.addAttribute("customer", customer);
        model.addAttribute("procedures", procedures);
        model.addAttribute("employees", employees);
        model.addAttribute("appointment", procedureAppointment);

        return "procedure/procedure-signup";
    }

    @PostMapping("/signup")
    public String signUpForProcedure(@AuthenticationPrincipal Customer customer,
                                     ProcedureAppointment procedureAppointment,
                                     Model model) {
        procedureAppointment.setSignedUpCustomer(customer);
        appointmentService.save(procedureAppointment);
        System.out.println(procedureAppointment.toString());
        return "redirect:/profile";
    }
}
