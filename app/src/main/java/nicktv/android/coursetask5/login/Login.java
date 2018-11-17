package nicktv.android.coursetask5.login;

public class Login {
    private String login;
    private String id;

    public Login (String login, String id){
        this.login = login;
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getId() {
        return id;
    }
}
