package dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.subscriptions.rooms;

import dev.alexandreoliveira.gft.travels.rentcar.configurations.KafkaConfiguration;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.subscriptions.rooms.lock.TransfersLockSubscriptionMessage;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.services.TransfersService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(
        id = "travels-transfers-lock",
        topics = {KafkaConfiguration.KAFKA_TOPIC_LOCK_TRANSFERS},
        groupId = "0")
public class TransfersSubscription {

    private final TransfersService transfersService;

    public TransfersSubscription(TransfersService transfersService) {
        this.transfersService = transfersService;
    }

    @KafkaHandler
    public void listenLockMessage(@Payload TransfersLockSubscriptionMessage message) {
        transfersService.lock(message);
    }

    @KafkaHandler(isDefault = true)
    public void listenUnknown(@Payload Object message) {
        System.out.println("Unknown message: " + message.toString());
    }
}
