package by.bsuir.hairdressingsalon.hairsalonapp.controller;

import by.bsuir.hairdressingsalon.hairsalonapp.entity.Customer;
import by.bsuir.hairdressingsalon.hairsalonapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final CustomerService customerService;

    @Autowired
    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/registration")
    public String displayRegistrationForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "security/registration";
    }

    @PostMapping(value = "/registration")
    public String registerUser(@RequestParam(name = "passwordConfirmation") String passwordConfirmation,
                               @Validated Customer customer,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
            return "security/registration";
        }

        if (customer.getPassword() != null && !customer.getPassword().equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "security/registration";
        }

        if (!customerService.save(customer)) {
            model.addAttribute("registrationError", "Этот логин/имейл уже используется");
            return "security/registration";
        }

        return "redirect:/login";
    }
}
