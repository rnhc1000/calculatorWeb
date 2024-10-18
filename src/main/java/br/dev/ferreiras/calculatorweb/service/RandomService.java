package br.dev.ferreiras.calculatorweb.service;

import br.dev.ferreiras.calculatorweb.dto.RandomApiRequestDto;
import br.dev.ferreiras.calculatorweb.service.exceptions.RandomProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RandomService {

  private RandomApiRequestDto randomApiRequestDto;

  public RandomService() {
  }

  @Autowired
  public RandomService(final RandomApiRequestDto randomApiRequestDto) {

    this.randomApiRequestDto = randomApiRequestDto;
  }

  public String prepareRequestBody() throws JsonProcessingException {

    final ObjectMapper objectMapper = new ObjectMapper();
    final ObjectNode rootNode = objectMapper.createObjectNode();
    final ObjectNode data = objectMapper.createObjectNode();
    data.put("apiKey", "b720e6c8-5bd7-4c80-ab27-60a893668157");
    data.put("n", 1);
    data.put("length", 12);
    data.put("characters", "abcdefghijklmnopqrstuvwxyzABCDEFFGHIJKLMNOPQRSTUVXZ0123456789)(*&%$#@");
    rootNode.set("params", data);
    rootNode.put("jsonrpc", "2.0");
    rootNode.put("method", "generateStrings");
    rootNode.put("id", 57);

    return objectMapper.writeValueAsString(rootNode);
  }

  public String makeApiRequest() {

    String requestBody = null;
    try {
      requestBody = this.prepareRequestBody();
    } catch (final JsonProcessingException exception) {
      throw new RandomProcessingException("JSON Processing error!");
    }

    final WebClient webClient = WebClient.create("https://api.random.org");

    return String.valueOf(webClient.post()
                                   .uri("/json-rpc/4/invoke")
                                   .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                   .contentType(MediaType.APPLICATION_JSON)
                                   .bodyValue(requestBody)
                                   .retrieve()
                                   .bodyToMono(String.class)
                                   .block());
  }
}


