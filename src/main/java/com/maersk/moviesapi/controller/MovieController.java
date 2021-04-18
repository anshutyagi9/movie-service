package com.maersk.moviesapi.controller;

import com.maersk.moviesapi.model.Movie;
import com.maersk.moviesapi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/movies")
public class MovieController {

    @Autowired
    MovieService service;

    @PostMapping
    public ResponseEntity<Integer> createMovie(@RequestBody Movie movie) {
        try {
            return new ResponseEntity<>(service.save(movie), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie, @PathVariable int id) {
        try {
            service.update(movie, id);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getMovies(@RequestParam(required = false) Integer year, @RequestParam(required = false) Float rating) {
        return new ResponseEntity<>(service.getMovies(year, rating), HttpStatus.OK);
    }
}
