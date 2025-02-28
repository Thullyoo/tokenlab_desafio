package br.thullyoo.event_back.service.impl;

import br.thullyoo.event_back.dto.request.event.EventRequest;
import br.thullyoo.event_back.model.Event;
import br.thullyoo.event_back.model.User;
import br.thullyoo.event_back.repository.EventRepository;
import br.thullyoo.event_back.security.JWTUtils;
import br.thullyoo.event_back.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private JWTUtils jwtUtils;

    public Event registerEvent(EventRequest eventRequest) {

        User user = jwtUtils.getUserByToken();

        if (eventRequest.startTime().isAfter(eventRequest.endTime())){
            throw new DateTimeException("Start Time cannot be after the End Time");
        }
        if (eventRequest.startTime().isEqual(eventRequest.endTime())){
            throw new DateTimeException("Start Time cannot be equal the End Time");
        }


        Event event = new Event();
        event.setDescription(eventRequest.description());
        event.setEndTime(eventRequest.endTime());
        event.setStartTime(eventRequest.startTime());
        event.setUserCreator(user);

        eventRepository.save(event);

        return event;
    }
}
