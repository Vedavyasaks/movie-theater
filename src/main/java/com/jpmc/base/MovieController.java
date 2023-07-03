package com.jpmc.base;

import com.jpmc.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class MovieController {

    List<Movie> movies;

    public MovieController() {
        this.movies = new ArrayList<>();
    }

    public Movie getMovieByMovieName(String movieName) {
        Movie movie = null;
        for(Movie currMovie : movies) {
            if((movie.getTitle()).equals(movieName)) {
                movie = currMovie;
            }
        }
        if(movie == null){
            throw new ValidationException("No Movie Found with name "+ movieName);
        }
        return movie;
    }

    void addMovie(Movie movie) {
        movies.add(movie);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
