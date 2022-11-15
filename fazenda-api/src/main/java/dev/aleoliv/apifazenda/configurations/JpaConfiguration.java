package dev.aleoliv.apifazenda.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaConfiguration implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    return () -> {
      SecurityContext securityContext = SecurityContextHolder.getContext();

      if (securityContext == null) {
        throw new RuntimeException("Usuário não autenticado");
      }

      Authentication authentication = securityContext.getAuthentication();

      if (authentication != null &&
        authentication.isAuthenticated() &&
        authentication.getPrincipal() != null) {
        var username = authentication.getPrincipal().toString();
        return Optional.of(username);
      }

      throw new RuntimeException("Usuário não autenticado");
    };
  }
}
