package br.thullyoo.event_back.service;

import br.thullyoo.event_back.dto.request.auth.LoginRequest;
import br.thullyoo.event_back.dto.response.auth.TokenResponse;
import br.thullyoo.event_back.security.JWTUtils;
import br.thullyoo.event_back.service.impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JWTUtils jwtUtils;

    public TokenResponse login(LoginRequest loginRequest) {
        String token = jwtUtils.generateJWT(loginRequest);
        Long expiresAt = 300L;
        return new TokenResponse(token, expiresAt);
    }
}
