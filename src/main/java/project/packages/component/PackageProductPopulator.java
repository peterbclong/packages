package project.packages.component;

import org.springframework.stereotype.Service;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Provides package and product population services.
 */
@Service
class PackageProductPopulator {
  private ProductsService products;

  public PackageProductPopulator(ProductsService products) {
    this.products = products;
  }

  Package populate(Package toPopulate) throws Exception {
    long totalPrice = 0;
    for (Product product : toPopulate.products) {
      Call<Product> call = products.getProduct(product.id);
      Response<Product> response = call.execute();
      if (response.isSuccessful()) {
        Product actualProduct = response.body();
        product.name = actualProduct.name;
        product.usdPrice = actualProduct.usdPrice;
        totalPrice += product.usdPrice;
      } else {
        throw new Exception(response.message());
      }
    }
    toPopulate.price = totalPrice;
    return toPopulate;
  }
}
