package com.android.android_lab_7;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DroidActivity2 extends AppCompatActivity {

    Button mCameraButton;
    Button mBatteryButton;
    TextView mExpressionTextView;
    Button mContactsButton;
    Button mWiFiIn;
    Button mWiFiOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        mExpressionTextView = (TextView) findViewById(R.id.expression);
        mCameraButton = (Button) findViewById(R.id.camera);
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CAMERA_BUTTON);
                intent.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_CAMERA));
                sendOrderedBroadcast(intent, null);
            }
        });

        mBatteryButton = (Button) findViewById(R.id.battery);
        mBatteryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = (int)getBatteryLevel();
              //  String a = Float.toString(getBatteryLevel());
                mExpressionTextView.setText("Заряд батареи " + a + "%");
            }
        });

        mWiFiIn = (Button) findViewById(R.id.WiFi);
        mWiFiIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wifiEnableTrue();
            }
        });

        mWiFiOff = (Button) findViewById(R.id.WiFiOff);
        mWiFiOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wifiEnableFalse();
            }
        });
        mContactsButton = (Button) findViewById(R.id.contacts);
        mContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DroidActivity2.this, ContactActivity.class);
                startActivity(intent);
            }
        });
    }


    public float getBatteryLevel() {
        Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        if (level == -1 || scale == -1) {
            return 50f;
        }

        return (float)((float)level / (float)scale) * 100f;
    }

    public void wifiEnableTrue() {
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        try {
            wifiManager.setWifiEnabled(true);
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Сеть не найдена", Toast.LENGTH_LONG).show();
        }
    }

    public void wifiEnableFalse() {
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        try {
            wifiManager.setWifiEnabled(false);
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Ошибка сети", Toast.LENGTH_LONG).show();
        }
    }
}
