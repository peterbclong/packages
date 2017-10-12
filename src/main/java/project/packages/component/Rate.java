package project.packages.component;

import java.util.Map;

/**
 * Models a (list of) currency exchange rates.
 */
class Rate {
  private String base;
  private String date;
  private Map<String, Double> rates;

  public Rate() {}

  public Rate(String base, String date, Map<String, Double> rates) {
    this.base = base;
    this.date = date;
    this.rates = rates;
  }

  public String getBase() {
    return base;
  }

  public String getDate() {
    return date;
  }

  public Map<String, Double> getRates() {
    return rates;
  }

  double rateFor(String currency) {
    return rates.get(currency);
  }
}
