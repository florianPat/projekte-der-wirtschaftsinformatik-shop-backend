package fhdw.pdw.controller;

import fhdw.pdw.model.User;
import fhdw.pdw.model.dto.ApiResponse;
import fhdw.pdw.repository.UserRepository;
import fhdw.pdw.security.UserDetail;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javax.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class UserController {
  protected UserRepository userRepository;
  protected Validator validator;
  protected PasswordEncoder passwordEncoder;

  public UserController(
      UserRepository userRepository, Validator validator, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.validator = validator;
    this.passwordEncoder = passwordEncoder;
  }

  @PutMapping("/user")
  @Secured("ROLE_USER")
  public ResponseEntity<?> putUser(@RequestBody User putUser) {
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
          String getterMethodPrefix = "get";
          if (field.getType().equals(Boolean.TYPE)) {
            getterMethodPrefix = "is";
          }

          Object propertyValue =
              putUser
                  .getClass()
                  .getDeclaredMethod(
                      getterMethodPrefix
                          + field.getName().substring(0, 1).toUpperCase()
                          + field.getName().substring(1))
                  .invoke(putUser);

          if (field.getName().equals("email")) {
            String putUserEmail = putUser.getEmail();
            if (userRepository.existsByEmail(putUserEmail)
                || user.getEmail().equals(putUserEmail)) {
              continue;
            }
          }
          if (field.getName().equals("password")) {
            if (!putUser.getPassword().equals(putUser.getPasswordRepeat())) {
              continue;
            }
            propertyValue = passwordEncoder.encode((CharSequence) propertyValue);
            shouldReauthenticate = true;
          }

          user.getClass()
              .getDeclaredMethod(
                  "set"
                      + field.getName().substring(0, 1).toUpperCase()
                      + field.getName().substring(1),
                  field.getType())
              .invoke(user, propertyValue);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
          e.printStackTrace();
          return new ResponseEntity<>(new ApiResponse(false, ""), HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }
    }

    user.setPasswordRepeat("this_is_the_repeated_password");

    userRepository.save(user);

    if (shouldReauthenticate) {
      return new ResponseEntity<>(
          new ApiResponse(true, "User updated successfully and should authenticate again"),
          HttpStatus.OK);
    }

    return new ResponseEntity<>(new ApiResponse(true, "User updated successfully"), HttpStatus.OK);
  }
}
