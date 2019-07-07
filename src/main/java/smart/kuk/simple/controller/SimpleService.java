package smart.kuk.simple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class SimpleService {

  @Autowired
  private PersonRepository personRepository;

  public List<Person> getPersons(String name) {
    Assert.notNull(name, "The name parameter is empty.");
    return personRepository.findAllByNameLike("%" + name + "%");
  }
}
