package br.thullyoo.event_back.dto.response.event;

import java.time.LocalDateTime;
import java.util.List;

public record EventResponse(String description, LocalDateTime startTime, LocalDateTime endTime, String userCreator, List<UserEventResponse> members) {
}
