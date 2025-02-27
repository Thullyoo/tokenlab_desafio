package br.thullyoo.event_back.security;

import br.thullyoo.event_back.dto.request.auth.LoginRequest;
import br.thullyoo.event_back.model.User;
import br.thullyoo.event_back.repository.UserRepository;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JWTUtils {

    @Autowired
    private JwtEncoder encoder;

    @Autowired
    private UserRepository userRepository;

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

    public User getUserByToken(){

        JwtAuthenticationToken jwtAuthenticationToken =
                (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (jwtAuthenticationToken == null) {
            throw new IllegalStateException("User is not authenticated.");
        }

        Jwt jwt = jwtAuthenticationToken.getToken();
        String subject = jwt.getClaim("sub");

        Pattern pattern = Pattern.compile("document='([0-9]+)'");
        Matcher matcher = pattern.matcher(subject);

        if (matcher.find()){
            Optional<User> user = userRepository.findByDocument(matcher.group(1));
            if (user.isEmpty()) {
                throw new BadCredentialsException("User not found");
            } else {
                return user.get();
            }
        }
        throw new IllegalArgumentException("Document not found");
    }
}
