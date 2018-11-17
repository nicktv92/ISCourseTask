package nicktv.android.coursetask5.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.ClientSecretBasic;
import net.openid.appauth.TokenResponse;

import nicktv.android.coursetask5.BuildConfig;
import nicktv.android.coursetask5.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultActivity extends AppCompatActivity {

    AuthState authState;
    TextView textLogin;
    TextView textId;
    AuthStateLogin stateLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textLogin = findViewById(R.id.text_login);
        textId = findViewById(R.id.text_id);

        stateLogin = new AuthStateLogin(this);
        if (stateLogin.readAuthState().isAuthorized()) {
            getLoginInfo(stateLogin.readAuthState().getAccessToken());
        } else getToken();
    }

    private void getToken() {
        AuthorizationService authService = new AuthorizationService(this);
        AuthorizationResponse resp = AuthorizationResponse.fromIntent(getIntent());
        AuthorizationException ex = AuthorizationException.fromIntent(getIntent());
        if (resp == null) {
            Toast.makeText(this, String.valueOf(ex), Toast.LENGTH_SHORT).show();
        } else {
            final ClientAuthentication clientAuth = new ClientSecretBasic(BuildConfig.APIKEY);
            authState = new AuthState();
            authService.performTokenRequest(
                    resp.createTokenExchangeRequest(),
                    clientAuth,
                    new AuthorizationService.TokenResponseCallback() {
                        @Override
                        public void onTokenRequestCompleted(
                                TokenResponse resp, AuthorizationException ex) {
                            if (resp != null) {
                                authState.update(resp, ex);
                                stateLogin.writeAuthState(authState);
                                getLoginInfo(resp.accessToken);
                            } else {
                                Toast.makeText(ResultActivity.this,
                                        "Token не получен: " + ex.errorDescription,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void getLoginInfo(String token) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LoginConstants.APIURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginService service = retrofit.create(LoginService.class);

        Call<Login> call = service.getLogin(token);
        call.enqueue(new Callback<Login>() {

            @Override
            public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                textLogin.setText(response.body().getLogin());
                textId.setText(response.body().getId());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ResultActivity.this, "Данные не получены",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}

