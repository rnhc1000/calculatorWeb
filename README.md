                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      **## Take-home Microservices Challenge

This is how we faced the challenge of creating a Web Calculator


## _Table of contents_

- [_Overview_](#overview)
- [_Requirements_](#requirements)
- [_Screenshot_](#screenshot)
- [_Links_](#links)
- [_Built with_](#built-with)
    - [_How I did it_](#how-i-did-it)
- [_Continued development_](#continued-development)
- [_Useful resources_](#useful-resources)
- [_Author_](#author)
- [Acknowledgments](#acknowledgments)

## _Overview_

This app is a calculator supporting basic math operations and a random string generator. The goal is to evaluate how a dev face the challenge of
building a RESTful API to be consumed by a ReactJS-based front-end app. available at <a href="https://caculatorweb.ferreiras.dev.br" target="_blank">CalculatorWeb-UI</a>.
There are some specific requirements such as authentication and authorization, data persistence, data recovery,
consume a specific api -> (https://random.org), authenticated endpoints and some other requirements.
The app has been coded using Java 21, Spring Boot, Spring Security, Spring JPA, Spring Webflux,
Flyway, Jackson, Lombok, OpenAPI, MySQL, Docker and hosted in an AWS EC2 instance with secure access provided
by a NGINX SSL proxy reverse.

- src
    - main
    - java
        - br.dev.ferreiras.calculatorWeb
            - config
            - controller
              - handlers 
            - dto
            - entity
            - enums
            - mapper
            - repository
            - services
              - exceptions
    - resources
        - db.migration
        - certs
    - test
-

_Requirements_

  ```
  
  - MySQL Database : http://127.0.0.1:3306
  - profile active: dev or prod
  - service socket: 127.0.0.1:8095

```

## _Screenshot_

[![](./webCalculator.png)]()

## _Links_

- Live Site URL: <a href="https://api.ferreiras.dev.br/swagger-ui/index.html" target="_blank">API CalculatorWeb</a>

## _Built with_

[![My Skills](https://skillicons.dev/icons?i=java,spring,mysql,gradle,docker,redhat,aws,idea,git,github,)](https://skillicons.dev)

## _How I did it_

```java
@Getter
@Entity
@Table (name = "tb_records")
public class Records implements Serializable {
  
  private static final long serialVersionUUID = 1L;

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long recordId;

  @NotNull
  private BigDecimal amount;

  private BigDecimal balance;

  private String operationResponse;

  @JoinColumn (name = "operation_id")
  @ManyToOne (cascade = CascadeType.ALL)
  private Long operationId;

  @CreationTimestamp
  private Instant createdAt;

  @ManyToOne (cascade = CascadeType.ALL)
  @JoinColumn (name = "user_id")
  private User userId;

  public Records(Long recordId, BigDecimal amount, BigDecimal balance,
                String operationResponse, Long operationId, Instant createdAt,
                User userId) {
    this.recordId = recordId;
    this.amount = amount;
    this.balance = balance;
    this.operationResponse = operationResponse;
    this.operationId = operationId;
    this.createdAt = createdAt;
    this.userId = userId;
  }

  public Records() {
  }

}
``` 

## _Continued development_

- Unit Tests
- Provide a Json to FrontEnd including
    - delivery status of each operation to frontend
    - count of operations consumed by subscriber
- Subscriber Authentication - OK
    - Spring JWT-OAuth2 - OK
- Records Pagination - OK

### _Useful resources_

- [https://spring.io] Awesome Java framework!.
- [https://start.spring.io/]  Handy startup tool.
- [https://mvnrepository.com] Tools that help tackle the beast

## _Author_

- Website - [https://ferreiras.dev.br]
  _Acknowledgments_
- 
