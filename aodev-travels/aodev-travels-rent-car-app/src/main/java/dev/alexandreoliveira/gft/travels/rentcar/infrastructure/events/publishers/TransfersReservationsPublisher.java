package dev.alexandreoliveira.gft.travels.rentcar.infrastructure.events.publishers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alexandreoliveira.gft.travels.rentcar.configurations.KafkaConfiguration;
import dev.alexandreoliveira.gft.travels.rentcar.core.models.TransferModel;
import dev.alexandreoliveira.gft.travels.rentcar.core.usecases.transfers.lock.TransfersLockReservationPublisher;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("transfersReservationsPublisher")
public class TransfersReservationsPublisher implements TransfersLockReservationPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TransfersReservationsPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void notify(TransferModel transferModel) {
        Map<String, Object> reservedTransfersData = HashMap.newHashMap(3);
        reservedTransfersData.put("reservationId", transferModel.getExternalId());
        reservedTransfersData.put("transferId", transferModel.getId());

        Map<String, Object> data = HashMap.newHashMap(1);
        data.put("reservedTransfers", List.of(reservedTransfersData));

        List <Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("__TypeId__", "dev.alexandreoliveira.gft.aodev.travels.infrastructure.entrypoints.subscriptions.kafka.reservations.locks.transfers.ReservationsLocksTransfersSubscriptionMessage".getBytes()));

        ProducerRecord<String, Object> record = new ProducerRecord<>(
                KafkaConfiguration.KAFKA_TOPIC_TRAVEL_TRANSFERS_LOCK_LISTEN,
                0,
                System.currentTimeMillis(),
                "lockTransfersResponse",
                data,
                headers
        );

        kafkaTemplate.send(record);
    }
}
