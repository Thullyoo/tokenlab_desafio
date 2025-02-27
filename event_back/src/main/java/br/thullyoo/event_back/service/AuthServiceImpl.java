package br.thullyoo.event_back.service;

import br.thullyoo.event_back.dto.request.auth.LoginRequest;
import br.thullyoo.event_back.dto.response.auth.TokenResponse;
import br.thullyoo.event_back.model.User;
import br.thullyoo.event_back.repository.UserRepository;
import br.thullyoo.event_back.security.JWTUtils;
import br.thullyoo.event_back.service.impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public TokenResponse login(LoginRequest loginRequest) {

        Optional<User> user = userRepository.findByDocument(loginRequest.document());

        if (user.isEmpty()){
            throw new BadCredentialsException("User not found");
        }

        if (!passwordEncoder.matches(loginRequest.password(), user.get().getPassword())){
            throw new BadCredentialsException("Document or password incorrect");
        }

        String token = jwtUtils.generateJWT(user.get());
        Long expiresAt = 300L;
        return new TokenResponse(token, expiresAt);
    }
}
