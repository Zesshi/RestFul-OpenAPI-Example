package net.ictcampus.Controller.services;

import net.ictcampus.Model.Genre;
import net.ictcampus.Controller.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class GenreService {
    @Autowired
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Iterable<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Genre findById(Integer id) {
        Optional<Genre> tag = genreRepository.findById(id);
        return tag.orElseThrow(EntityNotFoundException::new);
    }

    public Iterable<Genre> findByName(String name) {
        return genreRepository.findByName(name);
    }

    public void insert(Genre tag) {
        genreRepository.save(tag);
    }

    public void update(Genre tag) {
        genreRepository.save(tag);
    }

    public void deleteById(Integer id) {
        genreRepository.deleteById(id);
    }
}
