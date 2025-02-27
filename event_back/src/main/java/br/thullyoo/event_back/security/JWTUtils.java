package br.thullyoo.event_back.security;

import br.thullyoo.event_back.dto.request.auth.LoginRequest;
import br.thullyoo.event_back.model.User;
import br.thullyoo.event_back.repository.UserRepository;
import com.nimbusds.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Component
public class JWTUtils {

    @Autowired
    private JwtEncoder encoder;

    public String generateJWT(User user){

        final Instant issuedAt = Instant.now();
        final Instant expiresAt = issuedAt.plusSeconds(300);

        var claims = JwtClaimsSet.builder()
                .issuer("JWTUtils")
                .subject(user.toString())
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
