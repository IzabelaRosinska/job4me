package miwm.job4me.controllers;

import miwm.job4me.exceptions.UserAlreadyExistException;
import miwm.job4me.mappers.UserMapper;
import miwm.job4me.model.users.RegisterData;
import miwm.job4me.model.users.UserDto;
import miwm.job4me.services.users.UserAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
