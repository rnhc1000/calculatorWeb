package br.dev.ferreiras.calculatorWeb.controller;

import br.dev.ferreiras.calculatorWeb.dto.RecordsDto;
import br.dev.ferreiras.calculatorWeb.service.RecordsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping (path = "api/v1")
public class RecordsController {

  @Autowired
  private RecordsService recordsService;

  public final static Logger logger = LoggerFactory.getLogger(RecordsController.class);

  @Operation (summary = "Fetch 10 records per page")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Get up to 10 messages per page.",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = RecordsController.class))})})
  @ResponseStatus
  @GetMapping (value = "/records")
  public ResponseEntity<RecordsDto> getAllMessages(
          @RequestParam (defaultValue = "0") int page,
          @RequestParam (defaultValue = "10") int size) {

    logger.info(String.format(("Page Number -> , Size of Each Page -> , %s, %s"), page, size));

    return recordsService.getPagedRecords(page, size);
  }
}
