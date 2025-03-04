package br.thullyoo.event_back.service;

import br.thullyoo.event_back.dto.request.invite.InviteRequest;
import br.thullyoo.event_back.model.Invite;

public interface InviteService {

    public Invite registerInvite(InviteRequest inviteRequest);

}
