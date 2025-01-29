package com.victor.ray.event.controller;

import com.victor.ray.event.beans.CreateEventRequest;
import com.victor.ray.event.beans.ErrorResponse;
import com.victor.ray.event.beans.EventCreationResponse;
import com.victor.ray.event.exception.EventServiceException;
import com.victor.ray.event.service.EventService;
import com.victor.ray.event.validations.EventCreationRequestValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class EventController {


    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    private final EventCreationRequestValidation eventCreationRequestValidation;

    @Autowired
    public EventController(EventService eventService,EventCreationRequestValidation eventCreationRequestValidation){
        this.eventService = eventService;
        this.eventCreationRequestValidation=eventCreationRequestValidation;
    }


    @Operation(
            summary = "Create a new event",
            description = "This endpoint allows you to create a new event by providing necessary event details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventCreationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "api/v1/eventservice/events",produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public CompletableFuture<ResponseEntity<EventCreationResponse>> createEventController(@Valid @RequestBody CreateEventRequest createEventRequest){
        logger.info("Received request to create event with details: {}", createEventRequest);
      return  eventService.createEvent(createEventRequest)
                .thenApply(eventRes -> {
                    logger.info("Event created successfully with ID: {}", eventRes.getEventId());
                    return new ResponseEntity<>(eventRes, HttpStatus.CREATED);
                })
                .exceptionally(ex ->{
                    logger.error("Error occurred while creating event: {}", ex.getMessage());
                    throw new EventServiceException("Failed to create event. Please try again later.", ex);
                });

    }

}
