package net.ictcampus.Controller.controllers;

import net.ictcampus.Model.Genre;
import net.ictcampus.Controller.services.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
@RequestMapping("/genres")
public class GenreController {
    @Autowired
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }


    @Operation(summary = "Find genre by name. With no given arguments all genre(s) are returned.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre(s) found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Genre.class))
            })
    })
    @GetMapping
    public Iterable<Genre> findByName(@RequestParam(value="name", required = false) String name){
        try {
            if (StringUtils.isNotBlank(name)) {
                return genreService.findByName(name);
            }
            return genreService.findAll();
        }
        catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "genre not found");
        }
    }


    @Operation(summary = "Find a genre by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Genre.class))
            }),
            @ApiResponse(responseCode = "404", description = "Genre not found.")
    })
    @GetMapping(path = "{id}")
    public Genre findById(@PathVariable(value = "id") Integer id){
        try {
            return genreService.findById(id);
        }
        catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "genre not found");
        }
    }


    @Operation(summary = "Insert Genre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Genre inserted."),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "409")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody @Valid Genre newGenre){
        try {
            genreService.insert(newGenre);
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "insert conflict");
        }
    }


    @Operation(summary = "Update Genre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre updated."),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "409")
    })
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@RequestBody @Valid Genre genre){
        try {
            genreService.update(genre);
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "update conflict");
        }
    }


    @Operation(summary = "Delete a Genre by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre deleted."),
            @ApiResponse(responseCode = "404", description = "Genre not found.")
    })
    @DeleteMapping(path="{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(value = "id") Integer id){
        try {
            genreService.deleteById(id);
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "delete conflict");
        }
    }
}
