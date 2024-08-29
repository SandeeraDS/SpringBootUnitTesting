package org.ds.unittesting.service;


import org.ds.unittesting.bean.MovieBean;
import org.ds.unittesting.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    private MovieBean harryPotter;

    private MovieBean titanic;
    private MovieBean loadOfTheRings;

    @BeforeEach
    void init(){
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


    @InjectMocks // instead of autowired
    private MovieService movieService;

    @Mock // if our test case does not depend on Spring container Bean then use @Mock
    private MovieRepository movieRepository;

    Calendar calendar = Calendar.getInstance();


    @Test
    @DisplayName("Save a new movie")
    void save(){
        when(movieRepository.save(any(MovieBean.class))).thenReturn(harryPotter);

        MovieBean newMovie = movieService.save(harryPotter);
        assertNotNull(newMovie);
        assertEquals(newMovie.getId(),1);
    }

    @Test
    void getMovieList(){
        List<MovieBean> movieBeanList = new ArrayList<>();
        movieBeanList.add(harryPotter);
        movieBeanList.add(titanic);

        when(movieRepository.findAll()).thenReturn(movieBeanList);

        List<MovieBean> allMovies = movieService.getAllMovies();

        assertEquals(2,allMovies.size());
    }

    @Test
    void getMovieById(){

        when(movieRepository.findById(1L)).thenReturn(Optional.ofNullable(harryPotter));
        when(movieRepository.findById(3L)).thenReturn(Optional.empty());

        MovieBean movieBean1 = movieService.getMovieById(1);
        assertNotNull(movieBean1);
        assertEquals("Harry Potter 1",movieBean1.getName());

        Executable executable = () -> movieService.getMovieById(3);;

        assertThrows(RuntimeException.class,executable);
    }

    @Test
    void updateMovie(){
        when(movieRepository.findById(1L)).thenReturn(Optional.ofNullable(harryPotter));
        when(movieRepository.save(any(MovieBean.class))).thenReturn(harryPotter);

        harryPotter.setGenre("action");

        MovieBean movieBean = movieService.updateMovie(harryPotter,1L);
        assertNotNull(movieBean);
        assertEquals("action",movieBean.getGenre());
    }


    @Test
    void deleteMovie(){
        when(movieRepository.findById(1L)).thenReturn(Optional.ofNullable(harryPotter));
        doNothing().when(movieRepository).delete(harryPotter);

        movieService.deleteMovieById(1L);
        verify(movieRepository,times(1)).delete(harryPotter);
    }

}
