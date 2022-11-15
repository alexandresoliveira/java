package dev.aleoliv.apifazenda.services.animal.create;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public record AnimalCreateServiceInput(
  @NotNull
  UUID traceLoggerId,

  @NotNull
  @Size(min = 1, max = 80)
  String tag,

  @NotNull
  UUID farmId
) {
}
