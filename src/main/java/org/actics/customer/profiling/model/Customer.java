package org.actics.customer.profiling.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Customer
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-21T22:01:26.297063+01:00[Europe/Zurich]", comments = "Generator version: 7.11.0")
public class Customer {

  private UUID id;
  private String firstName;
  private String lastName;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate birthdate;

  private @Nullable CustomerContactDetails contactDetails;
  private CustomerProfileEconomicCircumstances economicCircumstances;
  private RiskToleranceEnum riskTolerance;

  private @Nullable String investmentExperience;
  private @Nullable String investmentObjectives;

  public Customer() {}

  public Customer(
          UUID id,
          String firstName,
          String lastName,
          LocalDate birthdate,
          @Nullable CustomerContactDetails contactDetails,
          CustomerProfileEconomicCircumstances economicCircumstances,
          RiskToleranceEnum riskTolerance,
          @Nullable String investmentExperience,
          @Nullable String investmentObjectives) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthdate = birthdate;
    this.contactDetails = contactDetails;
    this.economicCircumstances = economicCircumstances;
    this.riskTolerance = riskTolerance;
    this.investmentExperience = investmentExperience;
    this.investmentObjectives = investmentObjectives;
  }

  @NotNull
  @Schema(name = "id", example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @NotNull
  @Schema(name = "firstName", example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("firstName")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @NotNull
  @Schema(name = "lastName", example = "Doe", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("lastName")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @NotNull
  @Valid
  @Schema(name = "birthdate", example = "1980-01-01", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("birthdate")
  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  @Nullable
  @Valid
  @Schema(name = "contactDetails", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contactDetails")
  public CustomerContactDetails getContactDetails() {
    return contactDetails;
  }

  public void setContactDetails(@Nullable CustomerContactDetails contactDetails) {
    this.contactDetails = contactDetails;
  }

  @NotNull
  @Valid
  @Schema(name = "economicCircumstances", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("economicCircumstances")
  public CustomerProfileEconomicCircumstances getEconomicCircumstances() {
    return economicCircumstances;
  }

  public void setEconomicCircumstances(CustomerProfileEconomicCircumstances economicCircumstances) {
    this.economicCircumstances = economicCircumstances;
  }

  @NotNull
  @Schema(name = "riskTolerance", example = "Medium", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("riskTolerance")
  public RiskToleranceEnum getRiskTolerance() {
    return riskTolerance;
  }

  public void setRiskTolerance(RiskToleranceEnum riskTolerance) {
    this.riskTolerance = riskTolerance;
  }

  @Nullable
  @Schema(name = "investmentExperience", example = "Moderate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("investmentExperience")
  public String getInvestmentExperience() {
    return investmentExperience;
  }

  public void setInvestmentExperience(@Nullable String investmentExperience) {
    this.investmentExperience = investmentExperience;
  }

  @Nullable
  @Schema(name = "investmentObjectives", example = "Wealth accumulation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("investmentObjectives")
  public String getInvestmentObjectives() {
    return investmentObjectives;
  }

  public void setInvestmentObjectives(@Nullable String investmentObjectives) {
    this.investmentObjectives = investmentObjectives;
  }

  // Enum für RiskTolerance
  public enum RiskToleranceEnum {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private final String value;

    RiskToleranceEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static RiskToleranceEnum fromValue(String value) {
      for (RiskToleranceEnum b : RiskToleranceEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @Override
  public String toString() {
    return "Customer{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", birthdate=" + birthdate +
            ", contactDetails=" + contactDetails +
            ", economicCircumstances=" + economicCircumstances +
            ", riskTolerance=" + riskTolerance +
            ", investmentExperience='" + investmentExperience + '\'' +
            ", investmentObjectives='" + investmentObjectives + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customer that = (Customer) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(birthdate, that.birthdate) &&
            Objects.equals(contactDetails, that.contactDetails) &&
            Objects.equals(economicCircumstances, that.economicCircumstances) &&
            riskTolerance == that.riskTolerance &&
            Objects.equals(investmentExperience, that.investmentExperience) &&
            Objects.equals(investmentObjectives, that.investmentObjectives);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, birthdate, contactDetails, economicCircumstances, riskTolerance, investmentExperience, investmentObjectives);
  }
}
