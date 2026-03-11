package com.example.demo;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest 
@AutoConfigureMockMvc 
public class ControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @Test
    void testGetKredyt_Sukces() throws Exception {
        mockMvc.perform(get("/kredyt")
                .param("kwota", "10000")
                .param("procent", "0.1")
                .param("rat", "12"))
                .andExpect(status().isOk()) // Oczekujemy statusu 200 OK
                .andExpect(content().string(containsString("Wynik raty:")));
    }

    @Test
    void testGetKredyt_BladLiter() throws Exception {
        mockMvc.perform(get("/kredyt")
                .param("kwota", "nie_liczba") //  tekst zamiast liczby
                .param("procent", "0.1")
                .param("rat", "12"))
                .andExpect(status().isOk())
                .andExpect(content().string("Błąd, wprowadzone dane nie są liczbami"));
    }

    @Test
    void testGetKredyt_LiczbyUjemne() throws Exception {
        mockMvc.perform(get("/kredyt")
                .param("kwota", "-5000") // liiczba ujemna
                .param("procent", "0.1")
                .param("rat", "12"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Złe liczb")));
    }
}
