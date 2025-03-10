package br.thullyoo.event_back.service;

import br.thullyoo.event_back.dto.request.invite.InviteRequest;
import br.thullyoo.event_back.dto.request.invite.IsAcceptedInvite;
import br.thullyoo.event_back.dto.response.invite.InviteResponse;
import br.thullyoo.event_back.model.Invite;

import java.util.List;
import java.util.UUID;

public interface InviteService {

    public Invite registerInvite(InviteRequest inviteRequest);

    public List<InviteResponse> getInviteByUser();

    public void isAcceptedInvite(IsAcceptedInvite isAcceptedInvite);
}
