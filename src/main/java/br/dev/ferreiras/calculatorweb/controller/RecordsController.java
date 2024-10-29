package br.dev.ferreiras.calculatorweb.controller;

import br.dev.ferreiras.calculatorweb.dto.RecordsDto;
import br.dev.ferreiras.calculatorweb.service.RecordsService;
import br.dev.ferreiras.calculatorweb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping (path = "api/v1")
public class RecordsController {

  private final RecordsService recordsService;

  private final UserService userService;

  public RecordsController(final RecordsService recordsService, final UserService userService) {
    this.recordsService = recordsService;
    this.userService = userService;
  }

  public static final Logger logger = LoggerFactory.getLogger(RecordsController.class);

  @Operation (summary = "Fetch 10 records per page")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Get up to 10 messages per page.",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = RecordsController.class))})})
  @ResponseStatus
  @GetMapping (value = "/records")
  public ResponseEntity<RecordsDto> getAllMessages(
          @RequestParam (defaultValue = "1") int page,
          @RequestParam (defaultValue = "20") int size) {

    RecordsController.logger.info("Page Number -> {}, Size of Each Page -> {}", page, size);

    return this.recordsService.getPagedRecords(page, size);
  }

  @Operation (summary = "Fetch 12 records per page provided the username authenticated")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Get up to 12 messages per page.",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = RecordsController.class))}),
          @ApiResponse (responseCode = "404", description = "Resource not found!",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = RecordsController.class))})
  })
  @ResponseStatus
  @GetMapping (value = "/user/records")
  public ResponseEntity<RecordsDto> getRecordsByUsername(
          @RequestParam (defaultValue = "0") final int page,
          @RequestParam (defaultValue = "10") final int size
          ) throws Exception {

    final String user = this.userService.authenticated();
    RecordsController.logger.info("Page Number: {} , Size of Each Page: {} , User: {}",  page, size, user);

    return this.recordsService.findRecordsByUsername(page, size, user);
  }
}
