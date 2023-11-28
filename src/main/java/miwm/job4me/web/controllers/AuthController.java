package miwm.job4me.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.exceptions.UserAlreadyExistException;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Calendar;

import static miwm.job4me.messages.AppMessages.*;

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
            if(registerData.getRole().equals(EMPLOYEE))
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent((Employee)person, request.getLocale(), appUrl));
            else if(registerData.getRole().equals(EMPLOYER))
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent((Employer)person, request.getLocale(), appUrl));

        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping("/registration-confirm")
    @Operation(summary = "Confirms user registration", description = "Confirm registration and unlock user")
    public void confirmRegistration(HttpServletResponse response, @RequestParam("token") String token) throws IOException {
        VerificationToken verificationToken = userAuthService.getVerificationToken(token);
        if (verificationToken == null) {
            response.sendRedirect(ERROR_URL);
        }
        Employee employee = verificationToken.getEmployee();
        Employer employer = verificationToken.getEmployer();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            response.sendRedirect(ERROR_URL);
        }
        if(employee != null)
            userAuthService.unlockEmployee(employee);
        else if(employer != null)
            userAuthService.unlockEmployer(employer);

        response.sendRedirect(FRONT_HOST_AZURE + LOGIN_URL);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Send password rest token", description = "Create token and send to user via email")
    public ResponseEntity<?> resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        Person person = userAuthService.loadUserByUsername(userEmail);
        if (person == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        userAuthService.sendResetToken(person, request.getContextPath());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/change-password")
    @Operation(summary = "Enable changing password", description = "Display password to reset password if token valid")
    public void showChangePasswordPage(HttpServletResponse response, @RequestParam("token") String token) throws IOException {
        if(!userAuthService.isValidPasswordResetToken(token))
            response.sendRedirect(ERROR_URL);
        else
            response.sendRedirect(FRONT_HOST_AZURE + UPDATE_PASSWORD_URL + token);
    }

    @PostMapping("/save-password")
    @Operation(summary = "Save new user password after reset", description = "Save new password if token is valid")
    public void savePassword(HttpServletResponse response, @Valid PasswordDto passwordDto) throws IOException {
        if(!userAuthService.isValidPasswordResetToken(passwordDto.getToken()))
            response.sendRedirect(ERROR_URL);

        Person user = userAuthService.getUserByPasswordResetToken(passwordDto.getToken());
        if(user != null) {
            userAuthService.changeUserPassword(user, passwordDto.getNewPassword());
            response.sendRedirect(FRONT_HOST_AZURE + LOGIN_URL);
        } else
            response.sendRedirect(ERROR_URL);
    }

    @PostMapping("/update-password")
    @Operation(summary = "Update password for authenticated user", description = "Update user password")
    public ResponseEntity<?> updatePassword(@Valid PasswordDto passwordDto) {
        Person user = userAuthService.getAuthenticatedUser();
        if(user != null) {
            userAuthService.changeUserPassword(user, passwordDto.getNewPassword());
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
