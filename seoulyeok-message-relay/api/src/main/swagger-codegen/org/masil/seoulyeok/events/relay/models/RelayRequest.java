package org.masil.seoulyeok.events.relay.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * RelayRequest
 */@javax.annotation.Generated(value = "com.trevari.oas.codegen.spring.TrevariSpringCodeGenerator")
public class RelayRequest  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("payload")
  private String payload;

  @JsonProperty("destinationId")
  private Long destinationId;

  @JsonProperty("payloadVersion")
  private String payloadVersion;

  public RelayRequest payload(String payload) {
    this.payload = payload;
    return this;
  }

  /**
   * Get payload
   * @return payload
  */

  @NotNull


  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  public RelayRequest destinationId(Long destinationId) {
    this.destinationId = destinationId;
    return this;
  }

  /**
   * Get destinationId
   * @return destinationId
  */

  @NotNull


  public Long getDestinationId() {
    return destinationId;
  }

  public void setDestinationId(Long destinationId) {
    this.destinationId = destinationId;
  }

  public RelayRequest payloadVersion(String payloadVersion) {
    this.payloadVersion = payloadVersion;
    return this;
  }

  /**
   * Get payloadVersion
   * @return payloadVersion
  */

  @NotNull

@Pattern(regexp = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?$")
  public String getPayloadVersion() {
    return payloadVersion;
  }

  public void setPayloadVersion(String payloadVersion) {
    this.payloadVersion = payloadVersion;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RelayRequest relayRequest = (RelayRequest) o;
    return Objects.equals(this.payload, relayRequest.payload) &&
        Objects.equals(this.destinationId, relayRequest.destinationId) &&
        Objects.equals(this.payloadVersion, relayRequest.payloadVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(payload, destinationId, payloadVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RelayRequest {\n");

    sb.append("    payload: ").append(toIndentedString(payload)).append("\n");
    sb.append("    destinationId: ").append(toIndentedString(destinationId)).append("\n");
    sb.append("    payloadVersion: ").append(toIndentedString(payloadVersion)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

