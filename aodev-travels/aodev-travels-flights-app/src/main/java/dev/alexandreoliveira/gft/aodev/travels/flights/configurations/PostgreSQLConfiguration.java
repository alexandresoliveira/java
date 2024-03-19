package dev.alexandreoliveira.gft.aodev.travels.flights.configurations;

import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.FlightEntity;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.SeatEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.repositories"})
@EnableJpaAuditing
public class PostgreSQLConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("TravelsFlightApp");
    }

    @Bean(name = "SeatsRepository")
    public JpaRepository<SeatEntity, UUID> seatsRepository(JpaContext jpaContext) {
        return new SimpleJpaRepository<>(SeatEntity.class, jpaContext.getEntityManagerByManagedType(SeatEntity.class));
    }

    @Bean(name = "FlightsRepository")
    public JpaRepository<FlightEntity, UUID> flightsRepository(JpaContext jpaContext) {
        return new SimpleJpaRepository<>(FlightEntity.class, jpaContext.getEntityManagerByManagedType(FlightEntity.class));
    }
}
