package dev.alexandreoliveira.apps.tictactoe.shared.restclients.amazon;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("dev.alexandreoliveira.apps.tictactoe.shared.restclients.amazon.AmazonNotificationRestClientTest")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AmazonNotificationRestClientTest {
  private final BasicJsonTester json = new BasicJsonTester(this.getClass());

  @Test
  @Order(1)
  void must_be_response_status_created_when_send_a_message_with_mock_server() {
    try (MockWebServer mockWebServer = new MockWebServer()) {

      MockResponse mockResponse = new MockResponse()
        .setResponseCode(201)
        .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
        .setBody("Success");

      mockWebServer.enqueue(mockResponse);

      var sut = new AmazonNotificationRestClient(WebClient.builder(), "http://localhost:9090");

      UUID fakeId = UUID.randomUUID();

      mockWebServer.start(9090);
      sut.sendNotification(fakeId, "test message");
      mockWebServer.shutdown();

      RecordedRequest recordedRequest = mockWebServer.takeRequest();
      JsonContent<Object> body = json.from(recordedRequest.getBody().readUtf8());

      assertThat(body).extractingJsonPathStringValue("$.message").isEqualTo("test message");
      assertThat(recordedRequest.getMethod()).isEqualTo("POST");
      assertThat(recordedRequest.getPath()).isEqualTo(String.format("/users/%s/notify", fakeId));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
