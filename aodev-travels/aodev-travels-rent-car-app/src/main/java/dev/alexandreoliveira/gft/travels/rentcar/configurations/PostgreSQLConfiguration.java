package dev.alexandreoliveira.gft.travels.rentcar.configurations;

import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.dateproviders.postgresql.entites.TransferEntity;
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
@EnableJpaRepositories(basePackages = {"dev.alexandreoliveira.gft.travels.rentcar.infrastructure.dateproviders.postgresql.repositories"})
@EnableJpaAuditing
public class PostgreSQLConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("TravelsRentCarApp");
    }

    @Bean(name = "jpaTransfersRepository")
    public JpaRepository<TransferEntity, UUID> jpaTransfersRepository(JpaContext jpaContext) {
        return new SimpleJpaRepository<>(TransferEntity.class, jpaContext.getEntityManagerByManagedType(TransferEntity.class));
    }
}
