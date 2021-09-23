package fhdw.pdw;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PdwApplication {
  @PostConstruct
  void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  /**
   * Diese Methode startet den Backend Teil der Applikation
   * Hier wird der Tomcat Server gestartet, der Service Container aufgebaut, die Flyway Migrationen
   * ausgeführt und auf Anfragen gewartet
   */
  public static void main(String[] args) {
    SpringApplication.run(fhdw.pdw.PdwApplication.class, args);
  }
}
