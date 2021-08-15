package fhdw.pdw.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ContactFormMessage {
  @NotBlank protected String name;
  @NotBlank @Email protected String email;
  @NotBlank protected String subject;
  @NotBlank protected String text;

  public ContactFormMessage() {}

  public ContactFormMessage(String name, String email, String subject, String text) {
    this.name = name;
    this.email = email;
    this.subject = subject;
    this.text = text;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
