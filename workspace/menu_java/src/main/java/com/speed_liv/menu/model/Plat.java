package com.speed_liv.menu.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Plat
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-10-18T08:35:46.768096443Z[Etc/UTC]", comments = "Generator version: 7.7.0")
public class Plat {

  private Long id;

  private String name;

  private Double price;

  private Boolean disponible;

  public Plat() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Plat(Long id, String name, Double price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public Plat id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @NotNull 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Plat name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull 
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Plat price(Double price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
   */
  @NotNull 
  @Schema(name = "price", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("price")
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Plat disponible(Boolean disponible) {
    this.disponible = disponible;
    return this;
  }

  /**
   * Disponibilité (false si rupture de stock)
   * @return disponible
   */
  
  @Schema(name = "disponible", description = "Disponibilité (false si rupture de stock)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("disponible")
  public Boolean getDisponible() {
    return disponible;
  }

  public void setDisponible(Boolean disponible) {
    this.disponible = disponible;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Plat plat = (Plat) o;
    return Objects.equals(this.id, plat.id) &&
        Objects.equals(this.name, plat.name) &&
        Objects.equals(this.price, plat.price) &&
        Objects.equals(this.disponible, plat.disponible);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, price, disponible);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Plat {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    disponible: ").append(toIndentedString(disponible)).append("\n");
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

