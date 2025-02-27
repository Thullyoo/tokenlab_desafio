package br.thullyoo.event_back.service;

import br.thullyoo.event_back.dto.request.auth.LoginRequest;
import br.thullyoo.event_back.dto.response.auth.TokenResponse;

public interface AuthService {

    public TokenResponse login(LoginRequest loginRequest);
}
