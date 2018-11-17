package nicktv.android.coursetask5.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("location/search/")
    Call<List<CityList>> getCityData(@Query("query") String search);
}
