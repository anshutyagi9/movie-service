package com.maersk.moviesapi.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Movie")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(updatable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Float rating;

    @Column(nullable = false)
    private Integer year;
}
