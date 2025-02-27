package br.dev.ferreiras.calculatorweb.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorsController implements ErrorController {

  private static final Logger logger = LoggerFactory.getLogger(ErrorsController.class);
  @RequestMapping("/error")
  public String handleError(final HttpServletRequest request) {

    ErrorsController.logger.info("-----------Inside Custom Error Handler-------------");
    final Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);


    if (null != status) {

      final var statusCode  = status.toString();

      return switch (statusCode) {
        case "NOT_FOUND" -> {
          ErrorsController.logger.info("-----------404 Error-------------");
          yield "404-error";
        }
        case "UNAUTHORIZED" -> {
          ErrorsController.logger.info("-----------401 Error-------------");
          yield "401-error";
        }
        case "FORBIDDEN" -> {
          ErrorsController.logger.info("-----------403 Error-------------");
          yield "403-error";
        }
        case "INTERNAL_SERVER_ERROR" -> {
          ErrorsController.logger.info("-----------500 Error-------------");
          yield "500-error";
        }
        default -> "error";
      };

    }

    return "error";
  }
}
