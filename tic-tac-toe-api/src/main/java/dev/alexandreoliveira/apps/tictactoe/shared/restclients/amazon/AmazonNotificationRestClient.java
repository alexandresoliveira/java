package dev.alexandreoliveira.apps.tictactoe.shared.restclients.amazon;

import dev.alexandreoliveira.apps.tictactoe.shared.restclients.NotificationRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.UUID;

@Component("amazonNotificationRestClient")
public class AmazonNotificationRestClient implements NotificationRestClient {

  private final WebClient.Builder builder;
  private final String baseUrl;

  public AmazonNotificationRestClient(
    WebClient.Builder builder,
    @Value("${app.notification.amazon.url}") String baseUrl) {
    this.builder = builder;
    this.baseUrl = baseUrl;
  }

  @Override
  public String sendNotification(
    UUID userId,
    String message
  ) {
    Map<String, String> request = Map.of("message", message);

    WebClient client = builder
      .baseUrl(baseUrl)
      .build();

    return client
      .post()
      .uri(
        "users/{id}/notify",
        userId
      )
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .bodyValue(request)
      .retrieve()
      .bodyToMono(String.class)
      .block();
  }
}
