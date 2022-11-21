package dev.alexandreoliveira.mp.registrationservice.configurations.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@Configuration
@EnableJpaRepositories(basePackages = "dev.alexandreoliveira.mp.registrationservice.database.jpa.repositories")
@EntityScan(basePackages = "dev.alexandreoliveira.mp.registrationservice.database.jpa.entities")
@EnableJpaAuditing
public class JpaConfiguation {

  @Bean
  public AuditorAware<String> auditorAware() {
    return () -> Optional.of("registration-service");
  }
}
