package dev.alexandreoliveira.mp.registrationservice.services.users.save.filter;

import dev.alexandreoliveira.mp.registrationservice.database.jpa.redis.FilterDTO;

public interface UsersSaveFilterServiceDAO {
  void saveFilter(
    String key,
    FilterDTO filterData
  );
}
