package project.packages.component;

import org.springframework.stereotype.Service;

import retrofit2.Response;

/**
 * Provides package price accumulation services.
 */
@Service
class PackagePriceAccumulator {
  private static final String BASE = "USD";
  private FixerIo fixer;

  PackagePriceAccumulator(FixerIo fixer) {
    this.fixer = fixer;
  }

  Package accumulate(Package toAccumulate, String currency) throws Exception {
    if (!BASE.equals(currency)) {
      long total = toAccumulate.products.stream().mapToLong(product -> product.usdPrice).sum();
      Response<Rate> response = fixer.getRate(BASE, currency).execute();
      if (response.isSuccessful()) {
        toAccumulate.price = total * response.body().rateFor(currency);
      } else {
        throw new Exception(response.message());
      }
    }
    return toAccumulate;
  }
}
