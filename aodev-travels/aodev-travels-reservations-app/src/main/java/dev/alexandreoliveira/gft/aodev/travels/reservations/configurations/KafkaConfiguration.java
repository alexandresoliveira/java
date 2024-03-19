package dev.alexandreoliveira.gft.aodev.travels.reservations.configurations;

import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.subscriptions.kafka.reservations.locks.flights.ReservationsLocksFlightsSubscriptionMessage;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.subscriptions.kafka.reservations.locks.hotels.ReservationsLocksHotelsSubscriptionMessage;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.subscriptions.kafka.reservations.locks.transfers.ReservationsLocksTransfersSubscriptionMessage;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.events.dto.destinations.CreateDestinationEvent;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.events.dto.destinations.UpdateDestinationEvent;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.TransactionManager;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfiguration {

    public static final String KAFKA_TOPIC_LOCK_HOTEL = "LOCK_HOTELS";
    public static final String KAFKA_TOPIC_LOCK_FLIGHTS = "LOCK_FLIGHTS";
    public static final String KAFKA_TOPIC_LOCK_TRANSFERS = "LOCK_TRANSFERS";

    public static final String KAFKA_TOPIC_TRAVEL_HOTELS_LOCK_LISTEN = "TRAVEL_HOTELS_LOCK";
    public static final String KAFKA_TOPIC_TRAVEL_FLIGHTS_LOCK_LISTEN = "TRAVEL_FLIGHTS_LOCK";
    public static final String KAFKA_TOPIC_TRAVEL_TRANSFERS_LOCK_LISTEN = "TRAVEL_TRANSFERS_LOCK";

    public static final String KAFKA_TOPIC_TRAVEL_CQRS_DESTINATION_EVENTS = "TRAVEL_CQRS_DESTINATION_EVENTS";

    private static final String KAFKA_ADDRESS = "localhost:9092";

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_ADDRESS);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic lockHotels() {
        return TopicBuilder.name(KAFKA_TOPIC_LOCK_HOTEL)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic lockFlights() {
        return TopicBuilder.name(KAFKA_TOPIC_LOCK_FLIGHTS)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic lockTransfers() {
        return TopicBuilder.name(KAFKA_TOPIC_LOCK_TRANSFERS)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic cqrsDestinationTopic() {
        return TopicBuilder.name(KAFKA_TOPIC_TRAVEL_CQRS_DESTINATION_EVENTS)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_ADDRESS);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "app-reservations-tx-");
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name = "writeKafkaTransactionManager")
    public TransactionManager writeKafkaTransactionManager() {
        return new KafkaTransactionManager<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        ProducerFactory<String, Object> producerFactory = producerFactory();
        producerFactory.transactionCapable();
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        String trustedPackage = String.join(
                ",",
                CreateDestinationEvent.class.getPackageName(),
                UpdateDestinationEvent.class.getPackageName(),
                ReservationsLocksFlightsSubscriptionMessage.class.getPackageName(),
                ReservationsLocksHotelsSubscriptionMessage.class.getPackageName(),
                ReservationsLocksTransfersSubscriptionMessage.class.getPackageName());
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_ADDRESS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, trustedPackage);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    @Primary
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
