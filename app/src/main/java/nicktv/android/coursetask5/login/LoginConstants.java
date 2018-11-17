package nicktv.android.coursetask5.login;

import android.net.Uri;

public class LoginConstants {

    public static final String AUTHURL="https://github.com/login/oauth/authorize";
    public static final String TOKENURL="https://github.com/login/oauth/access_token";
    public static final String APIURL="https://api.github.com";
    public static final String CLIENT_ID="***";
    public static final Uri REDIRECT_URI= Uri.parse("login://nicktv.android.coursetask5");

}
