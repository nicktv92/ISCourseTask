package nicktv.android.coursetask5.login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import net.openid.appauth.AuthState;

import org.json.JSONException;


class AuthStateLogin {

    private static final String AUTH_PREFERENCES_NAME = "AuthStatePreference";
    private static final String AUTH_STATE = "AUTH_STATE";

    @NonNull
    private Activity activity;

    AuthStateLogin(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    AuthState readAuthState() {
        SharedPreferences authPrefs = activity.getSharedPreferences(AUTH_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String stateStr = authPrefs.getString(AUTH_STATE, null);
        if (!TextUtils.isEmpty(stateStr)) {
            try {
                return AuthState.jsonDeserialize(stateStr);
            } catch (JSONException e) {
                Log.w("AUTH", e.getMessage(), e);
            }
        }
        return new AuthState();
    }

    void writeAuthState(@NonNull AuthState state) {
        SharedPreferences authPrefs = activity.getSharedPreferences(AUTH_PREFERENCES_NAME, Context.MODE_PRIVATE);
        authPrefs.edit()
                .putString(AUTH_STATE, state.jsonSerializeString())
                .apply();
    }

     void clearAuthState() {
        activity.getSharedPreferences(AUTH_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit()
                .remove(AUTH_STATE)
                .apply();
    }

}
