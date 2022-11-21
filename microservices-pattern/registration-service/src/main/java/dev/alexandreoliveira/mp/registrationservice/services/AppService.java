package dev.alexandreoliveira.mp.registrationservice.services;

public interface AppService<Input, Output> {
  Output execute(Input input);
}
