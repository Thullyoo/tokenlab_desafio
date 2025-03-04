package br.thullyoo.event_back.dto.response.invite;

public record InviteResponse(String userSender, Long eventId, boolean isViewed) {
}
