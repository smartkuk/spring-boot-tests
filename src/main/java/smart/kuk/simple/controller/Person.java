package smart.kuk.simple.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Person {

  @Id
  @GeneratedValue
  private Long id;

  @Setter
  private String name;

  @Setter
  private Integer age;

  public Person() {}

  public Person(String name, Integer age) {
    this.name = name;
    this.age = age;
  }
}
