package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.subscriptions.kafka.destinations;

import dev.alexandreoliveira.gft.aodev.travels.reservations.configurations.KafkaConfiguration;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.DestinationEntity;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.repositories.destinations.WriteDestinationsRepository;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.events.dto.destinations.CreateDestinationEvent;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.events.dto.destinations.UpdateDestinationEvent;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@KafkaListener(
        id = "travels-cqrs-destination-events",
        topics = {KafkaConfiguration.KAFKA_TOPIC_TRAVEL_CQRS_DESTINATION_EVENTS}
)
@Transactional(value = "writeKafkaTransactionManager", rollbackFor = {Throwable.class})
public class DestinationsEventAggregate {

    private final WriteDestinationsRepository writeDestinationsRepository;

    public DestinationsEventAggregate(WriteDestinationsRepository writeDestinationsRepository) {
        this.writeDestinationsRepository = writeDestinationsRepository;
    }

    @KafkaHandler
    public void createDestination(CreateDestinationEvent createDestination) {
        var entity = new DestinationEntity();
        entity.setCity(createDestination.city());
        entity.setState(createDestination.state());
        writeDestinationsRepository.save(entity);
    }

    @KafkaHandler
    public void updateDestination(UpdateDestinationEvent updateDestination) {
        var entity = new DestinationEntity();
        entity.setId(updateDestination.id());
        entity.setCity(updateDestination.city());
        entity.setState(updateDestination.state());
        writeDestinationsRepository.update(entity);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        System.out.println(object.toString());
    }
}
