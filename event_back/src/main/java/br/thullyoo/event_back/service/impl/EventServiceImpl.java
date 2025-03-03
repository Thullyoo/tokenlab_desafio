package br.thullyoo.event_back.service.impl;

import br.thullyoo.event_back.dto.request.event.EventRequest;
import br.thullyoo.event_back.dto.request.event.EventUpdateRequest;
import br.thullyoo.event_back.dto.response.event.EventResponse;
import br.thullyoo.event_back.dto.response.event.UserEventResponse;
import br.thullyoo.event_back.model.Event;
import br.thullyoo.event_back.model.User;
import br.thullyoo.event_back.repository.EventRepository;
import br.thullyoo.event_back.repository.UserRepository;
import br.thullyoo.event_back.security.JWTUtils;
import br.thullyoo.event_back.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.*;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    public EventResponse registerEvent(EventRequest eventRequest) {

        User user = jwtUtils.getUserByToken();

        if (eventRequest.startTime().isAfter(eventRequest.endTime())){
            throw new DateTimeException("Start Time cannot be after the End Time");
        }
        if (eventRequest.startTime().isEqual(eventRequest.endTime())){
            throw new DateTimeException("Start Time cannot be equal the End Time");
        }

        if (userRepository.hasOverlappingEvents(user.getId(), eventRequest.startTime(), eventRequest.endTime())){
            throw new DateTimeException("Event cannot stand out another");
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

        return new EventResponse(event.getId(), event.getDescription(), event.getStartTime(), event.getEndTime(), event.getUserCreator().getName(), members);
    }

    public List<EventResponse> getEventsByUser(){

        User user = jwtUtils.getUserByToken();

        List<EventResponse> eventsRes = new ArrayList<>();

        for (Event event : user.getEvents()){
            List<UserEventResponse> members = event.getMembers()
                    .stream()
                    .map(member -> new UserEventResponse(member.getName()))
                    .toList();
           eventsRes.add(new EventResponse(event.getId(),event.getDescription(), event.getStartTime(), event.getEndTime(), event.getUserCreator().getName(), members));
        }

        return eventsRes;
    }

    public EventResponse getEventById(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isEmpty()) {
            throw new IllegalArgumentException("Event not found");
        }
        Event event = eventOptional.get();
        List<UserEventResponse> members = event.getMembers()
                .stream()
                .map(member -> new UserEventResponse(member.getName()))
                .toList();
        return new EventResponse(event.getId(),event.getDescription(), event.getStartTime(), event.getEndTime(), event.getUserCreator().getName(), members);
    }

    public Boolean isEventCreator(Long id) {
        User user = jwtUtils.getUserByToken();

        for (Event event : user.getEventsCreated()){
            if (Objects.equals(event.getId(), id)){
                return true;
            }
        }
        return false;
    }

    public void updateEvent(EventUpdateRequest eventUpdateRequest){

        User user = jwtUtils.getUserByToken();

        Optional<Event> eventOptional = eventRepository.findById(eventUpdateRequest.eventId());

        if (eventOptional.isEmpty()){
            throw new IllegalArgumentException("Event not found");
        }

        Event event = eventOptional.get();

        if (!eventUpdateRequest.description().equalsIgnoreCase(event.getDescription())){
            if (eventUpdateRequest.description() != null){
                event.setDescription(eventUpdateRequest.description());
            }
        }

        if (eventUpdateRequest.startTime().isAfter(eventUpdateRequest.endTime())){
            throw new DateTimeException("Start Time cannot be after the End Time");
        } else if (!eventUpdateRequest.startTime().isEqual(event.getStartTime())){
            event.setStartTime(eventUpdateRequest.startTime());
        }

        if (eventUpdateRequest.startTime().isEqual(eventUpdateRequest.endTime())){
            throw new DateTimeException("Start Time cannot be equal the End Time");
        } else if (!eventUpdateRequest.endTime().isEqual(event.getEndTime())){
            event.setEndTime(eventUpdateRequest.endTime());
        }

        if (userRepository.hasOverlappingEvents(user.getId(), eventUpdateRequest.startTime(), eventUpdateRequest.endTime())){
            throw new DateTimeException("Event cannot stand out another");
        }

        eventRepository.save(event);
    }

}
