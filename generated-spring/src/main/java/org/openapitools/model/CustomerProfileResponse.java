package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.model.CustomerProfile;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * CustomerProfileResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-21T21:05:52.609592+01:00[Europe/Zurich]", comments = "Generator version: 7.11.0")
public class CustomerProfileResponse {

  private @Nullable String id;

  private @Nullable CustomerProfile profile;

  public CustomerProfileResponse id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  
  @Schema(name = "id", example = "c123456", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public CustomerProfileResponse profile(CustomerProfile profile) {
    this.profile = profile;
    return this;
  }

  /**
   * Get profile
   * @return profile
   */
  @Valid 
  @Schema(name = "profile", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("profile")
  public CustomerProfile getProfile() {
    return profile;
  }

  public void setProfile(CustomerProfile profile) {
    this.profile = profile;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerProfileResponse customerProfileResponse = (CustomerProfileResponse) o;
    return Objects.equals(this.id, customerProfileResponse.id) &&
        Objects.equals(this.profile, customerProfileResponse.profile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, profile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomerProfileResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    profile: ").append(toIndentedString(profile)).append("\n");
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

