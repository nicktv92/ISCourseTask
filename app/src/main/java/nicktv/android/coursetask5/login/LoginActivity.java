package nicktv.android.coursetask5.login;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ResponseTypeValues;

import nicktv.android.coursetask5.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthStateLogin stateLogin = new AuthStateLogin(this);
        if (stateLogin.readAuthState().isAuthorized()){
            Intent intent = new Intent (this, ResultActivity.class);
            startActivity(intent);
            return;
        }

        setContentView(R.layout.activity_login);

        AuthorizationServiceConfiguration serviceConfiguration = new AuthorizationServiceConfiguration(
                Uri.parse(LoginConstants.AUTHURL) /* auth endpoint */,
                Uri.parse(LoginConstants.TOKENURL) /* token endpoint */
        );

        AuthorizationRequest.Builder builder = new AuthorizationRequest.Builder(
                serviceConfiguration,
                LoginConstants.CLIENT_ID,
                ResponseTypeValues.CODE,
                LoginConstants.REDIRECT_URI
        );
        AuthorizationRequest request = builder.build();

        AuthorizationService authService = new AuthorizationService(this);
        authService.performAuthorizationRequest(
                request,
                PendingIntent.getActivity(this,0, new Intent(
                        this, ResultActivity.class), 0)
        );
    }
}
