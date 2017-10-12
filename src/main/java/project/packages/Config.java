package project.packages;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import project.packages.component.FixerIo;
import project.packages.component.ProductsService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Provides runtime configuration.
 */
@Configuration
@EnableSwagger2
class Config {

  @Bean
  ProductsService productService() {
    String userPass = Base64.getEncoder().encodeToString("user:pass".getBytes());
    OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
    builder.addInterceptor(chain -> {
      Request request = chain.request().newBuilder().addHeader("Authorization", "Basic " + userPass).build();
      return chain.proceed(request);
    });
    OkHttpClient client = builder.build();
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://product-service.herokuapp.com").client(client)
        .addConverterFactory(JacksonConverterFactory.create()).build();
    return retrofit.create(ProductsService.class);
  }

  @Bean
  FixerIo fixerIo() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.fixer.io")
        .addConverterFactory(JacksonConverterFactory.create()).build();
    return retrofit.create(FixerIo.class);
  }

  @Bean
  Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("project.packages.web")).paths(regex("/packages.*")).build();

  }
}
