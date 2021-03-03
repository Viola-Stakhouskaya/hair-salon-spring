package by.bsuir.hairdressingsalon.hairsalonapp;

import by.bsuir.hairdressingsalon.hairsalonapp.controller.HomeController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SampleTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HomeController homeController;

    @Test
    void contextAvailabilityTest() {
        assertThat(homeController).isNotNull();
    }

    @Test
    void registrationControllerTest() throws Exception {
        this.mockMvc.perform(get("/registration"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("security/registration"))
                    .andExpect(content().contentType("text/html;charset=UTF-8"))
                    .andExpect(content().string(containsString("Регистрация")));
    }

    @Test
    void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/profile"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("http://localhost/login"));
    }


    @Test
    void loginSuccessTest() throws Exception {
        this.mockMvc.perform(formLogin().user("viola").password("viola"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));
    }

    @Test
    void badCredentialsError() throws Exception {
        this.mockMvc.perform(post("/login").param("login", "Invalid"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("viola")
    void userAuthenticatedTest() throws Exception {
        this.mockMvc.perform(get("/profile"))
                    .andDo(print())
                    .andExpect(authenticated());
    }

}
