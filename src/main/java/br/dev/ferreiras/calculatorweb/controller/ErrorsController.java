package br.dev.ferreiras.calculatorweb.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorsController implements ErrorController {

  private final Logger logger = LoggerFactory.getLogger(ErrorsController.class);
  @RequestMapping ("/error")
  public String handleError(HttpServletRequest request) {

    logger.info("-----------Inside Custom Error Handler-------------");
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);


    if (status != null) {

      var statusCode  = Integer.parseInt(status.toString());

      if (statusCode == HttpStatus.NOT_FOUND.value()) {
        logger.info("-----------404 Error-------------");
        return "404-error";

      } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
        logger.info("-----------401 Error-------------");
        return "401-error";

      } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
        logger.info("-----------403 Error-------------");
        return "403-error";

      } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        logger.info("-----------500 Error-------------");
        return "500-error";
      }
    }

    return "error";
  }
}
