package br.thullyoo.event_back.controller;

import br.thullyoo.event_back.dto.request.invite.InviteRequest;
import br.thullyoo.event_back.dto.request.invite.IsAcceptedInvite;
import br.thullyoo.event_back.dto.response.invite.InviteResponse;
import br.thullyoo.event_back.model.Invite;
import br.thullyoo.event_back.service.impl.InviteServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/invite")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Invite Management", description = "APIs for managing invites")
public class InviteController {

    @Autowired
    private InviteServiceImpl inviteService;

    @Operation(summary = "Register a new invite", description = "Create a new invite")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Invite registered successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required")
    })
    @PostMapping(value = "/register")
    public ResponseEntity<Invite> registerInvite(@RequestBody InviteRequest inviteRequest) {
        Invite invite = inviteService.registerInvite(inviteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(invite);
    }

    @Operation(summary = "List invites by user", description = "Retrieve a list of invites for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invites retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required"),
    })
    @GetMapping(value = "/list-invite")
    public ResponseEntity<List<InviteResponse>> listInviteByUser() {
        List<InviteResponse> inviteList = inviteService.getInviteByUser();
        return ResponseEntity.status(HttpStatus.OK).body(inviteList);
    }

    @Operation(summary = "Accept or reject an invite", description = "Update the status of an invite (accepted or rejected)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Invite status updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required"),
            @ApiResponse(responseCode = "404", description = "Invite not found")
    })
    @PostMapping(value = "/isAccepted")
    public ResponseEntity<Void> isAcceptedInvite(@RequestBody IsAcceptedInvite isAcceptedInvite) {
        inviteService.isAcceptedInvite(isAcceptedInvite);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}