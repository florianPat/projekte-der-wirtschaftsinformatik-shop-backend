package fhdw.pdw.security;

import fhdw.pdw.model.User;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetail implements UserDetails {
  protected int id;
  protected String name;
  protected String username;
  protected String email;
  @JsonIgnore protected String password;
  protected Collection<? extends GrantedAuthority> authorities;
  protected static final long serialVersionUID = 42L;

  public UserDetail(
      int id,
      String name,
      String username,
      String email,
      String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDetail create(User user) {
    List<GrantedAuthority> authorities =
        user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
            .collect(Collectors.toList());

    return new UserDetail(
        user.getId(),
        user.getFirstName() + user.getLastName(),
        user.getEmail(),
        user.getEmail(),
        user.getPassword(),
        authorities);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserDetail that = (UserDetail) o;

    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
