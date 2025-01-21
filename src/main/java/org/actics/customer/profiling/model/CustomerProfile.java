package org.actics.customer.profiling.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Generated;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * CustomerProfile
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-21T22:01:26.297063+01:00[Europe/Zurich]", comments = "Generator version: 7.11.0")
public class CustomerProfile {

  private String firstName;

  private String lastName;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate birthdate;

  private @Nullable CustomerProfileContactDetails contactDetails;

  private CustomerProfileEconomicCircumstances economicCircumstances;

  /**
   * Gets or Sets riskTolerance
   */
  public enum RiskToleranceEnum {
    LOW("Low"),
    
    MEDIUM("Medium"),
    
    HIGH("High");

    private String value;

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

  private RiskToleranceEnum riskTolerance;

  private @Nullable String investmentExperience;

  private @Nullable String investmentObjectives;

  public CustomerProfile() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CustomerProfile(String firstName, String lastName, LocalDate birthdate, CustomerProfileEconomicCircumstances economicCircumstances, RiskToleranceEnum riskTolerance) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthdate = birthdate;
    this.economicCircumstances = economicCircumstances;
    this.riskTolerance = riskTolerance;
  }

  public CustomerProfile firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
   */
  @NotNull 
  @Schema(name = "firstName", example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("firstName")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public CustomerProfile lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
   */
  @NotNull 
  @Schema(name = "lastName", example = "Doe", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("lastName")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public CustomerProfile birthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
    return this;
  }

  /**
   * Get birthdate
   * @return birthdate
   */
  @NotNull @Valid 
  @Schema(name = "birthdate", example = "Tue Jan 01 01:00:00 CET 1980", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("birthdate")
  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public CustomerProfile contactDetails(CustomerProfileContactDetails contactDetails) {
    this.contactDetails = contactDetails;
    return this;
  }

  /**
   * Get contactDetails
   * @return contactDetails
   */
  @Valid 
  @Schema(name = "contactDetails", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contactDetails")
  public CustomerProfileContactDetails getContactDetails() {
    return contactDetails;
  }

  public void setContactDetails(CustomerProfileContactDetails contactDetails) {
    this.contactDetails = contactDetails;
  }

  public CustomerProfile economicCircumstances(CustomerProfileEconomicCircumstances economicCircumstances) {
    this.economicCircumstances = economicCircumstances;
    return this;
  }

  /**
   * Get economicCircumstances
   * @return economicCircumstances
   */
  @NotNull @Valid 
  @Schema(name = "economicCircumstances", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("economicCircumstances")
  public CustomerProfileEconomicCircumstances getEconomicCircumstances() {
    return economicCircumstances;
  }

  public void setEconomicCircumstances(CustomerProfileEconomicCircumstances economicCircumstances) {
    this.economicCircumstances = economicCircumstances;
  }

  public CustomerProfile riskTolerance(RiskToleranceEnum riskTolerance) {
    this.riskTolerance = riskTolerance;
    return this;
  }

  /**
   * Get riskTolerance
   * @return riskTolerance
   */
  @NotNull 
  @Schema(name = "riskTolerance", example = "Medium", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("riskTolerance")
  public RiskToleranceEnum getRiskTolerance() {
    return riskTolerance;
  }

  public void setRiskTolerance(RiskToleranceEnum riskTolerance) {
    this.riskTolerance = riskTolerance;
  }

  public CustomerProfile investmentExperience(String investmentExperience) {
    this.investmentExperience = investmentExperience;
    return this;
  }

  /**
   * Get investmentExperience
   * @return investmentExperience
   */
  
  @Schema(name = "investmentExperience", example = "Moderate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("investmentExperience")
  public String getInvestmentExperience() {
    return investmentExperience;
  }

  public void setInvestmentExperience(String investmentExperience) {
    this.investmentExperience = investmentExperience;
  }

  public CustomerProfile investmentObjectives(String investmentObjectives) {
    this.investmentObjectives = investmentObjectives;
    return this;
  }

  /**
   * Get investmentObjectives
   * @return investmentObjectives
   */
  
  @Schema(name = "investmentObjectives", example = "Wealth accumulation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("investmentObjectives")
  public String getInvestmentObjectives() {
    return investmentObjectives;
  }

  public void setInvestmentObjectives(String investmentObjectives) {
    this.investmentObjectives = investmentObjectives;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerProfile customerProfile = (CustomerProfile) o;
    return Objects.equals(this.firstName, customerProfile.firstName) &&
        Objects.equals(this.lastName, customerProfile.lastName) &&
        Objects.equals(this.birthdate, customerProfile.birthdate) &&
        Objects.equals(this.contactDetails, customerProfile.contactDetails) &&
        Objects.equals(this.economicCircumstances, customerProfile.economicCircumstances) &&
        Objects.equals(this.riskTolerance, customerProfile.riskTolerance) &&
        Objects.equals(this.investmentExperience, customerProfile.investmentExperience) &&
        Objects.equals(this.investmentObjectives, customerProfile.investmentObjectives);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, birthdate, contactDetails, economicCircumstances, riskTolerance, investmentExperience, investmentObjectives);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomerProfile {\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    birthdate: ").append(toIndentedString(birthdate)).append("\n");
    sb.append("    contactDetails: ").append(toIndentedString(contactDetails)).append("\n");
    sb.append("    economicCircumstances: ").append(toIndentedString(economicCircumstances)).append("\n");
    sb.append("    riskTolerance: ").append(toIndentedString(riskTolerance)).append("\n");
    sb.append("    investmentExperience: ").append(toIndentedString(investmentExperience)).append("\n");
    sb.append("    investmentObjectives: ").append(toIndentedString(investmentObjectives)).append("\n");
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

