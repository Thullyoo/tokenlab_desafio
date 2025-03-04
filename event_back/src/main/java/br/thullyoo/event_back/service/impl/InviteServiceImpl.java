package br.thullyoo.event_back.service.impl;

import br.thullyoo.event_back.dto.request.invite.InviteRequest;
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

import java.util.Optional;
import java.util.OptionalInt;

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

}
