package org.actics.customer.profiling.model;

import java.math.BigDecimal;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import org.springframework.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * CustomerProfileEconomicCircumstances
 */
@JsonTypeName("CustomerProfile_economicCircumstances")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-21T22:01:26.297063+01:00[Europe/Zurich]", comments = "Generator version: 7.11.0")
public class CustomerProfileEconomicCircumstances {

  private @Nullable BigDecimal annualIncome;
  private @Nullable BigDecimal netWorth;
  private @Nullable BigDecimal liabilities;

  // Standard-Konstruktor
  public CustomerProfileEconomicCircumstances() {}

  // Konstruktor mit Feldern
  public CustomerProfileEconomicCircumstances(@Nullable BigDecimal annualIncome, @Nullable BigDecimal netWorth, @Nullable BigDecimal liabilities) {
    this.annualIncome = annualIncome;
    this.netWorth = netWorth;
    this.liabilities = liabilities;
  }

  public CustomerProfileEconomicCircumstances annualIncome(BigDecimal annualIncome) {
    this.annualIncome = annualIncome;
    return this;
  }

  /**
   * Get annualIncome
   * @return annualIncome
   */
  @Nullable
  @Valid
  @Schema(name = "annualIncome", example = "100000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("annualIncome")
  public BigDecimal getAnnualIncome() {
    return annualIncome;
  }

  public void setAnnualIncome(@Nullable BigDecimal annualIncome) {
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
  @Nullable
  @Valid
  @Schema(name = "netWorth", example = "500000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("netWorth")
  public BigDecimal getNetWorth() {
    return netWorth;
  }

  public void setNetWorth(@Nullable BigDecimal netWorth) {
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
  @Nullable
  @Valid
  @Schema(name = "liabilities", example = "100000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("liabilities")
  public BigDecimal getLiabilities() {
    return liabilities;
  }

  public void setLiabilities(@Nullable BigDecimal liabilities) {
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
    CustomerProfileEconomicCircumstances that = (CustomerProfileEconomicCircumstances) o;
    return Objects.equals(this.annualIncome, that.annualIncome) &&
            Objects.equals(this.netWorth, that.netWorth) &&
            Objects.equals(this.liabilities, that.liabilities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(annualIncome, netWorth, liabilities);
  }

  @Override
  public String toString() {
    return "class CustomerProfileEconomicCircumstances {\n" +
            "    annualIncome: " + toIndentedString(annualIncome) + "\n" +
            "    netWorth: " + toIndentedString(netWorth) + "\n" +
            "    liabilities: " + toIndentedString(liabilities) + "\n" +
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
