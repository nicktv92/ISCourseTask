package nicktv.android.coursetask5.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class PowerConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        String charge = "Устройство заряжается";
        String disCharge = "Устройство не заряжается";

        if (isCharging) {
            Toast.makeText(context, charge, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, disCharge, Toast.LENGTH_LONG).show();
        }
    }
}
