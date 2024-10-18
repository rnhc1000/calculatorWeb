package br.dev.ferreiras.calculatorweb.controller;

import br.dev.ferreiras.calculatorweb.service.RandomService;
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

  private final RandomService randomService;

  public RandomController(final RandomService randomService) {
    this.randomService = randomService;
  }

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
  public ResponseEntity<String> getRandomString(@RequestBody String randomApiRequestDto) throws JsonProcessingException {
    randomApiRequestDto = this.randomService.prepareRequestBody();
    RandomController.logger.info("{}", randomApiRequestDto);

    return ResponseEntity.ok(this.randomService.makeApiRequest());
  }
}
