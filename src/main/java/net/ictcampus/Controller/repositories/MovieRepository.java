package net.ictcampus.Controller.repositories;

import net.ictcampus.Model.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
    @Query("SELECT m FROM Movie m " +
            "JOIN m.genre g " +
            "WHERE g.name LIKE CONCAT('%', :genreName, '%')")
    Iterable<Movie> findByGenreName(@Param("genreName") String genreName);

    @Query("SELECT m FROM Movie m WHERE m.name LIKE CONCAT('%', :name, '%')")
    Iterable<Movie> findByName(@Param("name") String name);
}
