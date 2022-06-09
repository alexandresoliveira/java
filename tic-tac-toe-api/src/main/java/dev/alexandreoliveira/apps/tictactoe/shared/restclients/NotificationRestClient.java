package dev.alexandreoliveira.apps.tictactoe.shared.restclients;

import java.util.UUID;

public interface NotificationRestClient {

  String sendNotification(UUID userId, String message);
}
