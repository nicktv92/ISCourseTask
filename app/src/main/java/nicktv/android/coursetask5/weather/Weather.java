package nicktv.android.coursetask5.weather;


public class Weather {

    private String the_temp;
    private String min_temp;
    private String max_temp;
    private String applicable_date;
    private String weather_state_abbr;

    public Weather(String the_temp, String min_temp, String max_temp, String applicable_date,
                   String weather_state_abbr) {
        this.the_temp = the_temp;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.applicable_date = applicable_date;
        this.weather_state_abbr = weather_state_abbr;
    }

    String getWeather() {
        return the_temp;
    }

    String getWeatherMin() {
        return min_temp;
    }

    String getWeatherMax() {
        return max_temp;
    }

    String getWeatherDate() {
        return applicable_date;
    }

    String getWeather_state_abbr() {
        return weather_state_abbr;
    }
}
