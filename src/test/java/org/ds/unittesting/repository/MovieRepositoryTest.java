package org.ds.unittesting.repository;

import org.ds.unittesting.bean.MovieBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest
@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;
    Calendar calendar = Calendar.getInstance();
    private MovieBean harryPotter;
    private MovieBean titanic;
    private MovieBean loadOfTheRings;

    @BeforeEach
    void init(){
        harryPotter = new MovieBean();
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
    @DisplayName("Save new movie")
    void save() {
        //given

        //when
        MovieBean newMovie = movieRepository.save(harryPotter);
        //then
        assertNotNull(newMovie);
        assertNotEquals(newMovie.getId(), 0);
    }

    @Test
    @DisplayName("Get All Movies")
    void getAllMovies() {
        //given
        movieRepository.save(harryPotter);
        movieRepository.save(titanic);
        // when
        List<MovieBean> movieList = movieRepository.findAll();
        //then
        assertNotNull(movieList);
        assertEquals(movieList.size(),2);
    }


    @Test
    @DisplayName("get movie id")
    void getMovieById() {
        //given
        movieRepository.save(harryPotter);
        //when
        Optional<MovieBean> existingMovie = movieRepository.findById(harryPotter.getId());
        // then
        assertTrue(existingMovie.isPresent());
    }

    @Test
    @DisplayName("update movie")
    void updateMovie() {
        //given
        movieRepository.save(harryPotter);
        //when
        MovieBean existingMovie = movieRepository.findById(harryPotter.getId()).get();
        existingMovie.setGenre("Romantic");
        MovieBean updatedMovie = movieRepository.save(existingMovie);
        // then
        assertEquals(updatedMovie.getGenre(),"Romantic");
    }


    @Test
    @DisplayName("delete by id")
    void deleteMovieById() {
        //given
        movieRepository.save(harryPotter);
        movieRepository.save(titanic);
        // when
        movieRepository.deleteById(titanic.getId());
        Optional<MovieBean> movieBean1 = movieRepository.findById(titanic.getId());
        List<MovieBean> movieList = movieRepository.findAll();
        //then
        assertFalse(movieBean1.isPresent());
        assertEquals(movieList.size(),1);
    }


    @Test
    @DisplayName("Get Movies Genera")
    void findByGenre() {
        //given
        movieRepository.save(harryPotter);
        movieRepository.save(titanic);
        movieRepository.save(loadOfTheRings);
        // when
        List<MovieBean> movieBeanList = movieRepository.findByGenre("Fantasy");
        //then
        assertNotNull(movieBeanList);
        assertEquals(movieBeanList.size(),2);
    }

}
