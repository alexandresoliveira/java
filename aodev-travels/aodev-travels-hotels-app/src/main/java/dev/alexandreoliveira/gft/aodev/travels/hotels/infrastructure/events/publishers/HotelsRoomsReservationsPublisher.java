package dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.events.publishers;

import dev.alexandreoliveira.gft.aodev.travels.hotels.configurations.KafkaConfiguration;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.RoomModel;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.rooms.lock.RoomsLockReservationPublisher;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("roomsLockReservationPublisher")
public class HotelsRoomsReservationsPublisher implements RoomsLockReservationPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public HotelsRoomsReservationsPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void notifyForReservationLockRooms(RoomModel model) {
        Map<String, Object> data = HashMap.newHashMap(3);
        data.put("reservationId", model.getExternalId());
        data.put("hotelId", model.getHotel().getId());
        data.put("roomId", model.getId());

        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("__TypeId__", "dev.alexandreoliveira.gft.aodev.travels.infrastructure.entrypoints.subscriptions.kafka.reservations.locks.hotels.ReservationsLocksHotelsSubscriptionMessage".getBytes()));

        ProducerRecord<String, Object> record = new ProducerRecord<>(
                KafkaConfiguration.KAFKA_TOPIC_TRAVEL_HOTELS_LOCK_LISTEN,
                0,
                System.currentTimeMillis(),
                "lockHotelRoomsResponse",
                data,
                headers
        );

        kafkaTemplate.send(record);
    }
}
