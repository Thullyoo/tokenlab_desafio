package br.thullyoo.event_back.dto.request.user;

import jakarta.validation.constraints.Pattern;

public record UserUpdateRequest(@Pattern(regexp = "^(|.{2,100})$", message = "Name must be empty or between 2 and 100 characters")
                                 String name,

                                @Pattern(regexp = "^(|^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$)$", message = "Email must be empty or a valid email address")
                                 String email,

                                @Pattern(regexp = "^(|.{8,20})$", message = "Password must be empty or between 8 and 20 characters")
                                 String password) {
}
