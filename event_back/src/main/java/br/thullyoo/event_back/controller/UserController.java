package br.thullyoo.event_back.controller;

import br.thullyoo.event_back.dto.request.user.UserRegisterRequest;
import br.thullyoo.event_back.dto.request.user.UserUpdateRequest;
import br.thullyoo.event_back.model.User;
import br.thullyoo.event_back.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> registerUser(@RequestBody @Valid UserRegisterRequest userRequest){
        User user = userService.registerUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@RequestBody @Valid
                                            UserUpdateRequest userUpdateRequest){
        userService.updateUser(userUpdateRequest);
        return ResponseEntity.ok().body(null);
    }
}
