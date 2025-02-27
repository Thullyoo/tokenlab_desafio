package br.thullyoo.event_back.controller;

import br.thullyoo.event_back.dto.request.auth.LoginRequest;
import br.thullyoo.event_back.dto.response.auth.TokenResponse;
import br.thullyoo.event_back.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest){
        TokenResponse tokenResponse = authService.login(loginRequest);
        return ResponseEntity.ok().body(tokenResponse);
    }
}
