package nicktv.android.coursetask5.weather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import nicktv.android.coursetask5.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Weather> weatherLists;
    private final static String URL = "https://www.metaweather.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        recyclerView = (RecyclerView) findViewById(R.id.weatherRecycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        int woeid = intent.getIntExtra("woeid", 0);

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle("weather forecast");

        getWeatherInCity(woeid);
    }

    private void getWeatherInCity(int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        WeatherService service = retrofit.create(WeatherService.class);

        Call<JSONResponse> call = service.getWeatherData(id);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(@NonNull Call<JSONResponse> call, @NonNull Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                weatherLists = new ArrayList<>(Arrays.asList(jsonResponse.getWeatherArray()));
                WeatherAdapter weatherAdapter = new WeatherAdapter(weatherLists, getApplicationContext());
                recyclerView.setAdapter(weatherAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(WeatherActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
