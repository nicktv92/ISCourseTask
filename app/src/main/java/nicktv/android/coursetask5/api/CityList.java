package nicktv.android.coursetask5.api;

public class CityList {
    private String title;
    private String location_type;
    private String latt_long;
    private int woeid;

    public CityList(String title, String location_type, String latt_long, int woeid) {
        this.title = title;
        this.location_type = location_type;
        this.latt_long = latt_long;
        this.woeid = woeid;
    }

    public String getTitle() {
        return title;
    }

    String getLocation_type() {
        return location_type;
    }

    String getLatt_long() {
        return latt_long;
    }

    int getWoeid() {
        return woeid;
    }
}
