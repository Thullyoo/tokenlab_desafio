package br.thullyoo.event_back.service;

import br.thullyoo.event_back.dto.request.event.EventRequest;
import br.thullyoo.event_back.model.Event;

public interface EventService {

    public Event registerEvent(EventRequest eventRequest);

}
