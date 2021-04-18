package com.maersk.moviesapi.service;

import com.maersk.moviesapi.model.Movie;
import com.maersk.moviesapi.repository.MovieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

    @InjectMocks
    MovieService service;

    @Mock
    MovieRepository repository;

    @Test
    public void tc_001_testSave_whenNewMovieAdded_AndRequestObjectHasMissingAttributes_thenThrowException() {
        Movie movie = Movie.builder().name("Gravity").year(2015).build();
        try {
            service.save(movie);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Request has missing attributes", e.getMessage());
        }
    }

    @Test
    public void tc_002_testSave_whenNewMovieAdded_AndRequestObjectHasAllAttributes_thenCreateNewRecord() {
        Movie movie = Movie.builder().name("Gravity").year(2015).rating(7f).id(1).build();
        when(repository.save(movie)).thenReturn(movie);
        int id = service.save(movie);
        assertEquals(movie.getId(), id);
    }

    @Test
    public void tc_003_testUpdate_whenExistingRecordIsUpdated_thenCreateNewRecord() {
        Movie prevMovie = Movie.builder().name("Gravity").year(2015).rating(7f).id(1).build();
        Movie updatedMovie = Movie.builder().name("Gravity").year(2020).rating(7f).id(1).build();
        when(repository.save(updatedMovie)).thenReturn(updatedMovie);
        service.save(updatedMovie);
        assertEquals(prevMovie.getId(), updatedMovie.getId());
        assertEquals(prevMovie.getName(), updatedMovie.getName());
        assertEquals(prevMovie.getRating(), updatedMovie.getRating());
        assertEquals(Optional.of(2020).get(), updatedMovie.getYear());
    }

    @Test
    public void tc_004_testFindAll() {
        List<Movie> movies = getMovies();
        when(repository.findAll()).thenReturn(movies);
        List<Movie> response = service.getMovies(null, null);
        assertEquals(movies.size(), response.size());
    }

    @Test
    public void tc_005_testFindAllByYear_whenYearIsPassedAsParameter() {
        List<Movie> movies = getMovies().stream().filter(movie -> movie.getYear().equals(2020)).collect(Collectors.toList());
        when(repository.findMovieByYear(2020)).thenReturn(movies);
        List<Movie> response = service.getMovies(2020, null);
        assertEquals(movies.size(), response.size());
    }

    @Test
    public void tc_005_testFindAllByRating_whenRatingIsPassedAsParameter() {
        List<Movie> movies = getMovies().stream().filter(movie -> movie.getRating().equals(7f)).collect(Collectors.toList());
        when(repository.findMovieByRating(7f)).thenReturn(movies);
        List<Movie> response = service.getMovies(null, 7f);
        assertEquals(movies.size(), response.size());
    }

    @Test
    public void tc_006_testFindAll_whenBothRequestParamsArePassedAsParameter() {
        try {
            service.getMovies(2020, 7f);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("bad request", e.getMessage());
        }
    }


    private List<Movie> getMovies() {
        Movie movieOne = Movie.builder().name("Welcome 2 America").year(2021).rating(7f).id(1).build();
        Movie movieTwo = Movie.builder().name("Gravity").year(2020).rating(7f).id(1).build();
        Movie movieThree = Movie.builder().name("Harry Potter").year(2020).rating(9f).id(1).build();
        return new ArrayList<>(Arrays.asList(movieOne, movieTwo, movieThree));
    }
}
