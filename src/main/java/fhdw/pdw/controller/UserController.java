package fhdw.pdw.controller;

import fhdw.pdw.model.User;
import fhdw.pdw.model.dto.ApiResponse;
import fhdw.pdw.repository.UserRepository;
import fhdw.pdw.security.UserDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@RequestMapping("/api")
@RestController
public class UserController {
    protected UserRepository userRepository;
    protected Validator validator;
    protected PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, Validator validator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    @PutMapping("/user")
    @Secured("ROLE_USER")
    public ResponseEntity<?> putUser(@Valid @RequestBody User putUser) {
        Object userPrinciple = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(userPrinciple instanceof UserDetail)) {
            throw new RuntimeException("The logged in user needs to be of type UserDetail!");
        }
        UserDetail userDetail = (UserDetail) userPrinciple;

        User user = userRepository.findByEmail(userDetail.getEmail()).orElseThrow();

        boolean shouldReauthenticate = false;

        for (Field field : putUser.getClass().getDeclaredFields()) {
            if (validator.validateProperty(putUser, field.getName()).isEmpty()) {
                try {
                    Object propertyValue = putUser.getClass().getDeclaredMethod("get" + field.getName().toUpperCase()).invoke(putUser);

                    if (field.getName().equals("email")) {
                        String putUserEmail = putUser.getEmail();
                        if (userRepository.existsByEmail(putUserEmail) || user.getEmail().equals(putUserEmail)) {
                            continue;
                        }
                    }
                    if (field.getName().equals("password")) {
                        if (!putUser.getPassword().equals(putUser.getPasswordRepeat())) {
                            continue;
                        }
                        putUser.setPassword(passwordEncoder.encode(putUser.getPassword()));
                        shouldReauthenticate = true;
                    }

                    user.getClass().getDeclaredMethod("set" + field.getName().toUpperCase(), field.getType()).invoke(user, propertyValue);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(new ApiResponse(false, ""), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }

        user.setPasswordRepeat("this_is_the_repeated_password");

        userRepository.save(user);

        if (shouldReauthenticate) {
            return new ResponseEntity<>(new ApiResponse(true, "User updated successfully and should authenticate again"), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ApiResponse(true, "User updated successfully"), HttpStatus.OK);
    }
}
