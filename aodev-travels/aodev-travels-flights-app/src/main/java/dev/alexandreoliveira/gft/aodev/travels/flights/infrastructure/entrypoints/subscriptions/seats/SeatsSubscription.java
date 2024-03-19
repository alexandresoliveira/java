package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.subscriptions.seats;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alexandreoliveira.gft.aodev.travels.flights.configurations.KafkaConfiguration;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.subscriptions.seats.reservations.SeatsReservationsSubscriptionMessage;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.services.SeatsService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "travel-flights-seats", topics = {KafkaConfiguration.KAFKA_TOPIC_LOCK_FLIGHTS})
public class SeatsSubscription {

    private final SeatsService service;
    private final ObjectMapper objectMapper;

    public SeatsSubscription(SeatsService service) {
        this.service = service;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaHandler
    public void listenSeatReservation(@Payload String message) {
        try {
            SeatsReservationsSubscriptionMessage seatsReservationsSubscriptionMessage = objectMapper.readValue(message, SeatsReservationsSubscriptionMessage.class);
            service.reservation(seatsReservationsSubscriptionMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
