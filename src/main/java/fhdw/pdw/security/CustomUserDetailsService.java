package fhdw.pdw.security;

import fhdw.pdw.model.User;
import fhdw.pdw.repository.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired protected UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByEmail(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + username));
    return UserDetail.create(user);
  }

  // NOTE: This method is used by JWTAuthenticationFilter
  @Transactional
  public UserDetail loadUserById(Integer id) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    return UserDetail.create(user);
  }
}
