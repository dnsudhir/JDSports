package dnsudhir.com.jdsports.interfaces;

import dnsudhir.com.jdsports.model.NavBO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceInterface {

  @GET("/stores/jdsports/nav") Call<NavBO> getNav(@Query("api_key") String api_key);
}
