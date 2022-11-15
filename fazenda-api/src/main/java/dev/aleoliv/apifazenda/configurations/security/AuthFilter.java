package dev.aleoliv.apifazenda.configurations.security;

import dev.aleoliv.apifazenda.database.jpa.repositories.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class AuthFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final UserRepository userRepository;

  public AuthFilter(
    TokenService tokenService,
    UserRepository userRepository
  ) {
    this.tokenService = tokenService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    String token = recoverToken(request);
    if (tokenService.isValidToken(token)) {
      try {
        authorizationClient(token);
      } catch (Exception e) {
        throw new ServletException(e);
      }
    }
    filterChain.doFilter(
      request,
      response
    );
  }

  private void authorizationClient(
    String token
  ) throws Exception {
    UUID id = tokenService.recoverUserId(token);
    var userEntity = userRepository.findById(id).orElseThrow();
    var authentication =
      new UsernamePasswordAuthenticationToken(
        userEntity,
        null,
        userEntity.getAuthorities()
      );
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private String recoverToken(
    HttpServletRequest request
  ) {
    String header = request.getHeader("Authorization");
    if (header == null || !header.startsWith("Bearer "))
      return null;
    return header.substring(7);
  }

}
