package org.actics.customer.profiling.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import org.springframework.lang.Nullable;

/**
 * CustomerContactDetails
 */
@JsonTypeName("CustomerProfile_contactDetails")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-21T22:01:26.297063+01:00[Europe/Zurich]", comments = "Generator version: 7.11.0")
public class CustomerContactDetails {

  private @Nullable String email;
  private @Nullable String phone;

  public CustomerContactDetails() {}

  public CustomerContactDetails(@Nullable String email, @Nullable String phone) {
    this.email = email;
    this.phone = phone;
  }

  public CustomerContactDetails email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  @Nullable
  @jakarta.validation.constraints.Email
  @Schema(name = "email", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(@Nullable String email) {
    this.email = email;
  }

  public CustomerContactDetails phone(String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * Get phone
   * @return phone
   */
  @Nullable
  @Schema(name = "phone", example = "+41791234567", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("phone")
  public String getPhone() {
    return phone;
  }

  public void setPhone(@Nullable String phone) {
    this.phone = phone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerContactDetails that = (CustomerContactDetails) o;
    return Objects.equals(this.email, that.email) &&
            Objects.equals(this.phone, that.phone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, phone);
  }

  @Override
  public String toString() {
    return "class CustomerContactDetails {\n" +
            "    email: " + toIndentedString(email) + "\n" +
            "    phone: " + toIndentedString(phone) + "\n" +
            "}";
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
