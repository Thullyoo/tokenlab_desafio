package br.thullyoo.event_back.service;

import br.thullyoo.event_back.dto.request.user.UserRegisterRequest;
import br.thullyoo.event_back.dto.request.user.UserUpdateRequest;
import br.thullyoo.event_back.model.User;
import br.thullyoo.event_back.repository.UserRepository;
import br.thullyoo.event_back.security.JWTUtils;
import br.thullyoo.event_back.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Transactional
    public User registerUser(UserRegisterRequest userRequest) {

        User user = new User();

        user.setDocument(userRequest.document());
        user.setEmail(userRequest.email());
        user.setName(userRequest.name());
        user.setPassword(passwordEncoder.encode(userRequest.password()));

        userRepository.save(user);

        return user;
    }

    @Transactional
    public void updateUser(UserUpdateRequest userRequest) {

        User user =  jwtUtils.getUserByToken();


        if (!userRequest.name().isBlank() && !userRequest.name().isEmpty()){
            user.setName(userRequest.name());
        }

        if (!userRequest.password().isBlank() && !userRequest.password().isEmpty()){
            user.setPassword(passwordEncoder.encode(userRequest.password()));
        }

        if (!userRequest.email().isBlank() && !userRequest.email().isEmpty()){
            user.setEmail(userRequest.email());
        }

        userRepository.save(user);

    }
}
