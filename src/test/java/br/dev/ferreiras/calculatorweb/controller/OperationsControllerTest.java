package br.dev.ferreiras.calculatorweb.controller;

import br.dev.ferreiras.calculatorweb.repository.OperationsRepository;
import br.dev.ferreiras.calculatorweb.repository.RecordsRepository;
import br.dev.ferreiras.calculatorweb.repository.RoleRepository;
import br.dev.ferreiras.calculatorweb.repository.UserRepository;
import br.dev.ferreiras.calculatorweb.service.OperationsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OperationsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private OperationsService operationsService;

  @MockBean
  private OperationsRepository operationsRepository;

  @MockBean
  private RoleRepository roleRepository;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private RecordsRepository recordsRepository;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private TestRestTemplate template;

//  @BeforeEach
//  public void setup() {
//    mockMvc = MockMvcBuilders.standaloneSetup(new OperationsControllerTest())
//                              .apply(springSecurity())
//                              .build();
//  }

//  @Test
//  @WithMockUser (username = "admin@calculatorweb.com", roles = {"ROLE_ADMIN"})
//  void getOperators_ReturnsListOperators() throws Exception {
////get("/admin").with(user("admin").password("pass").roles("USER","ADMIN")))
//    when(this.operationsService.getOperationsCost()).thenReturn(List.of());
//    this.mockMvc.perform(
//                MockMvcRequestBuilders.get("/operators")
//                                      .with(user("admin").password("pass").roles("ROLE_ADMIN"))
//                                      .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(6)));
//  }

  @Test
   void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
    final ResponseEntity<String> result = this.template.withBasicAuth("admin@calculatorweb", "@c4lc5l4t0r")
                                                       .getForEntity("/operations", String.class);
    assertEquals(HttpStatus.OK, result.getStatusCode());
  }
}
