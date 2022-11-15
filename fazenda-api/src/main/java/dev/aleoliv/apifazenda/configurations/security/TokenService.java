package dev.aleoliv.apifazenda.configurations.security;

import dev.aleoliv.apifazenda.database.jpa.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

  private static final String STARTED_BASE_API = "StartedBaseApi";

  @Value("${backend.jwt.secret}")
  private String secret;

  @Value("${backend.jwt.expiration}")
  private Long expiration;

  public String buildToken(
    Authentication authenticate
  ) {
    var userEntity = (UserEntity) authenticate.getPrincipal();
    var today = new Date();
    var expirationDate = new Date(today.getTime() + expiration);
    var secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    return Jwts
      .builder()
      .setIssuer(STARTED_BASE_API)
      .setSubject(userEntity.getId().toString())
      .setIssuedAt(today).setExpiration(expirationDate)
      .signWith(
        secretKey,
        SignatureAlgorithm.HS256
      ).compact();
  }

  public boolean isValidToken(
    String token
  ) {
    var secretKey = Keys.hmacShaKeyFor(secret.getBytes());

    try {
      Jwts
        .parserBuilder()
        .setSigningKey(secretKey)
        .requireIssuer(STARTED_BASE_API)
        .build()
        .parse(token);
      return true;
    } catch (Exception e) {
    }
    return false;
  }

  public UUID recoverUserId(
    String token
  ) throws Exception {
    if (token.contains("Bearer "))
      token = token.substring(7);

    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

    try {
      Claims body = (Claims) Jwts
        .parserBuilder()
        .setSigningKey(secretKey)
        .requireIssuer(STARTED_BASE_API)
        .build()
        .parse(token)
        .getBody();
      return UUID.fromString(body.getSubject());
    } catch (Exception e) {
      throw new Exception(
        "BadRequest - Subject token not found",
        e
      );
    }
  }
}
