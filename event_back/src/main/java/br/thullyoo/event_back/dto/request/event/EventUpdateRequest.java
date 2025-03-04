package br.thullyoo.event_back.dto.request.event;

import java.time.LocalDateTime;

public record EventUpdateRequest(String description, LocalDateTime startTime, LocalDateTime endTime) {
}
