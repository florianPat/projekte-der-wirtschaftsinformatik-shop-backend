package fhdw.pdw.controller;

import fhdw.pdw.email.EmailService;
import fhdw.pdw.mapper.ConstraintViolationSetToErrorResponseMapper;
import fhdw.pdw.mapper.UserMapper;
import fhdw.pdw.model.Role;
import fhdw.pdw.model.RoleName;
import fhdw.pdw.model.User;
import fhdw.pdw.model.dto.JwtAuthenticationResponse;
import fhdw.pdw.model.dto.LoginUser;
import fhdw.pdw.model.dto.UserDto;
import fhdw.pdw.repository.RoleRepository;
import fhdw.pdw.repository.UserRepository;
import fhdw.pdw.security.JwtTokenProvider;
import fhdw.pdw.security.UserDetail;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  protected AuthenticationManager authenticationManager;
  protected UserRepository userRepository;
  protected RoleRepository roleRepository;
  protected PasswordEncoder passwordEncoder;
  protected JwtTokenProvider jwtTokenProvider;
  protected EmailService emailService;
  protected UserMapper userMapper;
  protected Validator validator;
  protected ConstraintViolationSetToErrorResponseMapper constraintViolationSetToErrorResponseMapper;

  public AuthController(
      AuthenticationManager authenticationManager,
      UserRepository userRepository,
      RoleRepository roleRepository,
      PasswordEncoder passwordEncoder,
      JwtTokenProvider jwtTokenProvider,
      EmailService emailService,
      UserMapper userMapper,
      Validator validator,
      ConstraintViolationSetToErrorResponseMapper constraintViolationSetToErrorResponseMapper) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
    this.emailService = emailService;
    this.userMapper = userMapper;
    this.validator = validator;
    this.constraintViolationSetToErrorResponseMapper = constraintViolationSetToErrorResponseMapper;
  }

  /**
   * Abfrage Anfrage zum registieren eines neuen Benutzers mit Validierung und Verschickung einer
   * Email
   */
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    Role roleUser =
        roleRepository
            .findByName(RoleName.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("User role not found!"));
    user.setRoles(Collections.singletonList(roleUser));

    User persistUser = userMapper.mapFrom(user);
    Set<ConstraintViolation<User>> violations = validator.validate(persistUser);
    if (!violations.isEmpty()) {
      return constraintViolationSetToErrorResponseMapper.mapFrom(violations);
    }
    User result = userRepository.save(persistUser);

    emailService.sendSimpleMessage(
        result.getEmail(),
        "Registrierungsbestätigung sip.shop",
        "Sehr geehrte Kundein,\nsehr geehrter Kunde,\n\n"
            + "wir freuen uns, Sie als neuen Kunden bei uns Willkommen zu heißen.\n\n"
            + "Eine kurze Übersicht über die von Ihnen hinterlegen Daten:\n\n"
            + result.getFirstName()
            + " "
            + result.getLastName()
            + "\n"
            + result.getStreet()
            + ", "
            + result.getZip()
            + " "
            + result.getCity()
            + "\n"
            + result.getEmail()
            + "\n"
            + "\nStimmen die o.g. Daten überein, dann können Sie diese Email als Registrierungsbestätigung speichern.\n"
            + "Stimmen die Daten nicht, ändern Sie diese bitte im Portal über Login.\n\n"
            + "Das sip.shop Team wünscht Ihnen einen guten Einkauf.\n\n"
            + "In diesem Sinne: Stay hydrated mit sip.shop!");

    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  /**
   * API-Endpunkt zum Einloggen mit einem registrierten Benutzers
   */
  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@Valid @RequestBody LoginUser user) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtTokenProvider.generateToken(authentication);
    return new ResponseEntity<>(new JwtAuthenticationResponse(jwt), HttpStatus.OK);
  }

  /**
   * API-Abfrage, um die Benutzerdaten des eingeloggten Benutzers zu erhalten
   * @return
   */
  @GetMapping("/user")
  public ResponseEntity<?> getUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if ("anonymousUser" == principal) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    if (!(principal instanceof UserDetail)) {
      throw new RuntimeException("The user should be of type UserDetail!");
    }
    UserDetail userDetail = (UserDetail) principal;
    Optional<User> userOptional = userRepository.findByEmailIgnoreCase(userDetail.getEmail());
    if (userOptional.isEmpty()) {
      throw new RuntimeException("The user entity was not found through the UserDetails!");
    }
    return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
  }
}
