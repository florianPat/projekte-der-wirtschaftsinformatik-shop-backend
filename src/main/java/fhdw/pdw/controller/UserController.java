package fhdw.pdw.controller;

import fhdw.pdw.mapper.ConstraintViolationSetToErrorResponseMapper;
import fhdw.pdw.mapper.UserMapper;
import fhdw.pdw.model.User;
import fhdw.pdw.model.dto.UserDto;
import fhdw.pdw.repository.UserRepository;
import fhdw.pdw.security.UserDetail;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class UserController {
  protected UserRepository userRepository;
  protected Validator validator;
  protected PasswordEncoder passwordEncoder;
  protected UserMapper userMapper;
  protected ConstraintViolationSetToErrorResponseMapper constraintViolationSetToErrorResponseMapper;

  public UserController(
      UserRepository userRepository,
      Validator validator,
      PasswordEncoder passwordEncoder,
      UserMapper userMapper,
      ConstraintViolationSetToErrorResponseMapper constraintViolationSetToErrorResponseMapper) {
    this.userRepository = userRepository;
    this.validator = validator;
    this.passwordEncoder = passwordEncoder;
    this.userMapper = userMapper;
    this.constraintViolationSetToErrorResponseMapper = constraintViolationSetToErrorResponseMapper;
  }

  /**
   * Patch-Endpunkt, um Daten des Benutzers, wie das Passwort oder die Adresse oder die E-Mail
   * Adresse zu verändern. Außerdem findet eine Überprüfung auf eine bereits verwendete E-Mail
   * sowie eine Passwort Validierung statt
   */
  @PatchMapping("/users")
  @Secured("ROLE_USER")
  public ResponseEntity<?> patchUser(@RequestBody UserDto patchUser) {
    Object userPrinciple = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!(userPrinciple instanceof UserDetail)) {
      throw new RuntimeException("The logged in user needs to be of type UserDetail!");
    }
    UserDetail userDetail = (UserDetail) userPrinciple;

    User user = userRepository.findByEmailIgnoreCase(userDetail.getEmail()).orElseThrow();
    User patchMappedUser = userMapper.mapFrom(patchUser);

    Set<ConstraintViolation<UserDto>> violations = validator.validate(patchUser);
    for (ConstraintViolation<?> constraintViolation : violations) {
      String path = constraintViolation.getPropertyPath().toString();
      int x = 0;
    }

    // TODO: Fix primitive types!
    for (Field field : patchMappedUser.getClass().getDeclaredFields()) {
      if (validator.validateProperty(patchMappedUser, field.getName()).isEmpty()) {
        try {
          String getterMethodPrefix = "get";
          if (field.getType().equals(Boolean.TYPE)) {
            getterMethodPrefix = "is";
          }

          Object propertyValue =
              patchMappedUser
                  .getClass()
                  .getDeclaredMethod(
                      getterMethodPrefix
                          + field.getName().substring(0, 1).toUpperCase()
                          + field.getName().substring(1))
                  .invoke(patchMappedUser);

          if (propertyValue == null) {
            continue;
          }

          if (field.getName().equals("password")) {
            propertyValue = passwordEncoder.encode((CharSequence) propertyValue);
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
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }
    }

    Set<ConstraintViolation<User>> violationsUser = validator.validate(user);
    if (!violationsUser.isEmpty()) {
      return constraintViolationSetToErrorResponseMapper.mapFrom(violationsUser);
    }

    userRepository.save(user);

    return new ResponseEntity<>(user, HttpStatus.OK);
  }
}
