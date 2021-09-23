package fhdw.pdw.mapper;

import fhdw.pdw.model.User;
import fhdw.pdw.model.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
  /**
   * Hier werden die Daten des Registierungsformulars in eine Benutzer Entität, welche in der
   * Datenbank persisiert werden kann, überführt
   */
  public User mapFrom(UserDto userDto) {
    User result =
        new User(
            userDto.getFirstName(),
            userDto.getLastName(),
            userDto.getStreet(),
            userDto.getZip(),
            userDto.getCity(),
            userDto.getBirthday(),
            userDto.getEmail(),
            userDto.getPassword(),
            userDto.isPrivacyStatement(),
            userDto.isHasVerifiedAge());
    result.setRoles(userDto.getRoles());
    return result;
  }
}
