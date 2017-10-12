package project.packages.component;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Provides an interface for retrieving exchange rates.
 */
public interface FixerIo {

  @GET("/latest")
  Call<Rate> getRate(@Query("base") String base, @Query("symbols") String symbols);
}
