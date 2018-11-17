package nicktv.android.coursetask5.login;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginService {
    @GET("user")
    Call<Login> getLogin(@Query("access_token") String token);
}

