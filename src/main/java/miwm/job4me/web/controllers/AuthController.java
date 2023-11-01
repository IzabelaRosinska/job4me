package miwm.job4me.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.exceptions.UserAlreadyExistException;
import miwm.job4me.model.VerificationToken;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Person;
import miwm.job4me.security.OnRegistrationCompleteEvent;
import miwm.job4me.services.users.UserAuthenticationService;
import miwm.job4me.web.mappers.users.UserMapper;
import miwm.job4me.web.model.users.RegisterData;
import miwm.job4me.web.model.users.UserDto;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

@RestController
public class AuthController {

    UserAuthenticationService userAuthService;
    UserMapper userMapper;
    ApplicationEventPublisher eventPublisher;

    public AuthController(UserAuthenticationService userAuthService, UserMapper userMapper, ApplicationEventPublisher eventPublisher) {
        this.userAuthService = userAuthService;
        this.userMapper = userMapper;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/signup")
    @Operation(summary = "Registers a new user", description = "Registers a new user in the database")
    public ResponseEntity<UserDto> registerUserAccount(@RequestBody RegisterData registerData, HttpServletRequest request) {
        UserDto userDto;
        try {
            Person person = userAuthService.registerNewUserAccount(registerData);
            userDto = userMapper.toDto(person);
            if(registerData.getRole().equals("EMPLOYEE")) {
                String appUrl = request.getContextPath();
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent((Employee)person, request.getLocale(), appUrl));
            }
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping("/registrationConfirm")
    public void confirmRegistration(WebRequest request, HttpServletResponse response, @RequestParam("token") String token) throws IOException {
        VerificationToken verificationToken = userAuthService.getVerificationToken(token);
        if (verificationToken == null) {
            response.sendRedirect("/");
        }
        Employee employee = verificationToken.getEmployee();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            response.sendRedirect("/");
        }
        userAuthService.unlockEmployee(employee);
        response.sendRedirect("http://localhost:4200/login");
    }
}
