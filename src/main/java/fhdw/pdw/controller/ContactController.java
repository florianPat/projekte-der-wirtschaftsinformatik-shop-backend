package fhdw.pdw.controller;

import fhdw.pdw.email.EmailService;
import fhdw.pdw.model.dto.ApiResponse;
import fhdw.pdw.model.dto.ContactFormMessage;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ContactController {
  protected EmailService emailService;

  public ContactController(EmailService emailService) {
    this.emailService = emailService;
  }

  @PostMapping("/contact")
  public ResponseEntity<?> postContactForm(
      @Valid @RequestBody ContactFormMessage contactFormMessage) {
    emailService.sendSimpleMessage(
        "siphydrated@gmail.com",
        "Kontaktformular: " + contactFormMessage.getSubject(),
        "Von: "
            + contactFormMessage.getName()
            + ", "
            + contactFormMessage.getEmail()
            + "\n"
            + contactFormMessage.getText());

    return new ResponseEntity<>(new ApiResponse(true, "Message send successfully"), HttpStatus.OK);
  }
}
