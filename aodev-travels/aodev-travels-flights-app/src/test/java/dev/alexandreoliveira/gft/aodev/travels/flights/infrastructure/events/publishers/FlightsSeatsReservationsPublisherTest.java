package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.events.publishers;

import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.FlightEntity;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.SeatEntity;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.ClassRule;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static dev.alexandreoliveira.gft.aodev.travels.flights.configurations.KafkaConfiguration.KAFKA_TOPIC_TRAVEL_FLIGHTS_LOCK_LISTEN;
import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightsSeatsReservationsPublisherTest {

    private final BasicJsonTester jsonTester = new BasicJsonTester(this.getClass());

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(1, true, KAFKA_TOPIC_TRAVEL_FLIGHTS_LOCK_LISTEN)
            .kafkaPorts(9092);

    @Test
    void shouldCheckWhenMessageAreCorrect() throws InterruptedException, JsonProcessingException {
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaRule.getEmbeddedKafka());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        producerProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);
        ProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);
        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("0", "true", embeddedKafkaRule.getEmbeddedKafka());
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        DefaultKafkaConsumerFactory<String, Object> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);
        ContainerProperties containerProperties = new ContainerProperties(KAFKA_TOPIC_TRAVEL_FLIGHTS_LOCK_LISTEN);
        KafkaMessageListenerContainer<String, Object> container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        final BlockingQueue<ConsumerRecord<String, Object>> records = new LinkedBlockingQueue<>();
        container.setupMessageListener((MessageListener<String, Object>) record -> {
            System.out.println(record);
            records.add(record);
        });

        embeddedKafkaRule.getEmbeddedKafka().afterPropertiesSet();

        container.setBeanName("templateTests");
        container.start();

        ContainerTestUtils.waitForAssignment(container,
                embeddedKafkaRule.getEmbeddedKafka().getPartitionsPerTopic());

        var sut = new FlightsSeatsReservationsPublisher(kafkaTemplate);

        var flightEntity = new FlightEntity();
        flightEntity.setId(UUID.randomUUID());

        var fakeSeatEntity = new SeatEntity();
        fakeSeatEntity.setId(UUID.randomUUID());
        fakeSeatEntity.setFlight(flightEntity);
        fakeSeatEntity.setExternalId(UUID.randomUUID());

        sut.publishLockSeatEvents(fakeSeatEntity.getExternalId(), List.of(fakeSeatEntity));

        ConsumerRecord<String, Object> received = records.poll(10, TimeUnit.SECONDS);
        assertThat(received).isNotNull();

        JsonContent<Object> body = jsonTester.from(new ObjectMapper().writeValueAsString(received.value()));
        assertThat(body).extractingJsonPathArrayValue("$.reservations").hasSize(1);
        assertThat(body).extractingJsonPathStringValue("$.reservations[0].reservationId").isNotBlank();
        assertThat(body).extractingJsonPathStringValue("$.reservations[0].flightId").isNotBlank();
        assertThat(body).extractingJsonPathStringValue("$.reservations[0].seatId").isNotBlank();
    }
}
