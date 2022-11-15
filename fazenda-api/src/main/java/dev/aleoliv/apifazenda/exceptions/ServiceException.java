package dev.aleoliv.apifazenda.exceptions;

import java.util.UUID;

public class ServiceException extends RuntimeException {
  private final UUID traceLoggerId;

  public ServiceException(
    String msg,
    UUID traceLoggerId
  ) {
    super(msg);
    this.traceLoggerId = traceLoggerId;
  }

  public UUID getTraceLoggerId() {
    return traceLoggerId;
  }
}
