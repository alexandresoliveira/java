package dev.aleoliv.apifazenda.services;

import org.springframework.transaction.annotation.Transactional;

public interface ApiService<Input, Output> {

  @Transactional(rollbackFor = {Throwable.class})
  Output execute(Input input);
}
