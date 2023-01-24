package net.ictcampus.Controller.controllers;

import net.ictcampus.Controller.services.MovieService;
import net.ictcampus.Model.Movie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @Operation(summary = "Find movie(s) by Name and/or Genre Name. With no given arguments all movies are returned.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie(s) found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))
            })
    })
    @GetMapping
    public Iterable<Movie> findByNameAndGenreName(@RequestParam(value="name", required = false) String name, @RequestParam(value="genrename", required = false) String genrename){
        try {
            if (StringUtils.isNotBlank(name)){
                return movieService.findByName(name);
            }
            else if (StringUtils.isNotBlank(genrename)){
                return movieService.findByGenreName(genrename);
            }
            else{
                return movieService.findAll();
            }
        }
        catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    }


    @Operation(summary = "Find a Movie by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))
            }),
            @ApiResponse(responseCode = "404", description = "Movie not found.")
    })
    @GetMapping(path = "{id}")
    public Movie findById(@PathVariable(value="id") Integer id){
        try{
            return movieService.findById(id);
        }
        catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
    }


    @Operation(summary = "Insert Movie.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie inserted."),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "409")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody @Valid Movie newMovie){
        try{
            movieService.insert(newMovie);
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Insert conflict");
        }
    }


    @Operation(summary = "Update Movie.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated."),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "409")
    })
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@RequestBody @Valid Movie movie){
        try{
            movieService.update(movie);
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Insert conflict");
        }
    }


    @Operation(summary = "Delete Movie by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie deleted."),
            @ApiResponse(responseCode = "404", description = "Movie not found.")
    })
    @DeleteMapping(path="{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(value="id") Integer id){
        try{
            movieService.deleteById(id);
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Insert conflict");
        }
    }
}
