package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.subscriptions.seats;

import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.services.SeatsService;
import org.junit.ClassRule;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

import static dev.alexandreoliveira.gft.aodev.travels.flights.configurations.KafkaConfiguration.KAFKA_TOPIC_TRAVEL_FLIGHTS_LOCK_LISTEN;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SeatsSubscriptionTest {

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(1, true, KAFKA_TOPIC_TRAVEL_FLIGHTS_LOCK_LISTEN)
            .kafkaPorts(9092);

    @Mock
    SeatsService mockSeatsService;

    @Test
    @Order(1)
    void test() {
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaRule.getEmbeddedKafka());
        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("0", "true", embeddedKafkaRule.getEmbeddedKafka());
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);
        ContainerProperties containerProperties = new ContainerProperties(KAFKA_TOPIC_TRAVEL_FLIGHTS_LOCK_LISTEN);
        KafkaMessageListenerContainer<String, String> container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);

        // TODO: MAKE THIS TEST
    }
}
