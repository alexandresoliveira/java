package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.events.publishers;

import dev.alexandreoliveira.gft.aodev.travels.flights.configurations.KafkaConfiguration;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.SeatModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.reservations.SeatsReservationsPublisher;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component("seatsReservationsPublisher")
public class FlightsSeatsReservationsPublisher implements SeatsReservationsPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public FlightsSeatsReservationsPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishLockSeatEvents(UUID registrationId, List<? extends SeatModel> seats) {
        List<Map<String, Object>> reservations = seats.stream()
                .map(seatModel -> {
                    Map<String, Object> reserve = HashMap.newHashMap(3);
                    reserve.put("reservationId", seatModel.getExternalId());
                    reserve.put("flightId", seatModel.getFlight().getId());
                    reserve.put("seatId", seatModel.getId());
                    return reserve;
                }).toList();

        Map<String, Object> data = HashMap.newHashMap(1);
        data.put("reservations", reservations);

        List <Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("__TypeId__", "dev.alexandreoliveira.gft.aodev.travels.infrastructure.entrypoints.subscriptions.kafka.reservations.locks.flights.ReservationsLocksFlightsSubscriptionMessage".getBytes()));

        ProducerRecord<String, Object> record = new ProducerRecord<>(
                KafkaConfiguration.KAFKA_TOPIC_TRAVEL_FLIGHTS_LOCK_LISTEN,
                0,
                System.currentTimeMillis(),
                "lockFlightSeatsResponse",
                data,
                headers
        );

        kafkaTemplate.send(record);
    }
}
