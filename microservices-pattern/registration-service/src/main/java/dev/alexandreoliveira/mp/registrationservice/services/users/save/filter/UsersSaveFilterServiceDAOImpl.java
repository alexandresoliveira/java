package dev.alexandreoliveira.mp.registrationservice.services.users.save.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alexandreoliveira.mp.registrationservice.database.jpa.redis.FilterDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component("UsersSaveFilterServiceDAOImpl")
class UsersSaveFilterServiceDAOImpl implements UsersSaveFilterServiceDAO {

  private final RedisTemplate<String, String> redisTemplate;
  private final ObjectMapper objectMapper;

  public UsersSaveFilterServiceDAOImpl(
    RedisTemplate<String, String> redisTemplate,
    ObjectMapper objectMapper
  ) {
    this.redisTemplate = redisTemplate;
    this.objectMapper = objectMapper;
  }

  @Override
  public void saveFilter(
    String key,
    FilterDTO filterData
  ) {
    try {
      redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(filterData));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
