package dev.aleoliv.apifazenda.configurations.security;

import dev.aleoliv.apifazenda.database.jpa.entities.AuthorityEntity;
import dev.aleoliv.apifazenda.database.jpa.repositories.AuthorityRepository;
import dev.aleoliv.apifazenda.database.jpa.repositories.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final AuthService authService;
  private final TokenService tokenService;
  private final UserRepository userRepository;

  public ApiSecurityConfiguration(
    AuthService authService,
    TokenService tokenService,
    UserRepository userRepository
  ) {
    this.authService = authService;
    this.tokenService = tokenService;
    this.userRepository = userRepository;
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(
    AuthenticationManagerBuilder auth
  ) throws Exception {
    auth.userDetailsService(authService).passwordEncoder(new BCryptPasswordEncoder());
  }

  @Override
  protected void configure(
    HttpSecurity http
  ) throws Exception {
    http.cors().and().csrf().disable().authorizeRequests()
        .antMatchers(
          HttpMethod.POST,
          "/v1/users/create"
        ).permitAll()
        .antMatchers(
          HttpMethod.POST,
          "/v1/auth/*"
        ).permitAll().anyRequest().authenticated().and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .addFilterBefore(
          new AuthFilter(
            tokenService,
            userRepository
          ),
          UsernamePasswordAuthenticationFilter.class
        );
  }

  @Override
  public void configure(
    WebSecurity web
  ) throws Exception {
    web.ignoring()
       .antMatchers(
         "/v2/api-docs",
         "/configuration/ui",
         "/swagger-resources/**",
         "/configuration/security",
         "/swagger-ui.html",
         "/webjars/**"
       );
  }

  @Bean
  public ApplicationRunner initializer(
    ApplicationContext context
  ) {
    return args -> {
      var authorityRepository = context.getBean(AuthorityRepository.class);

      authorityRepository.findByName("ROLE_ADMIN")
                         .orElseGet(() -> authorityRepository.save(new AuthorityEntity("ROLE_ADMIN")));

      authorityRepository.findByName("ROLE_USER")
                         .orElseGet(() -> authorityRepository.save(new AuthorityEntity("ROLE_USER")));

      authorityRepository.findByName("ROLE_DEVELOPER")
                         .orElseGet(() -> authorityRepository.save(new AuthorityEntity("ROLE_DEVELOPER")));
    };
  }

}
