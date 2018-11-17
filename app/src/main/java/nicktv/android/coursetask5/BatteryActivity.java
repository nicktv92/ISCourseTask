package nicktv.android.coursetask5;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import nicktv.android.coursetask5.battery.PowerConnectionReceiver;

public class BatteryActivity extends AppCompatActivity {

    TextView batteryLevel;
    PowerConnectionReceiver chargeStatus;
    ProgressBar progressBattery;
    ImageView imageBattery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        batteryLevel = (TextView) findViewById(R.id.batteryLevel);
        progressBattery = (ProgressBar) findViewById(R.id.progressBattery);
        imageBattery = (ImageView) findViewById(R.id.imageBattery);

    }

    @Override
    protected void onResume() {
        super.onResume();
        chargeStatus = new PowerConnectionReceiver();
        setBatteryLevel(getBatteryLevel());
        getStatus(chargeStatus);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //отмена регистрации ресивера
        unregisterReceiver(chargeStatus);
    }

    public void setBatteryLevel(float level) {
        batteryLevel.setText(String.valueOf(level));
        progressBattery.setProgress((int) level);
        if (level < 10) {
            batteryLevel.setTextColor(getResources().getColor(R.color.colorMin));
            imageBattery.setImageDrawable((getResources().getDrawable(R.drawable.ic_battery_20)));
        } else if (level >= 10 && level < 70) {
            batteryLevel.setTextColor(getResources().getColor(R.color.colorMedium));
            imageBattery.setImageDrawable((getResources().getDrawable(R.drawable.ic_battery_50)));
        } else {
            batteryLevel.setTextColor(getResources().getColor(R.color.colorMax));
            imageBattery.setImageDrawable((getResources().getDrawable(R.drawable.ic_battery_full)));
        }

    }

    public float getBatteryLevel() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);  //текущий заряд
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);  //максимальный заряд

        return (level / (float) scale) * 100.0f;
    }

    //регистрация ресивера для получения информации о подключении зарядного устройства
    public void getStatus(PowerConnectionReceiver receiver) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, ifilter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}