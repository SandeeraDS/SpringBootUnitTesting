package org.ds.unittesting.controller;


import org.ds.unittesting.bean.MovieBean;
import org.ds.unittesting.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping
    public MovieBean create(@RequestBody MovieBean movieBean) {
        return movieService.save(movieBean);
    }


    @GetMapping
    public List<MovieBean> getAll() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public MovieBean getById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        movieService.deleteMovieById(id);
    }

    @PutMapping("/{id}")
    public MovieBean updateById(@RequestBody MovieBean movieBean,@PathVariable long id){
        return movieService.updateMovie(movieBean,id);
    }
}
