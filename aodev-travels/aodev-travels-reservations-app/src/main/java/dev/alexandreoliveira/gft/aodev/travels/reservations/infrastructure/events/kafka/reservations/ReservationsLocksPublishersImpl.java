package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.events.kafka.reservations;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alexandreoliveira.gft.aodev.travels.reservations.configurations.KafkaConfiguration;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.FlightModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.HotelModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.TransferModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.create.ReservationsCreatePublisher;
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

@Component
public class ReservationsLocksPublishersImpl implements ReservationsCreatePublisher {

    private final KafkaTemplate<String, Object> template;
    private final ObjectMapper objectMapper;

    public ReservationsLocksPublishersImpl(KafkaTemplate<String, Object> template) {
        this.template = template;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void lockHotels(UUID reservationId, HotelModel hotel) {
        Map<String, Object> data = new HashMap<>();
        data.put("reservationId", reservationId);
        data.put("hotelId", hotel.getExternalId());
        data.put("roomId", hotel.getRoom());

        List <Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("__TypeId__", "dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.subscriptions.rooms.lock.RoomsLockSubscriptionMessage".getBytes()));

        ProducerRecord<String, Object> record = new ProducerRecord<>(
                KafkaConfiguration.KAFKA_TOPIC_LOCK_HOTEL,
                0,
                System.currentTimeMillis(),
                "lockHotelRooms",
                data,
                headers
        );

        template.send(record);
    }

    @Override
    public void lockTransfers(UUID reservationId, List<? extends TransferModel> transfers) {
        Map<String, Object> data = new HashMap<>();
        data.put("reservationId", reservationId);
        data.put("transfers", transfers.stream().map(TransferModel::getExternalId).toList());

        List <Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("__TypeId__", "dev.alexandreoliveira.gft.travels.rentcar.infrastructure.entrypoints.subscriptions.rooms.lock.TransfersLockSubscriptionMessage".getBytes()));

        ProducerRecord<String, Object> record = new ProducerRecord<>(
                KafkaConfiguration.KAFKA_TOPIC_LOCK_TRANSFERS,
                0,
                System.currentTimeMillis(),
                "lockTransfers",
                data,
                headers
        );

        template.send(record);
    }

    @Override
    public void lockFlights(UUID reservationId, List<? extends FlightModel> flights) {
        List<Map<String, Object>> seatsData = new ArrayList<>();

        flights.forEach(f -> {
            List<Map<String, Object>> seats = f.getSeats().stream().map(s -> {
                Map<String, Object> seatData = HashMap.newHashMap(3);
                seatData.put("reservationId", reservationId);
                seatData.put("flightId", f.getExternalId());
                seatData.put("seatId", s.getExternalId());
                return seatData;
            }).toList();
            seatsData.addAll(seats);
        });

        Map<String, Object> data = HashMap.newHashMap(1);
        data.put("seats", seatsData);

        List <Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("__TypeId__", "dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.subscriptions.seats.reservations.SeatsReservationsSubscriptionMessage".getBytes()));

        ProducerRecord<String, Object> record = new ProducerRecord<>(
                KafkaConfiguration.KAFKA_TOPIC_LOCK_FLIGHTS,
                0,
                System.currentTimeMillis(),
                "lockFlightSeats",
                data,
                headers
        );

        template.send(record);
    }
}
