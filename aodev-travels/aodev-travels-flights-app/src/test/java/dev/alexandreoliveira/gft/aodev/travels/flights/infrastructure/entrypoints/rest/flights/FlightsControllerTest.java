package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights;

import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.create.FlightsControllerCreateResponse;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.index.FlightsControllerIndexResponse;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.services.FlightsService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlightsController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FlightsService mockFlightsService;

    @Test
    @Order(1)
    void shouldWhenCreatedExpectedErrorWhenDataIsInvalid() throws Exception {
        var request = "{}".getBytes(StandardCharsets.UTF_8);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(new URI("/flights"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", is(not(emptyArray()))));

        clearInvocations(mockFlightsService);
        reset(mockFlightsService);
    }

    @Test
    @Order(2)
    void shouldWhenCreatedExpectedAnswerWhenDataAreCorrect() throws Exception {
        var request = """
                {
                	"company": "Azul",
                	"flightNumber": 123,
                	"seats": [
                		{ "seatNumber": "01A" },
                		{ "seatNumber": "01B" },
                		{ "seatNumber": "01C" },
                		{ "seatNumber": "01D" },
                		{ "seatNumber": "01E" },
                		{ "seatNumber": "01F" },
                		{ "seatNumber": "07B" },
                		{ "seatNumber": "07E" },
                		{ "seatNumber": "07F" }
                	],
                	"from": "(CFN) Belo Horizonte, Minas Gerais",
                	"to": "(REC) Recife, Pernanbuco",
                	"checkIn": "2024-07-15T08:25:43-0300",
                	"checkOut": "2014-07-22T18:25:43-0300",
                	"price": 1200.00
                }
                """.getBytes(StandardCharsets.UTF_8);

        doReturn(new FlightsControllerCreateResponse(UUID.randomUUID()))
                .when(mockFlightsService)
                .create(any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(new URI("/flights"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andExpect(jsonPath("$.id", is(not(emptyString()))));

        clearInvocations(mockFlightsService);
        reset(mockFlightsService);
    }

    @Test
    @Order(3)
    void shouldWhenIndexExpectedErrorWhenDataIsInvalid() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(new URI("/flights"));

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", is(not(emptyArray()))));

        clearInvocations(mockFlightsService);
        reset(mockFlightsService);
    }

    @Test
    @Order(4)
    void shouldWhenIndexExpectedAnswerWhenDataAreCorrect() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(new URI("/flights"))
                .param("cityOrigin", "Belo Hori")
                .param("cityDestiny", "Rec");

        FlightsControllerIndexResponse.Flight fakeFlight = new FlightsControllerIndexResponse.Flight(
                UUID.randomUUID(),
                "company",
                123,
                "from",
                "to",
                Collections.emptyList(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                BigDecimal.ZERO);
        FlightsControllerIndexResponse fakeResponse = new FlightsControllerIndexResponse(List.of(fakeFlight));

        doReturn(fakeResponse)
                .when(mockFlightsService)
                .index(any());

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flights").isArray())
                .andExpect(jsonPath("$.flights", is(not(emptyArray()))));

        clearInvocations(mockFlightsService);
        reset(mockFlightsService);
    }

}
