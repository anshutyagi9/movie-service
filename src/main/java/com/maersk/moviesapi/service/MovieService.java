package com.maersk.moviesapi.service;

import com.maersk.moviesapi.model.Movie;
import com.maersk.moviesapi.repository.MovieRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public List<Movie> getMovies(Integer year, Float rating) {
        List<Movie> movies;
        if (null != year && null != rating) {
            throw new IllegalArgumentException("bad request");
        }
        if (null == year && null == rating) {
            movies = movieRepository.findAll();
        } else if (null != year) {
            movies = movieRepository.findMovieByYear(year);
        } else {
            movies = movieRepository.findMovieByRating(rating);
        }
        return movies;
    }

    public int save(Movie movie) {
        if (null != movie && (StringUtils.isEmpty(movie.getName()) || null == movie.getYear() || null == movie.getRating())) {
            throw new IllegalArgumentException("Request has missing attributes");
        }
        Movie newMovie = movieRepository.save(movie);
        return newMovie.getId();
    }

    public void update(Movie movie, int id) throws RuntimeException {
        Optional<Movie> movieDetails = movieRepository.findById(id);
        if (!movieDetails.isPresent()) {
            throw new RuntimeException("Movie Not Found");
        }
        movieDetails.get().setName(movie.getName());
        movieDetails.get().setRating(movie.getRating());
        movieDetails.get().setYear(movie.getYear());
        movieRepository.save(movieDetails.get());
    }
}
