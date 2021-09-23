package fhdw.pdw.security;

import fhdw.pdw.model.User;
import fhdw.pdw.repository.UserRepository;
import javax.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  protected UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Um den Benutzer bei einer "getUser" Abfrage zu laden muss dieser am Benutzernamen erstellt werden
   */
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByEmailIgnoreCase(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + username));
    return UserDetail.create(user);
  }

  /**
   * Benutzt von dem JWTAuthenticationFilter
   */
  @Transactional
  public UserDetail loadUserById(Integer id) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    return UserDetail.create(user);
  }
}
