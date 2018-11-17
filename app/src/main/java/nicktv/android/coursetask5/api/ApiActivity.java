package nicktv.android.coursetask5.api;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import nicktv.android.coursetask5.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiActivity extends AppCompatActivity {
    private EditText searchTxt;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<CityList> cityLists = null;
    private final static String URL = "https://www.metaweather.com/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        searchTxt = (EditText) findViewById(R.id.editTextApi);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerApi);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (searchTxt.getText().length() != 0) getSearchCity(searchTxt.getText().toString());
    }

    public void onClickSearch(View view) {
        String search = String.valueOf(searchTxt.getText());
        if (search.isEmpty()) {
            Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT).show();
        } else {
            getSearchCity(search);
        }
    }

    private void getSearchCity(String search) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Search...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);

        Call<List<CityList>> call = service.getCityData(search);
        call.enqueue(new Callback<List<CityList>>() {
            @Override
            public void onResponse(@NonNull Call<List<CityList>> call, @NonNull Response<List<CityList>> response) {
                cityLists = response.body();
                ApiAdapter apiAdapter = new ApiAdapter(cityLists, getApplicationContext());
                recyclerView.setAdapter(apiAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<CityList>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ApiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
