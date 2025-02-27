package br.thullyoo.event_back.dto.response.auth;

public record TokenResponse(String token, Long expiresAt) {
}
