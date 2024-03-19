package dev.alexandreoliveira.gft.aodev.travels.hotels.configurations;

import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.entites.HotelEntity;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.entites.RoomEntity;
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
@EnableJpaRepositories(basePackages = {"dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dataproviders.postgresql.repositories"})
@EnableJpaAuditing
public class PostgreSQLConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("TravelsFlightApp");
    }

    @Bean(name = "jpaHotelsRepository")
    public JpaRepository<HotelEntity, UUID> jpaHotelsRepository(JpaContext jpaContext) {
        return new SimpleJpaRepository<>(HotelEntity.class, jpaContext.getEntityManagerByManagedType(HotelEntity.class));
    }

    @Bean(name = "jpaRoomsRepository")
    public JpaRepository<RoomEntity, UUID> jpaRoomsRepository(JpaContext jpaContext) {
        return new SimpleJpaRepository<>(RoomEntity.class, jpaContext.getEntityManagerByManagedType(RoomEntity.class));
    }
}
