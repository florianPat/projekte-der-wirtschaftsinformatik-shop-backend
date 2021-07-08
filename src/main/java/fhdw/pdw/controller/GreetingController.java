package fhdw.pdw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class GreetingController {

  @GetMapping("/greeting")
  public String greeting() {
    return "Hello World";
  }

  /*@GetMapping("/greeting/{id}")
  public Greeting parameterizedGreeting(@PathVariable String id) {
      return new Greeting(1, "Hello " + id);
  }

  @PostMapping("/greeting")
  public void addGreeting(@RequestBody Greeting greeting) {
      greetingService.addGreeting(greeting);
  }

  @GetMapping("/greetingWithRequestParam")
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "world") String name) {
      return new Greeting(0, "Hello " + name);
  }
  */
}
