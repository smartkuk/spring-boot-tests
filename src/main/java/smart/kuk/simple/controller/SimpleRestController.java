package smart.kuk.simple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/people")
public class SimpleRestController {

  @Autowired
  private SimpleService simpleService;

  @RequestMapping(method = RequestMethod.GET)
  public List<Person> getPersons(@RequestParam(name = "name") String name) {
    return simpleService.getPersons(name);
  }
}
