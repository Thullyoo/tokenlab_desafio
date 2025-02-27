package br.thullyoo.event_back.service;

import br.thullyoo.event_back.dto.request.user.UserRegisterRequest;
import br.thullyoo.event_back.dto.request.user.UserUpdateRequest;
import br.thullyoo.event_back.model.User;

public interface UserService {

    public User registerUser(UserRegisterRequest userRequest);

    public void updateUser(UserUpdateRequest userRequest);
}
