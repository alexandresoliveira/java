package dev.alexandreoliveira.mp.registrationservice.controllers.user.create;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alexandreoliveira.mp.registrationservice.services.AppService;
import dev.alexandreoliveira.mp.registrationservice.services.users.create.UsersCreateServiceInput;
import dev.alexandreoliveira.mp.registrationservice.services.users.create.UsersCreateServiceOutput;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserCreateController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsersCreateControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @Order(1)
  void shouldExceptionWhenDataIsNull() throws Exception {
    mockMvc
      .perform(post("/users/create").contentType(MediaType.APPLICATION_JSON).content("{}"))
      .andExpect(status().isBadRequest());
  }

  @Test
  @Order(2)
  void shouldExceptionWhenDataIsInvalid() throws Exception {
    var request = new UserCreateControllerRequest(
      "",
      "",
      ""
    );

    byte[] requestData = new ObjectMapper().writeValueAsBytes(request);

    mockMvc
      .perform(post("/users/create").contentType(MediaType.APPLICATION_JSON).content(requestData))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.errors").isArray())
      .andExpect(jsonPath(
        "$.errors",
        Matchers.hasSize(6)
      ));
  }

  @Test
  @Order(3)
  void mustReturnCorrectDataWhenRequestDataIsCorrect() throws Exception {
    var request = new UserCreateControllerRequest(
      "Test User",
      "test-user@email.com",
      "11122233"
    );

    byte[] requestData = new ObjectMapper().writeValueAsBytes(request);

    mockMvc
      .perform(post("/users/create").contentType(MediaType.APPLICATION_JSON).content(requestData))
      .andExpect(status().isCreated())
      .andExpect(header().exists("location"))
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.id").isNotEmpty());
  }

  @TestConfiguration
  static class UsersCreateControllerTestConfiguration {

    @Bean("UsersCreateService")
    public AppService<UsersCreateServiceInput, UsersCreateServiceOutput> service() {
      return input -> new UsersCreateServiceOutput(
        UUID.randomUUID(),
        "",
        ""
      );
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
      return new PlatformTransactionManager() {
        @Override
        public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
          return null;
        }

        @Override
        public void commit(TransactionStatus status) throws TransactionException {

        }

        @Override
        public void rollback(TransactionStatus status) throws TransactionException {

        }
      };
    }
  }
}
