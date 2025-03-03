package br.thullyoo.event_back.dto.request.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventUpdateRequest(Long eventId, String description, LocalDateTime startTime, LocalDateTime endTime) {
}
