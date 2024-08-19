package br.dev.ferreiras.calculatorWeb.service;

import br.dev.ferreiras.calculatorWeb.dto.RandomApiRequestDto;
import br.dev.ferreiras.calculatorWeb.dto.RandomApiResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Service
public class RandomService {
//
//  @Autowired
//  private WebClient.Builder builder;

  RandomApiRequestDto randomApiRequestDto = new RandomApiRequestDto(
          "b720e6c8-5bd7-4c80-ab27-60a893668157",
          "2.0",
          "generateStrings",
          "57",
          "1",
          "12",
          "abcdefghijklmnopqrstuvwxyz",
          true
  );


  public RandomService() {
  }

  public RandomService(RandomApiRequestDto randomApiRequestDto) {

    this.randomApiRequestDto = randomApiRequestDto;

  }

  public String prepareRequestBody() throws JsonProcessingException {

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode rootNode = objectMapper.createObjectNode();
    ObjectNode data = objectMapper.createObjectNode();
    data.put("apiKey", "b720e6c8-5bd7-4c80-ab27-60a893668157");
    data.put("n", 1);
    data.put("length", 12);
    data.put("characters", "abcdefghijklmnopqrstuvwxyz");
    rootNode.set("params", data);
    rootNode.put("jsonrpc", "2.0");
    rootNode.put("method", "generateStrings");
    rootNode.put("id", 57);

    String requestBody =  objectMapper.writeValueAsString(rootNode);
    System.out.println(requestBody);
    return requestBody;
  }

  public RandomApiResponseDto makeApiRequest()  {

    String requestBody = null;
    try {
      requestBody = prepareRequestBody();
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }


//    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//    params.add( "apiKey", "b720e6c8-5bd7-4c80-ab27-60a893668157");


//
//    return builder.build()
//                  .post()
//                  .bodyValue(params)
//                  .retrieve()
//                  .bodyToMono(new RandomApiRequestDto());

//
//    JsonObject params = new JsonObject();
//    params.addProperty("apiKey", "b720e6c8-5bd7-4c80-ab27-60a893668157");
//    request.addProperty("jsonrpc", "2.0");
//    request.addProperty("method", method);
//    request.add("params", params);
//    request.addProperty("id", UUID.randomUUID().toString());
//    request.addProperty("n", n);
//    request.addProperty("length", length);
//    request.addProperty("characters", characters);
//    request.addProperty("replacement", replacement);
//
//MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//params.add("param1", "value1");
//params.add("param2", "value2");
//
    WebClient webClient = WebClient.create("https://api.random.org");

    return Objects.requireNonNull(webClient.post()
                                           .uri("/json-rpc/4/invoke")
                                           .bodyValue(requestBody)
                                           .retrieve()
                                           .bodyToMono(RandomApiResponseDto.class)
                                           .block());
//  public RandomService(WebClient.Builder webClientBuilder) {
//    WebClient webClient = webClientBuilder
//            .baseUrl("https://api.random.org/json-rpc/4/invoke")
//            .defaultHeader("X-API-KEY", "b720e6c8-5bd7-4c80-ab27-60a893668157")
//            .build();
//  }
  }
}

/*
WebClient webClient = WebClient.builder()
        .baseUrl("http://hostname:8080/api")
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString("username:password".getBytes()))
        .build();

Mono<ResponseType> response = webClient.post()
        .uri("/endpoint")
        .bodyValue(requestBody)
        .retrieve()
        .bodyToMono(ResponseType.class);

        WebClient webClient = WebClient.builder()
    .baseUrl("https://api.example.com")
    .defaultHeader("X-API-KEY", "your_api_key_here")
    .build();

String response = webClient.post()
    .uri("/endpoint")
    .bodyValue(yourRequestBody) // Replace with your request body
    .retrieve()
    .bodyToMono(String.class)
    .block(); // Use block() for synchronous execution

        @Autowired
    private WebClient.Builder builder;
    private Object makeApiRequest(String apiKey, String uri){
       return builder.build()
                    .get(uri)
                    .header("API-Key", apiKey)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();

    }
 */
