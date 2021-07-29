package fhdw.pdw.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends AbstractEntity {
  @NotBlank protected String firstName;
  @NotBlank protected String lastName;
  @NotBlank protected String street;
  @NotBlank protected String zip;
  @NotBlank protected String city;
  @NotBlank protected String birthday;
  @NotBlank @Email protected String email;
  @NotBlank protected String password;
  @NotBlank protected String passwordRepeat;
  @NotNull @AssertTrue protected boolean privacyStatement;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "users_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User() {}

  public User(
      String firstName,
      String lastName,
      String street,
      String zip,
      String city,
      String birthday,
      String email,
      String password,
      String passwordRepeat,
      boolean privacyStatement) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.street = street;
    this.zip = zip;
    this.city = city;
    this.birthday = birthday;
    this.email = email;
    this.password = password;
    this.passwordRepeat = passwordRepeat;
    this.privacyStatement = privacyStatement;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPasswordRepeat() {
    return passwordRepeat;
  }

  public void setPasswordRepeat(String passwordRepeat) {
    this.passwordRepeat = passwordRepeat;
  }

  public boolean isPrivacyStatement() {
    return privacyStatement;
  }

  public void setPrivacyStatement(boolean privacyStatement) {
    this.privacyStatement = privacyStatement;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}
