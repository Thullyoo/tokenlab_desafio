package br.thullyoo.event_back.service.impl;

import br.thullyoo.event_back.dto.request.user.UserRegisterRequest;
import br.thullyoo.event_back.dto.request.user.UserUpdateRequest;
import br.thullyoo.event_back.model.User;
import br.thullyoo.event_back.repository.UserRepository;

public interface UserService {

    public User registerUser(UserRegisterRequest userRequest);

    public void updateUser(String document, UserUpdateRequest userRequest);
}
