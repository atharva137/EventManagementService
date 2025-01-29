package com.victor.ray.event.controller;


import com.victor.ray.event.beans.ErrorResponse;
import com.victor.ray.event.beans.EventCreationResponse;
import com.victor.ray.event.beans.UserRequest;
import com.victor.ray.event.beans.UserResponse;
import com.victor.ray.event.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public  UserController (UserService userService){
        this.userService = userService;
    }


    @Operation(
            summary = "Create a new User",
            description = "This endpoint allows you to create a new User by providing necessary User details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })

    @PostMapping(value = "api/v1/eventservice/create/user",produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest){
        UserResponse userResponse =  userService.createUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }





    @SecurityRequirement(name="bearerAuth")
    @GetMapping("/api/test")
    public String testAuthentication(
            @RequestHeader("test") String test,
            @RequestHeader("authtoken") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token is missing or invalid.");
        }
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        return "Authentication successful! Token: " + jwtToken;
    }

}
