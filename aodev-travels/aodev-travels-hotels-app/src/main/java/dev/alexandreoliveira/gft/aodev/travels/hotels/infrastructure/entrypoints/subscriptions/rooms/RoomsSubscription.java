package dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.subscriptions.rooms;

import dev.alexandreoliveira.gft.aodev.travels.hotels.configurations.KafkaConfiguration;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.subscriptions.rooms.lock.RoomsLockSubscriptionMessage;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.subscriptions.rooms.unlock.RoomsUnlockSubscriptionMessage;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.services.RoomsService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(
        id = "travels-hotels-lock",
        topics = {KafkaConfiguration.KAFKA_TOPIC_LOCK_HOTELS},
        groupId = "0"
)
public class RoomsSubscription {

    private final RoomsService roomsService;

    public RoomsSubscription(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @KafkaHandler
    public void listenLockMessage(@Payload RoomsLockSubscriptionMessage message) {
        roomsService.lockRoom(message);
    }

    @KafkaHandler
    public void listenUnlockMessage(RoomsUnlockSubscriptionMessage message) {
        roomsService.unlockRoom(message);
    }

    @KafkaHandler(isDefault = true)
    public void listenDefault(@Payload Object message) {
        System.out.println("Unknown message: " + message.toString());
    }
}
