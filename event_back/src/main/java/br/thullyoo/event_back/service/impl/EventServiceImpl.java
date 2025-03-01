package br.thullyoo.event_back.service.impl;

import br.thullyoo.event_back.dto.request.event.EventRequest;
import br.thullyoo.event_back.dto.response.event.EventResponse;
import br.thullyoo.event_back.dto.response.event.UserEventResponse;
import br.thullyoo.event_back.model.Event;
import br.thullyoo.event_back.model.User;
import br.thullyoo.event_back.repository.EventRepository;
import br.thullyoo.event_back.security.JWTUtils;
import br.thullyoo.event_back.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private JWTUtils jwtUtils;

    public EventResponse registerEvent(EventRequest eventRequest) {

        User user = jwtUtils.getUserByToken();

        if (eventRequest.startTime().isAfter(eventRequest.endTime())){
            throw new DateTimeException("Start Time cannot be after the End Time");
        }
        if (eventRequest.startTime().isEqual(eventRequest.endTime())){
            throw new DateTimeException("Start Time cannot be equal the End Time");
        }


        Event event = new Event();
        Set<User> event_user = event.getMembers();
        event.getMembers().add(user);
        event.setDescription(eventRequest.description());
        event.setEndTime(eventRequest.endTime());
        event.setStartTime(eventRequest.startTime());
        event.setUserCreator(user);
        event.setMembers(event_user);

        eventRepository.save(event);

        List<UserEventResponse> members = event.getMembers()
                .stream()
                .map(member -> new UserEventResponse(member.getName()))
                .toList();

        return new EventResponse(event.getDescription(), event.getStartTime(), event.getEndTime(), event.getUserCreator().getName(), members);
    }

    public List<EventResponse> getEventsByUser(){

        User user = jwtUtils.getUserByToken();

        List<EventResponse> eventsRes = new ArrayList<>();

        for (Event event : user.getEvents()){
            List<UserEventResponse> members = event.getMembers()
                    .stream()
                    .map(member -> new UserEventResponse(member.getName()))
                    .toList();
           eventsRes.add(new EventResponse(event.getDescription(), event.getStartTime(), event.getEndTime(), event.getUserCreator().getName(), members));
        }

        return eventsRes;
    }
}
