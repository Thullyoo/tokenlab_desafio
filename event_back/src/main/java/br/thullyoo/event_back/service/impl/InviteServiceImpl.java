package br.thullyoo.event_back.service.impl;

import br.thullyoo.event_back.dto.request.invite.InviteRequest;
import br.thullyoo.event_back.dto.request.invite.IsAcceptedInvite;
import br.thullyoo.event_back.dto.response.invite.InviteResponse;
import br.thullyoo.event_back.model.Event;
import br.thullyoo.event_back.model.Invite;
import br.thullyoo.event_back.model.User;
import br.thullyoo.event_back.repository.EventRepository;
import br.thullyoo.event_back.repository.InviteRepository;
import br.thullyoo.event_back.repository.UserRepository;
import br.thullyoo.event_back.security.JWTUtils;
import br.thullyoo.event_back.service.InviteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InviteServiceImpl implements InviteService {

    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Invite registerInvite(InviteRequest inviteRequest){
        User user = jwtUtils.getUserByToken();

        Optional<Event> event = eventRepository.findById(inviteRequest.eventId());

        if (event.isEmpty()){
            throw new IllegalArgumentException("Event is not found");
        }

        Optional<User> receiver = userRepository.findByDocument(inviteRequest.documentReceiver());

        if (receiver.isEmpty()){
            throw new IllegalArgumentException("User receiver not found");
        }

        Invite invite = new Invite();
        invite.setEvent(event.get());
        invite.setReceiver(receiver.get());
        invite.setSender(user);

        inviteRepository.save(invite);

        return invite;
    }

    public List<InviteResponse> getInviteByUser(){

        User user = jwtUtils.getUserByToken();

        List<InviteResponse> inviteResponseList = new ArrayList<>();

        for(Invite invite : user.getReceivedInvites()){
            if (!invite.isViewed()){
                inviteResponseList.add(new InviteResponse(invite.getId(), invite.getSender().getName(), invite.getEvent().getId()));
            }
        }
        return inviteResponseList;
    }

    public void isAcceptedInvite(IsAcceptedInvite isAcceptedInvite) {
        User user = jwtUtils.getUserByToken();

        Invite invite = inviteRepository.findById(isAcceptedInvite.inviteId())
                .orElseThrow(() -> new IllegalArgumentException("Invite not found"));

        if (!invite.getReceiver().equals(user)) {
            throw new IllegalArgumentException("This invitation was not sent to the logged user");
        }

        invite.setViewed(true);

        if (isAcceptedInvite.isAccepted()) {
            if (invite.getEvent().getMembers().contains(user)) {
                throw new IllegalArgumentException("The user is already a member of this event");
            }

            Set<User> members = invite.getEvent().getMembers();
            members.add(user);
            invite.getEvent().setMembers(members);
            invite.setAccepted(true);
            eventRepository.save(invite.getEvent());
        }

        inviteRepository.save(invite);
        userRepository.save(user);
    }
}
