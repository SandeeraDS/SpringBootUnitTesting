package org.ds.unittesting.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ds.unittesting.bean.MovieBean;
import org.ds.unittesting.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class MovieControllerTest {

    @MockBean // if our test case depend on Spring container Bean then use @MockBean
    private MovieService movieService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Calendar calendar = Calendar.getInstance();
    private MovieBean harryPotter;
    private MovieBean titanic;
    private MovieBean loadOfTheRings;

    @BeforeEach
    void init() {
        harryPotter = new MovieBean();
        harryPotter.setId(1L);
        harryPotter.setName("Harry Potter 1");
        harryPotter.setGenre("Fantasy");


        calendar.set(Calendar.YEAR, 2000);
        calendar.set(Calendar.MONTH, Calendar.JULY); // Months are 0-based in Calendar
        calendar.set(Calendar.DAY_OF_MONTH, 30);
        harryPotter.setReleasedDate(calendar.getTime());

        titanic = new MovieBean();
        titanic.setName("Titanic");
        titanic.setGenre("Romantic");

        calendar.set(Calendar.YEAR, 1999);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER); // Months are 0-based in Calendar
        calendar.set(Calendar.DAY_OF_MONTH, 25);
        titanic.setReleasedDate(calendar.getTime());

        loadOfTheRings = new MovieBean();
        loadOfTheRings.setName("Lord of the rings");
        loadOfTheRings.setGenre("Fantasy");

        calendar.set(Calendar.YEAR, 2001);
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER); // Months are 0-based in Calendar
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        loadOfTheRings.setReleasedDate(calendar.getTime());

    }


    @Test
    void shouldCreateNewMovie() throws Exception {

        when(movieService.save(any(MovieBean.class))).thenReturn(harryPotter);

        this.mockMvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(harryPotter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(harryPotter.getName())))
                .andExpect(jsonPath("$.genre",is(harryPotter.getGenre())));
    }
}
