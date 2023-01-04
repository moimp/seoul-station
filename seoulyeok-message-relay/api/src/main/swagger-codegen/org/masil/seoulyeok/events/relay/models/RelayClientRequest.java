package org.masil.seoulyeok.events.relay.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * RelayClientRequest
 */@javax.annotation.Generated(value = "com.trevari.oas.codegen.spring.TrevariSpringCodeGenerator")
public class RelayClientRequest  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("destinationType")
  private String destinationType;

  @JsonProperty("messagePayload")
  private String messagePayload;

  @JsonProperty("destinationName")
  private String destinationName;

  @JsonProperty("payloadVersion")
  private String payloadVersion;

  public RelayClientRequest destinationType(String destinationType) {
    this.destinationType = destinationType;
    return this;
  }

  /**
   * Get destinationType
   * @return destinationType
  */

  @NotNull


  public String getDestinationType() {
    return destinationType;
  }

  public void setDestinationType(String destinationType) {
    this.destinationType = destinationType;
  }

  public RelayClientRequest messagePayload(String messagePayload) {
    this.messagePayload = messagePayload;
    return this;
  }

  /**
   * Get messagePayload
   * @return messagePayload
  */

  @NotNull


  public String getMessagePayload() {
    return messagePayload;
  }

  public void setMessagePayload(String messagePayload) {
    this.messagePayload = messagePayload;
  }

  public RelayClientRequest destinationName(String destinationName) {
    this.destinationName = destinationName;
    return this;
  }

  /**
   * Get destinationName
   * @return destinationName
  */

  @NotNull


  public String getDestinationName() {
    return destinationName;
  }

  public void setDestinationName(String destinationName) {
    this.destinationName = destinationName;
  }

  public RelayClientRequest payloadVersion(String payloadVersion) {
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
    RelayClientRequest relayClientRequest = (RelayClientRequest) o;
    return Objects.equals(this.destinationType, relayClientRequest.destinationType) &&
        Objects.equals(this.messagePayload, relayClientRequest.messagePayload) &&
        Objects.equals(this.destinationName, relayClientRequest.destinationName) &&
        Objects.equals(this.payloadVersion, relayClientRequest.payloadVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(destinationType, messagePayload, destinationName, payloadVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RelayClientRequest {\n");

    sb.append("    destinationType: ").append(toIndentedString(destinationType)).append("\n");
    sb.append("    messagePayload: ").append(toIndentedString(messagePayload)).append("\n");
    sb.append("    destinationName: ").append(toIndentedString(destinationName)).append("\n");
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

