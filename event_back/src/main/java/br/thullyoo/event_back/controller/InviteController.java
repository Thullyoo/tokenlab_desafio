package br.thullyoo.event_back.controller;

import br.thullyoo.event_back.dto.request.invite.InviteRequest;
import br.thullyoo.event_back.dto.request.invite.IsAcceptedInvite;
import br.thullyoo.event_back.dto.response.invite.InviteResponse;
import br.thullyoo.event_back.model.Invite;
import br.thullyoo.event_back.service.impl.InviteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invite")
public class InviteController {

    @Autowired
    private InviteServiceImpl inviteService;

    @PostMapping(value = "/register")
    public ResponseEntity<Invite> registerInvite(@RequestBody InviteRequest inviteRequest){
        Invite invite = inviteService.registerInvite(inviteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(invite);
    }

    @GetMapping(value = "/list-invite")
    public ResponseEntity<List<InviteResponse>> listInviteByUser(){
        List<InviteResponse> inviteList = inviteService.getInviteByUser();
        return ResponseEntity.status(HttpStatus.OK).body(inviteList);
    }

    @PostMapping(value = "/isAccepted")
    public ResponseEntity<Void> isAcceptedInvite(@RequestBody IsAcceptedInvite isAcceptedInvite){
        inviteService.isAcceptedInvite(isAcceptedInvite);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
