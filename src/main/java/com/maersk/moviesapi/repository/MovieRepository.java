package com.maersk.moviesapi.repository;

import com.maersk.moviesapi.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query("select m from Movie m where year = ?1")
    List<Movie> findMovieByYear(Integer year);

    @Query("select m from Movie m where rating = ?1")
    List<Movie> findMovieByRating(Float rating);

}
