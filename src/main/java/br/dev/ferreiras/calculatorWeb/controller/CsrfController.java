package br.dev.ferreiras.calculatorWeb.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (path = "api/v1")
public class CsrfController {

    @RequestMapping ("/csrf")
    public CsrfToken csrf(CsrfToken token) {
      return token;
    }
  }
