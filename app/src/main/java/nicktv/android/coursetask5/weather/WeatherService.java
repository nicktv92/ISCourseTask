package nicktv.android.coursetask5.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {
    @GET("api/location/{path}/")
    Call<JSONResponse> getWeatherData(@Path("path") int search);
}
