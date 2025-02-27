package br.thullyoo.event_back.service;

import br.thullyoo.event_back.dto.request.user.UserRegisterRequest;
import br.thullyoo.event_back.dto.request.user.UserUpdateRequest;
import br.thullyoo.event_back.model.User;
import br.thullyoo.event_back.repository.UserRepository;
import br.thullyoo.event_back.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(UserRegisterRequest userRequest) {

        User user = new User();

        user.setDocument(userRequest.document());
        user.setEmail(userRequest.email());
        user.setName(userRequest.name());
        user.setPassword(userRequest.password());

        userRepository.save(user);

        return user;
    }

    public void updateUser(String document, UserUpdateRequest userRequest) {

        Optional<User> user =  userRepository.findByDocument(document);

        if (user.isEmpty()){
            throw new BadCredentialsException("User not found");
        }

        if (!userRequest.name().isBlank() && !userRequest.name().isEmpty()){
            user.get().setName(userRequest.name());
        }

        if (!userRequest.password().isBlank() && !userRequest.password().isEmpty()){
            user.get().setPassword(userRequest.password());
        }

        if (!userRequest.email().isBlank() && !userRequest.email().isEmpty()){
            user.get().setEmail(userRequest.email());
        }

        userRepository.save(user.get());
    }
}
