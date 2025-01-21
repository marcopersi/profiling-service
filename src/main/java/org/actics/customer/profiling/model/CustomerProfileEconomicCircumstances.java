package org.actics.customer.profiling.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.math.BigDecimal;
import org.springframework.lang.Nullable;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * CustomerProfileEconomicCircumstances
 */

@JsonTypeName("CustomerProfile_economicCircumstances")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-21T22:01:26.297063+01:00[Europe/Zurich]", comments = "Generator version: 7.11.0")
public class CustomerProfileEconomicCircumstances {

  private @Nullable BigDecimal annualIncome;

  private @Nullable BigDecimal netWorth;

  private @Nullable BigDecimal liabilities;

  public CustomerProfileEconomicCircumstances annualIncome(BigDecimal annualIncome) {
    this.annualIncome = annualIncome;
    return this;
  }

  /**
   * Get annualIncome
   * @return annualIncome
   */
  @Valid 
  @Schema(name = "annualIncome", example = "100000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("annualIncome")
  public BigDecimal getAnnualIncome() {
    return annualIncome;
  }

  public void setAnnualIncome(BigDecimal annualIncome) {
    this.annualIncome = annualIncome;
  }

  public CustomerProfileEconomicCircumstances netWorth(BigDecimal netWorth) {
    this.netWorth = netWorth;
    return this;
  }

  /**
   * Get netWorth
   * @return netWorth
   */
  @Valid 
  @Schema(name = "netWorth", example = "500000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("netWorth")
  public BigDecimal getNetWorth() {
    return netWorth;
  }

  public void setNetWorth(BigDecimal netWorth) {
    this.netWorth = netWorth;
  }

  public CustomerProfileEconomicCircumstances liabilities(BigDecimal liabilities) {
    this.liabilities = liabilities;
    return this;
  }

  /**
   * Get liabilities
   * @return liabilities
   */
  @Valid 
  @Schema(name = "liabilities", example = "100000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("liabilities")
  public BigDecimal getLiabilities() {
    return liabilities;
  }

  public void setLiabilities(BigDecimal liabilities) {
    this.liabilities = liabilities;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerProfileEconomicCircumstances customerProfileEconomicCircumstances = (CustomerProfileEconomicCircumstances) o;
    return Objects.equals(this.annualIncome, customerProfileEconomicCircumstances.annualIncome) &&
        Objects.equals(this.netWorth, customerProfileEconomicCircumstances.netWorth) &&
        Objects.equals(this.liabilities, customerProfileEconomicCircumstances.liabilities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(annualIncome, netWorth, liabilities);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomerProfileEconomicCircumstances {\n");
    sb.append("    annualIncome: ").append(toIndentedString(annualIncome)).append("\n");
    sb.append("    netWorth: ").append(toIndentedString(netWorth)).append("\n");
    sb.append("    liabilities: ").append(toIndentedString(liabilities)).append("\n");
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

