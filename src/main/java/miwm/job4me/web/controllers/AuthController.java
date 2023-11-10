package miwm.job4me.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.exceptions.UserAlreadyExistException;
import miwm.job4me.model.token.PasswordResetToken;
import miwm.job4me.model.token.VerificationToken;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Person;
import miwm.job4me.security.OnRegistrationCompleteEvent;
import miwm.job4me.services.users.UserAuthenticationService;
import miwm.job4me.web.mappers.users.UserMapper;
import miwm.job4me.web.model.users.PasswordDto;
import miwm.job4me.web.model.users.RegisterData;
import miwm.job4me.web.model.users.UserDto;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

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

            String appUrl = request.getContextPath();
            if(registerData.getRole().equals("EMPLOYEE"))
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent((Employee)person, request.getLocale(), appUrl));
            else if(registerData.getRole().equals("EMPLOYER"))
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent((Employer)person, request.getLocale(), appUrl));

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
        Employer employer = verificationToken.getEmployer();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            response.sendRedirect("/");
        }
        if(employee != null)
            userAuthService.unlockEmployee(employee);
        else if(employer != null)
            userAuthService.unlockEmployer(employer);

        response.sendRedirect("http://localhost:4200/login");
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        Person person = userAuthService.loadUserByUsername(userEmail);
        if (person == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        String token = UUID.randomUUID().toString();
        String appUrl = request.getContextPath();
        userAuthService.createPasswordResetTokenForUser(person, token);
        userAuthService.sendResetToken(person, token, appUrl);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/changePassword")
    public void showChangePasswordPage(HttpServletResponse response, @RequestParam("token") String token) throws IOException {
        String result = userAuthService.validatePasswordResetToken(token);
        if(result != null)
            response.sendRedirect("/");
        else
            response.sendRedirect("http://localhost:4200/updatePassword/?token=" + token);
    }

    @PostMapping("/savePassword")
    public void savePassword(HttpServletResponse response, @Valid PasswordDto passwordDto) throws IOException {
        String result = userAuthService.validatePasswordResetToken(passwordDto.getToken());
        if(result != null)
            response.sendRedirect("/");

        Person user = userAuthService.getUserByPasswordResetToken(passwordDto.getToken());
        if(user != null) {
            userAuthService.changeUserPassword(user, passwordDto.getNewPassword());
            response.sendRedirect("http://localhost:4200/login");
        } else
            response.sendRedirect("/");
    }
}
