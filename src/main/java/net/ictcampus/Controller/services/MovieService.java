package net.ictcampus.Controller.services;

import net.ictcampus.Model.Movie;
import net.ictcampus.Controller.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Iterable<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie findById(Integer id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.orElseThrow(EntityNotFoundException::new);
    }

    public Iterable<Movie> findByGenreName(String genreName) {
        return movieRepository.findByGenreName(genreName);
    }

    public Iterable<Movie> findByName(String name) {
        return movieRepository.findByName(name);
    }

    public void insert(Movie item) {
        movieRepository.save(item);
    }

    public void update(Movie item) {
        movieRepository.save(item);
    }

    public void deleteById(Integer id) {
        movieRepository.deleteById(id);
    }
}
