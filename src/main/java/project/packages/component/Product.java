package project.packages.component;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Models a product, one of a number in a package.
 */
public class Product implements Serializable {

  @JsonProperty
  String id;

  @JsonProperty
  String name;

  @JsonProperty
  long usdPrice;
}
