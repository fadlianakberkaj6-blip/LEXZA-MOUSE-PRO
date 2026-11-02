
package com.lexza.mousepro;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.view.InputDevice;
import android.widget.*;
import android.graphics.Color;
import android.graphics.Typeface;

public class MainActivity extends Activity {

    LinearLayout layout;
    TextView status, devices, keyLog, mouseLog, sensitivityText;
    boolean monitoring = false;
    int sensitivity = 50;

    int bg = Color.rgb(12, 16, 27);
    int blue = Color.rgb(20, 105, 190);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(25, 30, 25, 30);
        layout.setBackgroundColor(bg);
        scroll.addView(layout);

        TextView title = new TextView(this);
        title.setText("LEXZA MOUSE PRO");
        title.setTextColor(Color.CYAN);
        title.setTextSize(27);
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        layout.addView(title);

        TextView version = new TextView(this);
        version.setText("V2.0 | OTG EDITION");
        version.setTextColor(Color.WHITE);
        version.setGravity(Gravity.CENTER);
        version.setPadding(0, 8, 0, 25);
        layout.addView(version);

        status = addText("STATUS: MONITORING OFF");
        devices = addText("USB INPUT DEVICES: NOT CHECKED");
        keyLog = addText("KEYBOARD: Waiting for input");
        mouseLog = addText("MOUSE: Waiting for input");

        addButton("CHECK OTG DEVICES", v -> refreshDevices());

        addButton("START MONITORING", v -> {
            monitoring = true;
            status.setText("STATUS: MONITORING ON");
            status.setTextColor(Color.GREEN);
            refreshDevices();
            requestFocus();
        });

        addButton("STOP MONITORING", v -> {
            monitoring = false;
            status.setText("STATUS: MONITORING OFF");
            status.setTextColor(Color.RED);
        });

        addText("MOUSE SENSITIVITY");

        sensitivityText = addText("Sensitivity: 50");

        SeekBar seek = new SeekBar(this);
        seek.setMax(100);
        seek.setProgress(50);
        layout.addView(seek);

        seek.setOnSeekBarChangeListener(
            new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(
                    SeekBar bar, int value, boolean user
                ) {
                    sensitivity = value;
                    sensitivityText.setText(
                        "Sensitivity: " + value
                    );
                }

                public void onStartTrackingTouch(SeekBar bar) {}
                public void onStopTrackingTouch(SeekBar bar) {}
            }
        );

        addButton("RESET TEST LOG", v -> {
            keyLog.setText("KEYBOARD: Waiting for input");
            mouseLog.setText("MOUSE: Waiting for input");
        });

        addText("INPUT TESTER");
        addText("Tekan tombol keyboard atau gerakkan mouse saat aplikasi aktif.");

        setContentView(scroll);
        refreshDevices();
    }

    TextView addText(String text) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextColor(Color.WHITE);
        t.setTextSize(15);
        t.setPadding(5, 16, 5, 16);
        layout.addView(t);
        return t;
    }

    void addButton(String name,
                   android.view.View.OnClickListener action) {
        Button b = new Button(this);
        b.setText(name);
        b.setTextColor(Color.WHITE);
        b.setBackgroundColor(blue);
        LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(-1, -2);
        p.setMargins(0, 8, 0, 8);
        layout.addView(b, p);
        b.setOnClickListener(action);
    }

    void refreshDevices() {
        StringBuilder result = new StringBuilder();
        int[] ids = InputDevice.getDeviceIds();

        for (int id : ids) {
            InputDevice device = InputDevice.getDevice(id);

            if (device != null) {
                int sources = device.getSources();

                boolean keyboard =
                    (sources & InputDevice.SOURCE_KEYBOARD)
                    == InputDevice.SOURCE_KEYBOARD;

                boolean mouse =
                    (sources & InputDevice.SOURCE_MOUSE)
                    == InputDevice.SOURCE_MOUSE;

                if (keyboard || mouse) {
                    result.append(device.getName())
                        .append(keyboard ? " [KEYBOARD]" : "")
                        .append(mouse ? " [MOUSE]" : "")
                        .append("\n");
                }
            }
        }

        if (result.length() == 0) {
            result.append("No keyboard or mouse detected.");
        }

        devices.setText("USB INPUT DEVICES:\n" + result);
    }

    @Override
    public boolean dispatchKeyEvent(android.view.KeyEvent event) {
        if (monitoring) {
            keyLog.setText(
                "KEYBOARD: " +
                KeyEvent.keyCodeToString(event.getKeyCode()) +
                (event.getAction() == KeyEvent.ACTION_DOWN
                    ? " PRESSED" : " RELEASED")
            );
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (monitoring &&
            (event.getSource() & InputDevice.SOURCE_MOUSE)
                == InputDevice.SOURCE_MOUSE) {

            mouseLog.setText(
                "MOUSE X: " + event.getX() +
                "\nMOUSE Y: " + event.getY() +
                "\nSensitivity setting: " + sensitivity
            );
        }

        return super.onGenericMotionEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (monitoring &&
            event.getToolType(0) == MotionEvent.TOOL_TYPE_MOUSE) {

            mouseLog.setText(
                "MOUSE EVENT: " + event.getAction()
            );
        }

        return super.onTouchEvent(event);
    }
}

                        
