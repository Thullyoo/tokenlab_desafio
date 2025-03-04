package br.thullyoo.event_back.controller;

import br.thullyoo.event_back.dto.request.invite.InviteRequest;
import br.thullyoo.event_back.model.Invite;
import br.thullyoo.event_back.service.impl.InviteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invite")
public class InviteController {

    @Autowired
    private InviteServiceImpl inviteService;

    @PostMapping(value = "/register")
    public ResponseEntity<Invite> registerEnvite(@RequestBody InviteRequest inviteRequest){
        Invite invite = inviteService.registerInvite(inviteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(invite);
    }



}
