package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.events.kafka.destinations;

import dev.alexandreoliveira.gft.aodev.travels.reservations.configurations.KafkaConfiguration;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.DestinationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.commands.DestinationsCreateCommand;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.commands.DestinationsUpdateCommand;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.events.dto.destinations.CreateDestinationEvent;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.events.dto.destinations.UpdateDestinationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@Transactional(value = "writeKafkaTransactionManager", rollbackFor = {Throwable.class})
public class DestinationsPublisherImpl implements DestinationsCreateCommand, DestinationsUpdateCommand {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DestinationsPublisherImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishCreate(DestinationModel model) {
        var createDestination = new CreateDestinationEvent(model.getCity(), model.getState());
        kafkaTemplate.executeInTransaction(callback ->
            kafkaTemplate.send(KafkaConfiguration.KAFKA_TOPIC_TRAVEL_CQRS_DESTINATION_EVENTS, createDestination)
        );
    }

    @Override
    public void publishUpdate(DestinationModel model) {
        var updateDestination = new UpdateDestinationEvent(model.getId(), model.getCity(), model.getState());
        kafkaTemplate.send(KafkaConfiguration.KAFKA_TOPIC_TRAVEL_CQRS_DESTINATION_EVENTS, updateDestination);
    }
}
