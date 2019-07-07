package smart.kuk.simple.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class SimpleRestControllerTests {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  private PersonRepository personRepository;

  @Test
  @DisplayName("간단한 컨트롤러 통합 테스트")
  public void shouldGetSomePersons() {
    Person saved = personRepository.save(new Person("kuk", 70));
    String response = testRestTemplate.getForObject("/people?name=kuk", String.class);
    assertThat(response).contains(saved.getAge().toString());
  }
}
