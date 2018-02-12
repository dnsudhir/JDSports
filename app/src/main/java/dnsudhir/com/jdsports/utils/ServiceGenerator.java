package dnsudhir.com.jdsports.utils;

import dnsudhir.com.jdsports.interfaces.ApiServiceInterface;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

  private static final String ROOT_URL = AppUrls.BASE_URL;

  private static HttpLoggingInterceptor loggingInterceptor =
      new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

  private static Retrofit.Builder builder =
      new Retrofit.Builder().baseUrl(ROOT_URL).addConverterFactory(GsonConverterFactory.create());

  private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

  private static Retrofit retrofit = builder.client(httpClient.readTimeout(60, TimeUnit.SECONDS)
      .connectTimeout(60, TimeUnit.SECONDS)
      .addInterceptor(loggingInterceptor)
      .build()).build();

  public static <S> S createService(Class<S> serviceClass) {
    return retrofit.create(serviceClass);
  }

  public static ApiServiceInterface getInstance() {
    return retrofit.create(ApiServiceInterface.class);
  }


}