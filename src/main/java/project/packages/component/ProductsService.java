package project.packages.component;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Provides an interface for retrieving product details.
 */
public interface ProductsService {

  @GET("/api/v1/products/{id}")
  Call<Product> getProduct(@Path("id") String id);
}
