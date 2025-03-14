package br.thullyoo.event_back.controller;

import br.thullyoo.event_back.dto.request.event.EventRequest;
import br.thullyoo.event_back.dto.request.event.EventUpdateRequest;
import br.thullyoo.event_back.dto.response.event.EventResponse;
import br.thullyoo.event_back.service.impl.EventServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Event Management", description = "APIs for managing events")
public class EventController {

    @Autowired
    private EventServiceImpl eventService;

    @Operation(summary = "Register a new event", description = "Create a new event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required"),
    })
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventResponse> registerEvent(@RequestBody @Valid EventRequest eventRequest) {
        EventResponse event = eventService.registerEvent(eventRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    @Operation(summary = "List events by user", description = "Retrieve a list of events for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required"),
    })
    @GetMapping(value = "/list-events")
    public ResponseEntity<List<EventResponse>> getEventsByUser() {
        List<EventResponse> events = eventService.getEventsByUser();
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    @Operation(summary = "Get event by ID", description = "Retrieve an event by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable("id") Long id) {
        EventResponse eventResponse = eventService.getEventById(id);
        return ResponseEntity.status(HttpStatus.OK).body(eventResponse);
    }

    @Operation(summary = "Check if user is event creator", description = "Check if the authenticated user is the creator of the event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check completed successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping(value = "/event-creator/{id}")
    public ResponseEntity<Boolean> isEventCreator(@PathVariable("id") Long id) {
        boolean result = eventService.isEventCreator(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Update an event", description = "Update an existing event's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Void> updateEvent(@PathVariable("id") Long id, @RequestBody @Valid EventUpdateRequest updateRequest) {
        eventService.updateEvent(id, updateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @Operation(summary = "Delete an event", description = "Delete an existing event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}