package br.thullyoo.event_back.controller;

import br.thullyoo.event_back.dto.request.event.EventRequest;
import br.thullyoo.event_back.dto.response.event.EventResponse;
import br.thullyoo.event_back.model.Event;
import br.thullyoo.event_back.service.impl.EventServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventServiceImpl eventService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventResponse> registerEvent(@RequestBody @Valid EventRequest eventRequest){
        EventResponse event = eventService.registerEvent(eventRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    @GetMapping(value = "/list-events")
    public ResponseEntity<List<EventResponse>> getEventsByUser(){
        List<EventResponse> events = eventService.getEventsByUser();
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }
}
