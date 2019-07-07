# spring-boot-tests

Pet 프로젝트에 RestController를 통합 테스트 하기 위해 기존에는 주로 MockMvc 사용해서 구현을 많이 했었다. 테스트와 관련된 내용을 좀 찾아보고자 [spring-boot test] 문서를 보던중 [Testing with a running server] 발견하고 테스트 해보니 MockMvc로 하는거보다 편해서 앞으로 이걸 사용하려고 결정했다.

먼저 기존에 했던 방식의 테스트 코드를 보면 다음과 같다. 내가 느끼기에는 너무 길다.

```java
// ... 중략 ...
MvcResult mvcResult = this.mockMvc.perform(get("/greet"))
      .andDo(print()).andExpect(status().isOk())
      .andExpect(jsonPath("$.message").value("Hello World!!!"))
      .andReturn();

Assert.assertEquals("application/json;charset=UTF-8", 
      mvcResult.getResponse().getContentType());
```

그리고 우리는 비동기 응답(Callable<T>, DeferredResult<T> 등)을 주는 형태의 컨트롤러의 테스트를 할때는 아래와 유사한 형태의 테스트 코드를 사용하게 된다.

```java
// ... 중략 ...
MvcResult result = mockMvc.perform(get("/test/deferred").contentType(APPLICATION_JSON)).andReturn();

mockMvc.perform(asyncDispatch(result))
       .andExpect(status().isOk())
       .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
       .andExpect(jsonPath("message").value("hello"));
```

아래는 위에서 언급한 문서에서 알려준 형태의 테스트 코드인데 @SpringBootTest 사용해서 컨트롤러에 구현된 Endpoint 테스트하는 코드이다. TestRestTemplate 이라는 클래스를 사용해서 기존 RestTemplate 클래스의 사용방식처럼 테스트를 할 수 있다.
  
```java
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
```

[샘플 github] 프로젝트

[spring-boot test]: https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/boot-features-testing.html#boot-features-testing
[Testing with a running server]: https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-with-running-server
[샘플 github]: https://github.com/smartkuk/spring-boot-tests
