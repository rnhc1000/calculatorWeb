package br.dev.ferreiras.calculatorWeb.dto;

import java.time.Instant;
import java.util.Objects;

public class CustomizedErrors {
  private final Instant timestamp;
  private final Integer status;
  private final String error;
  private final String path;

  public CustomizedErrors(Instant timestamp, Integer status, String error, String path) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.path = path;
  }

  public Instant timestamp() {
    return timestamp;
  }

  public Integer status() {
    return status;
  }

  public String error() {
    return error;
  }

  public String path() {
    return path;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (CustomizedErrors) obj;
    return Objects.equals(this.timestamp, that.timestamp) &&
           Objects.equals(this.status, that.status) &&
           Objects.equals(this.error, that.error) &&
           Objects.equals(this.path, that.path);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timestamp, status, error, path);
  }

  @Override
  public String toString() {
    return "CustomizedErrors[" +
           "timestamp=" + timestamp + ", " +
           "status=" + status + ", " +
           "error=" + error + ", " +
           "path=" + path + ']';
  }

}
