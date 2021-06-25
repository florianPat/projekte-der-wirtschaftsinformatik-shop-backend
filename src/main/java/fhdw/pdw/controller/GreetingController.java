package fhdw.pdw.controller;

import fhdw.pdw.model.Greeting;
import fhdw.pdw.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class GreetingController {

    @Autowired
    protected GreetingService greetingService;

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "world") String name)
    {
        return new Greeting(0, "Hello " + name);
    }

    @GetMapping("/greeting/{id}")
    public Greeting parameterizedGreeting(@PathVariable String id)
    {
        return new Greeting(1, "Hello " + id);
    }

    @PostMapping("/greeting")
    public void addGreeting(@RequestBody Greeting greeting)
    {
        greetingService.addGreeting(greeting);
    }

}
