package dev.alexandreoliveira.mp.registrationservice.database.jpa.redis;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Map;

public record FilterDTO(
  @NotEmpty String page,
  @NotEmpty String username,
  @NotEmpty Map<String, ? extends Serializable> data
) {
}
