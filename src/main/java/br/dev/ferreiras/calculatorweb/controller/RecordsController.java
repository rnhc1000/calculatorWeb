package br.dev.ferreiras.calculatorweb.controller;

import br.dev.ferreiras.calculatorweb.dto.RecordsDto;
import br.dev.ferreiras.calculatorweb.service.RecordsService;
import br.dev.ferreiras.calculatorweb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1")
public class RecordsController {

  private final RecordsService recordsService;

  private final UserService userService;

  private static final String usernameNotFound = "Username not found!";

  public RecordsController(final RecordsService recordsService, final UserService userService) {
    this.recordsService = recordsService;
    this.userService = userService;
  }

  public static final Logger logger = LoggerFactory.getLogger(RecordsController.class);

  @Operation(
      summary = "Fetch 20 records per page",
      description = "Fetch 20 records per page",
      responses = {
          @ApiResponse(responseCode = "200", description = "Get up to 20 records per page.",
              content = {@Content(mediaType = "application/json",
                  schema = @Schema(implementation = RecordsController.class))}
          )})
  @GetMapping(value = "/records")
  public ResponseEntity<RecordsDto> getAllMessages(
      @RequestParam(defaultValue = "0", name = "page") final int page,
      @RequestParam(defaultValue = "20", name = "size") final int size) {

    RecordsController.logger.info("Page Number -> {}, Size of Each Page -> {}", page, size);

    return this.recordsService.getPagedRecords(page, size);
  }

  @Operation(
      summary = "Fetch 20 records per page",
      description = "Fetch 20 records per page provided the username authenticated",
      responses = {
          @ApiResponse(responseCode = "200", description = "Get up to 12 messages per page.",
              content = {@Content(mediaType = "application/json",
                  schema = @Schema(implementation = RecordsDto.class))}),
          @ApiResponse(responseCode = "404", description = "Resource not found!",
              content = {@Content(mediaType = "application/json")})
      })
  @GetMapping(value = "/user/records")
  public ResponseEntity<RecordsDto> getRecordsByUsername(
      @RequestParam(defaultValue = "1") final int page,
      @RequestParam(defaultValue = "20") final int size,
      @RequestParam(defaultValue = "asc") final String sort
  ) {
    final String user = String.valueOf(Optional.of(this.userService.getLoggedUsername())
        .orElseThrow(() ->
            new UsernameNotFoundException(RecordsController.usernameNotFound)));
    final boolean isDeleted = false;
    RecordsController.logger.info("Page Number: {} , Size of Each Page: {} , User: {}, Deleted? {}",
        page, size, user, isDeleted);

    logger.info("::: Username: {} :::", user);
    return this.recordsService.findRecordsByUsername(page, size, user);
  }

  @Operation(
      summary = "Fetch 20 records per page",
      description = "Fetch 20 records per page provided the username authenticated",
      responses = {
          @ApiResponse(responseCode = "200", description = "Get up to 20 records per page.",
              content = {@Content(mediaType = "application/json",
                  schema = @Schema(implementation = RecordsDto.class))}),
          @ApiResponse(responseCode = "404", description = "Resource not found!",
              content = {@Content(mediaType = "application/json")})
      })
  @GetMapping(value = "/user/operations")
  public ResponseEntity<List<RecordsDto>> getRecordsByUsernameStatus(
      @RequestParam(defaultValue = "0", name = "page") final int page,
      @RequestParam(defaultValue = "20", name = "size") final int size,
      @RequestParam(defaultValue = "false", name = "isDeleted") boolean isDeleted
  ) {

    final String user = String.valueOf(Optional.ofNullable(this.userService.getLoggedUsername())
        .orElseThrow(() ->
            new UsernameNotFoundException(RecordsController.usernameNotFound)));
    isDeleted = false;
    RecordsController.logger.info("Page Number: {} , Size of Each Page: {} , User: {},  isDeleted: {}",
        page, size, user, isDeleted);

    return ResponseEntity.ok(this.recordsService.findRecordsByUsernameStatus(page, size, user));
  }

  @Operation(summary = "Soft delete a record",
      description = "Set the status of a record to soft deleted",
      responses = {
          @ApiResponse(responseCode = "200", description = "Record soft deleted @database.",
              content = {@Content(mediaType = "application/json")})
      })
  @PutMapping("/user/operations/{id}")
  public ResponseEntity<HttpStatus> updateRecord(@PathVariable("id") final Long id) {

    this.recordsService.deleteRecordById(id);

    return ResponseEntity.ok(HttpStatus.ACCEPTED);
  }

  @Operation(summary = "Fetch records soft-deleted",
      description = "Fetch records soft-deleted provided the username authenticated",
      responses = {
          @ApiResponse(responseCode = "200", description = "Return soft deleted records.",
              content = {@Content(mediaType = "application/json",
                  schema = @Schema(implementation = RecordsDto.class))}),
          @ApiResponse(responseCode = "404", description = "Resource not found!",
              content = {@Content(mediaType = "application/json")})
      })
  @GetMapping(value = "/deleted")
  public ResponseEntity<List<RecordsDto>> getSoftDeletedRecords(
      @RequestParam final int page,
      @RequestParam final int size,
      @RequestParam final String username
  ) {

    final String user = String.valueOf(Optional.ofNullable(this.userService.getLoggedUsername())
        .orElseThrow(() ->
            new UsernameNotFoundException(RecordsController.usernameNotFound)));
    RecordsController.logger.info("Page Number: {} , Size of Each Page: {} , User: {}", page, size, user);

    return ResponseEntity.ok(this.recordsService.findSoftDeletedRecords(page, size, user));
  }
}
