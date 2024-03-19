package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.subscriptions.kafka.reservations.locks.hotels;

import dev.alexandreoliveira.gft.aodev.travels.reservations.configurations.KafkaConfiguration;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.services.ReservationsService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "travels-hotels-returned", topics = {KafkaConfiguration.KAFKA_TOPIC_TRAVEL_HOTELS_LOCK_LISTEN}, groupId = "0")
public class ReservationsLocksHotelsSubscription {

    private final ReservationsService service;

    public ReservationsLocksHotelsSubscription(ReservationsService service) {
        this.service = service;
    }

    @KafkaHandler
    public void listen(@Payload ReservationsLocksHotelsSubscriptionMessage message) {
        service.updateHotelsLockReservation(message);
    }
}
