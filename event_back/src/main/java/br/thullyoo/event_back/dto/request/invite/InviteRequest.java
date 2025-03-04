package br.thullyoo.event_back.dto.request.invite;

import java.util.UUID;

public record InviteRequest(Long eventId, String documentReceiver) {
}
