package com.lexza.mousepro;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(25, 35, 25, 25);
        layout.setBackgroundColor(Color.rgb(12, 15, 25));

        TextView title = new TextView(this);
        title.setText("LEXZA MOUSE PRO");
        title.setTextSize(27);
        title.setTextColor(Color.CYAN);
        title.setGravity(Gravity.CENTER);
        layout.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("PRO KEYMAPPER CONTROL PANEL");
        subtitle.setTextColor(Color.WHITE);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 15, 0, 30);
        layout.addView(subtitle);

        addButton("START MAPPING");
        addButton("KEY MAPPING SETTINGS");
        addButton("MOUSE SETTINGS");
        addButton("SENSITIVITY SETTINGS");
        addButton("ABOUT LEXZA");

        TextView status = new TextView(this);
        status.setText("\nSTATUS : READY\nVERSION : 1.0");
        status.setTextColor(Color.GREEN);
        status.setTextSize(15);
        status.setGravity(Gravity.CENTER);
        layout.addView(status);

        setContentView(layout);
    }

    void addButton(String name) {
        Button button = new Button(this);
        button.setText(name);
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(Color.rgb(25, 90, 150));

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(
                -1, -2
            );

        params.setMargins(0, 12, 0, 12);
        layout.addView(button, params);
    }
          }
