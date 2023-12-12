package miwm.job4me.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.exceptions.UserAlreadyExistException;
import miwm.job4me.model.token.VerificationToken;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.model.users.Account;
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
            Account account = userAuthService.registerNewUserAccount(registerData);
            userDto = userMapper.toDto(account);

            String appUrl = request.getContextPath();
            if(registerData.getRole().equals(EMPLOYEE))
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent((Employee) account, request.getLocale(), appUrl));
            else if(registerData.getRole().equals(EMPLOYER))
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent((Employer) account, request.getLocale(), appUrl));
            else if(registerData.getRole().equals(ORGANIZER))
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent((Organizer) account, request.getLocale(), appUrl));

        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping("/registration")
    @Operation(summary = "Confirms user registration", description = "Confirm registration and unlock user")
    public void confirmRegistration(HttpServletResponse response, @RequestParam("token") String token) throws IOException {
        VerificationToken verificationToken = userAuthService.getVerificationToken(token);
        if (verificationToken == null) {
            response.sendRedirect(ERROR_URL);
        }
        Employee employee = verificationToken.getEmployee();
        Employer employer = verificationToken.getEmployer();
        Organizer organizer = verificationToken.getOrganizer();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            response.sendRedirect(ERROR_URL);
        }
        if(employee != null)
            userAuthService.unlockEmployee(employee);
        else if(employer != null)
            userAuthService.unlockEmployer(employer);
        else if(organizer != null)
            userAuthService.unlockOrganizer(organizer);

        response.sendRedirect(FRONT_HOST_AZURE + LOGIN_URL);
    }

    @PostMapping("/password-reset")
    @Operation(summary = "Send password rest token", description = "Create token and send to user via email")
    public ResponseEntity<?> resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        Account account = userAuthService.loadUserByUsername(userEmail);
        if (account == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        userAuthService.sendResetToken(account, request.getContextPath());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/password-change")
    @Operation(summary = "Enable changing password", description = "Display password to reset password if token valid")
    public void showChangePasswordPage(HttpServletResponse response, @RequestParam(value = "token", required = false) String token) throws IOException {
        if(token != null && !userAuthService.isValidPasswordResetToken(token))
            response.sendRedirect(ERROR_URL);
        else if(token != null)
            response.sendRedirect(FRONT_HOST_AZURE + UPDATE_PASSWORD_URL + token);
        else
            response.sendRedirect(FRONT_HOST_AZURE + UPDATE_PASSWORD_URL);
    }

    @PostMapping("/password-update")
    @Operation(summary = "Save new user password after reset", description = "Save new password if token is valid")
    public ResponseEntity<?> updatePassword(@RequestParam(value = "token", required = false) String token, @Valid @RequestBody PasswordDto passwordDto) {
        if(token != null && !userAuthService.isValidPasswordResetToken(token))
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        Account user;
        if(token != null)
            user = userAuthService.getUserByPasswordResetToken(token);
        else
            user = userAuthService.getAuthenticatedUser();

        if(user != null && passwordDto.getPassword().equals(passwordDto.getMatchingPassword())) {
            userAuthService.changeUserPassword(user, passwordDto.getPassword());
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
