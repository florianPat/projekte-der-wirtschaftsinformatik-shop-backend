package fhdw.pdw.controller;

import fhdw.pdw.model.Role;
import fhdw.pdw.model.RoleName;
import fhdw.pdw.model.User;
import fhdw.pdw.model.dto.ApiResponse;
import fhdw.pdw.model.dto.JwtAuthenticationResponse;
import fhdw.pdw.repository.RoleRepository;
import fhdw.pdw.repository.UserRepository;
import fhdw.pdw.security.CurrentUser;
import fhdw.pdw.security.JwtTokenProvider;
import java.net.URI;
import java.util.Collections;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  protected AuthenticationManager authenticationManager;
  protected UserRepository userRepository;
  protected RoleRepository roleRepository;
  protected PasswordEncoder passwordEncoder;
  protected JwtTokenProvider jwtTokenProvider;

  public AuthController(
      AuthenticationManager authenticationManager,
      UserRepository userRepository,
      RoleRepository roleRepository,
      PasswordEncoder passwordEncoder,
      JwtTokenProvider jwtTokenProvider) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
    if (userRepository.existsByEmail(user.getEmail())) {
      return new ResponseEntity<>(
          new ApiResponse(false, "Email is already taken!"), HttpStatus.BAD_REQUEST);
    }

    if (!user.getPassword().equals(user.getPasswordRepeat())) {
      return new ResponseEntity<>(
          new ApiResponse(false, "Repeated password is not equal to the password!"),
          HttpStatus.BAD_REQUEST);
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setPasswordRepeat("this_is_the_repeated_password");

    Role roleUser =
        roleRepository
            .findByName(RoleName.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("User role not found!"));
    user.setRoles(Collections.singleton(roleUser));

    User result = userRepository.save(user);
    URI location =
        ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/users/{email}")
            .buildAndExpand(result.getEmail())
            .toUri();
    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "User registered successfully"));
  }

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@Valid @RequestBody User user) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtTokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser(@RequestBody User user) {
    return ResponseEntity.ok(new ApiResponse(true, "User logged out successfully"));
  }

  @PostMapping("/user")
  public User getUser(@CurrentUser User user) {
    return user;
  }
}
