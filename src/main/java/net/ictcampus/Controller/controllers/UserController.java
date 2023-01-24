package net.ictcampus.Controller.controllers;

import net.ictcampus.Model.ApplicationUser;
import net.ictcampus.Controller.services.ApplicationUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "You do not have permission to do this. Please use /login first.",
                content = {@Content(mediaType = "application/json")})})
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ApplicationUserService applicationUserService;

    @GetMapping
    @Operation(summary = "Find all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationUser.class))})})
    public Iterable<ApplicationUser> findAll() {
        return applicationUserService.findAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "Find a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationUser.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    public ApplicationUser findById(@Parameter(description = "Id of user to get") @PathVariable Integer id) {
        Optional<ApplicationUser> user = applicationUserService.findById(id);
        return user.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/sign-up", consumes = "application/json")
    @Operation(summary = "Create a new user.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User was created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationUser.class))}),
            @ApiResponse(responseCode = "409", description = "User could not be created, username already in use",
                    content = @Content)})
    public void signUp(@Valid @RequestBody ApplicationUser newUser) {
        try {
            applicationUserService.signUp(newUser);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already in use");
        }
    }


    @PutMapping(consumes = "application/json")
    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationUser.class))}),
            @ApiResponse(responseCode = "409", description = "User could not be updated",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationUser.class))})})
    public void update(@Valid @RequestBody ApplicationUser user) {
        try {
            applicationUserService.update(user);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already in use");
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationUser.class))}),
            @ApiResponse(responseCode = "404", description = "User could not be deleted",
                    content = @Content)})
    public void delete(@Parameter(description = "Id of user to delete") @PathVariable Integer id) {
        try {
            applicationUserService.deleteById(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User could not be deleted");
        }
    }
}
