package br.thullyoo.event_back.service;

import br.thullyoo.event_back.dto.request.event.EventRequest;
import br.thullyoo.event_back.dto.response.event.EventResponse;
import br.thullyoo.event_back.model.Event;

import java.util.List;
import java.util.Set;

public interface    EventService {

    public EventResponse registerEvent(EventRequest eventRequest);

    public List<EventResponse> getEventsByUser();

    public EventResponse getEventById(Long id);

    public Boolean isEventCreator(Long id);
}
