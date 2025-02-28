package br.thullyoo.event_back.controller;

import br.thullyoo.event_back.dto.request.event.EventRequest;
import br.thullyoo.event_back.model.Event;
import br.thullyoo.event_back.service.impl.EventServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventServiceImpl eventService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> registerEvent(@RequestBody @Valid EventRequest eventRequest){
        Event event = eventService.registerEvent(eventRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }
}
