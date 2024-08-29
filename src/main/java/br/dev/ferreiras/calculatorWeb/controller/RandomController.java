package br.dev.ferreiras.calculatorWeb.controller;

import br.dev.ferreiras.calculatorWeb.service.RandomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping (path = "api/v1")
public class RandomController {

  @Autowired
  private RandomService randomService;

  private static final Logger logger = LoggerFactory.getLogger(RandomController.class);


  @Operation (summary = "Get a random string")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Get a random string through random.org",
                  content = {@Content (mediaType = "application/json"
//                          schema = @Schema (implementation = UserController.class)
                  )}),
          @ApiResponse (responseCode = "401", description = "Not Authorized",
                  content = @Content),
          @ApiResponse (responseCode = "404", description = "endpoint not found",
                  content = @Content),
          @ApiResponse (responseCode = "415", description = "media not supported",
                  content = @Content)
  })
  @ResponseStatus
  @PostMapping (value = "/random", consumes = {"application/json"})
  @PreAuthorize ("hasAuthority('SCOPE_ROLE_ADMIN')")
  public ResponseEntity<String> getRandomString(@RequestBody String randomApiRequestDto)  {

    try {
      randomApiRequestDto = randomService.prepareRequestBody();
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    logger.info("{}", randomApiRequestDto);
    String list  = randomService.makeApiRequest();

    return ResponseEntity.ok(list);
  }

}
