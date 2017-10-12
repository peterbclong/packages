package project.packages.component;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Models a package.
 */
public class Package implements Serializable {

  @JsonProperty
  Long id;

  @JsonProperty
  String name;

  @JsonProperty
  String description;

  @JsonProperty
  List<Product> products;

  @JsonProperty
  double price;
}

