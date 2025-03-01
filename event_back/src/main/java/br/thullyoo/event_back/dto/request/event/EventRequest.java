package br.thullyoo.event_back.dto.request.event;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record EventRequest(
                            @NotBlank(message = "Description cannot be blank")
                            String description,
                           @NotNull(message = "The date and time cannot be null")
                           @FutureOrPresent(message = "The date and time must be in the present or future")
                           LocalDateTime startTime,
                           @NotNull(message = "The date and time cannot be null")
                           @FutureOrPresent(message = "he date and time must be in the present or future")
                           LocalDateTime endTime) {
}
