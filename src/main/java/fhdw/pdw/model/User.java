package fhdw.pdw.model;

import com.fasterxml.jackson.annotation.*;
import fhdw.pdw.constraints.UniqueUserEmail;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id",
    scope = User.class)
@Table(name = "users")
@UniqueUserEmail
public class User extends AbstractEntity {
  @NotBlank protected String firstName;
  @NotBlank protected String lastName;
  @NotBlank protected String street;
  @NotBlank protected String zip;
  @NotBlank protected String city;
  @NotBlank protected String birthday;
  @NotBlank @Email protected String email;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @NotBlank
  protected String password;

  @NotNull @AssertTrue protected boolean privacyStatement;

  @ManyToMany
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "users_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  protected List<Role> roles;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  protected List<Order> orders;

  @NotNull protected boolean hasVerifiedAge;

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
      boolean privacyStatement,
      boolean hasVerifiedAge) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.street = street;
    this.zip = zip;
    this.city = city;
    this.birthday = birthday;
    this.email = email;
    this.password = password;
    this.privacyStatement = privacyStatement;
    this.hasVerifiedAge = hasVerifiedAge;
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

  public boolean isPrivacyStatement() {
    return privacyStatement;
  }

  public void setPrivacyStatement(boolean privacyStatement) {
    this.privacyStatement = privacyStatement;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public boolean isHasVerifiedAge() {
    return hasVerifiedAge;
  }

  public void setHasVerifiedAge(boolean hasVerifiedAge) {
    this.hasVerifiedAge = hasVerifiedAge;
  }
}
