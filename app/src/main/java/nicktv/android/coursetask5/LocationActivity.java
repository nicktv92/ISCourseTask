package nicktv.android.coursetask5;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity implements LocationListener {

    TextView mGPSStatus, mNetworkStatus, mGPSLocation, mNetworkLocation;
    private LocationManager locationManager;
    ProgressBar progressGPS, progressNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mGPSStatus = (TextView) findViewById(R.id.gpsTitle);
        mNetworkStatus = (TextView) findViewById(R.id.networkTitle);
        mGPSLocation = (TextView) findViewById(R.id.gpsLocation);
        mNetworkLocation = (TextView) findViewById(R.id.networkLocation);
        progressGPS = (ProgressBar) findViewById(R.id.progressGPS);
        progressNetwork = (ProgressBar) findViewById(R.id.progressNetwork);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
            //регистрирация получения обновлеий данных о местоположении используя данные сети и GPS
        if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        if (locationManager.getAllProviders().contains(LocationManager.PASSIVE_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);

        checkEnabled();
    }

    @Override
    protected void onPause() {
        super.onPause();
            //отмена регистрации получения обновлений данных о местоположении
        locationManager.removeUpdates(this);
    }

        //реализация методов интерфеса LocationListener
    @Override
    public void onLocationChanged(Location location) {
        showLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onProviderEnabled(String provider) {
        checkEnabled();
        showLocation(locationManager.getLastKnownLocation(provider));
    }

    @Override
    public void onProviderDisabled(String provider) {
        checkEnabled();
    }

        //вывод полученных данных в TextView
    private void showLocation(Location location) {
        if (location == null){
            return;
        }

        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            mGPSLocation.setText(formatLocation(location));
            progressGPS.setVisibility(View.INVISIBLE);
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER) || location.getProvider().equals(
                LocationManager.PASSIVE_PROVIDER)) {
            mNetworkLocation.setText(formatLocation(location));
            progressNetwork.setVisibility(View.INVISIBLE);
        }
    }

        //формирование строки String для вывода  в методе showLocation();
    private String formatLocation(Location location) {
        if (location == null) return "Нет данных о местоположении";

        String address = "Широта " + location.getLatitude() + "\n" +
                "Долгота " + location.getLongitude() + "\n" +
                "Высота " + location.getAltitude();

        try {
            //получение адреса по полученным данным о местоположении
            Geocoder geocoder = new Geocoder(this, Locale.ROOT);
            List<Address> addresses;
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                address = addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    //проверка доступа к провайдерам
    private void checkEnabled() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mGPSStatus.setTextColor(getResources().getColor(R.color.colorEnable));
            progressGPS.setVisibility(View.VISIBLE);
        } else {
            mGPSStatus.setTextColor(getResources().getColor(R.color.colorDisable));
            mGPSLocation.setText("");
            progressGPS.setVisibility(View.INVISIBLE);
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            mNetworkStatus.setTextColor(getResources().getColor(R.color.colorEnable));
            progressNetwork.setVisibility(View.VISIBLE);
        } else {
            mNetworkStatus.setTextColor(getResources().getColor(R.color.colorDisable));
            mNetworkLocation.setText("");
            progressNetwork.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}