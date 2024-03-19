package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.subscriptions.kafka.reservations.locks.flights;

import dev.alexandreoliveira.gft.aodev.travels.reservations.configurations.KafkaConfiguration;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.services.ReservationsService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "travel-flights-returned", topics = {KafkaConfiguration.KAFKA_TOPIC_TRAVEL_FLIGHTS_LOCK_LISTEN}, groupId = "0")
public class ReservationsLocksFlightsSubscription {

    private final ReservationsService service;

    public ReservationsLocksFlightsSubscription(ReservationsService service) {
        this.service = service;
    }

    @KafkaHandler
    public void listen(@Payload ReservationsLocksFlightsSubscriptionMessage message) {
        service.updateFlightsLockReservation(message);
    }
}
