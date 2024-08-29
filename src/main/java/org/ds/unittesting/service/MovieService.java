package org.ds.unittesting.service;

import org.ds.unittesting.bean.MovieBean;
import org.ds.unittesting.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public MovieBean save(MovieBean movieBean){
        return movieRepository.save(movieBean);
    }

    public List<MovieBean> getAllMovies(){
        return movieRepository.findAll();
    }

    public MovieBean getMovieById(long id){
        return movieRepository.findById(id).orElseThrow(()->new RuntimeException("Movie not found for id = "+id));
    }

    public MovieBean updateMovie(MovieBean movieBean,Long id){
        MovieBean existingMovie = getMovieById(id);

        existingMovie.setName(existingMovie.getName());
        existingMovie.setGenre(movieBean.getGenre());
        existingMovie.setReleasedDate(movieBean.getReleasedDate());

        return movieRepository.save(existingMovie);
    }

    public MovieBean deleteMovieById(long id){
        MovieBean existingMovie = getMovieById(id);
        movieRepository.delete(existingMovie);
        return existingMovie;
    }
}
