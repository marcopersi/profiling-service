package org.actics.customer.profiling.model.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.*;

@JsonTypeName("CustomerProfile_contactDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerContactDetails {

  @Email
  @Schema(name = "email", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  private String email;

  @Schema(name = "phone", example = "+41791234567", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("phone")
  private String phone;
}
