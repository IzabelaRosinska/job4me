package miwm.job4me.web.controllers;

import miwm.job4me.exceptions.UserAlreadyExistException;
import miwm.job4me.services.users.UserAuthenticationService;
import miwm.job4me.web.mappers.users.UserMapper;
import miwm.job4me.web.model.users.RegisterData;
import miwm.job4me.web.model.users.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    UserAuthenticationService userAuthService;
    UserMapper userMapper;

    public AuthController(UserAuthenticationService userAuthService, UserMapper userMapper) {
        this.userAuthService = userAuthService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> registerUserAccount(@RequestBody RegisterData registerData) {
        UserDto user;
        try {
            user = userMapper.userToUserDto(userAuthService.registerNewUserAccount(registerData));
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
