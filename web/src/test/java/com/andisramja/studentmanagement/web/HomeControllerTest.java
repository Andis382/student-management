package com.andisramja.studentmanagement.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test integrimi për {@link HomeController} duke përdorur {@code @WebMvcTest}.
 *
 * @author Andis Ramja
 */
@WebMvcTest(HomeController.class)
@DisplayName("Testet për HomeController")
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET / kthen informacionin e API-së me status 200")
    void home_returnsApiInfo() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.autori").value("Andis Ramja"))
                .andExpect(jsonPath("$.aplikacioni").value("Sistemi i Menaxhimit të Studentëve"));
    }
}
