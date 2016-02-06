package com.example.martin.ieeeworkshopapp;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView timeView;
    private Button startButton;
    private Button pauseButton;
    private Button restartButton;

    long startTime = 0;
    long timeElapsed = 0;
    long pauseTime = 0;
    long currentTime = 0;

    private Handler updateHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleTextView = (TextView)findViewById(R.id.titleTextView);
        timeView = (TextView)findViewById(R.id.timeView);
        startButton = (Button)findViewById(R.id.startButton);
        pauseButton = (Button)findViewById(R.id.pauseButton);
        restartButton = (Button)findViewById(R.id.restartButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Timer started", Toast.LENGTH_SHORT).show();
                startTime = SystemClock.uptimeMillis();
                updateHandler.post(updateTimer);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Timer paused", Toast.LENGTH_SHORT).show();
                pauseTime += timeElapsed;
                timeElapsed = 0;
                updateHandler.removeCallbacks(updateTimer);
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Timer restarted", Toast.LENGTH_SHORT).show();
                timeView.setText("00:00:000");
                pauseTime = 0;
                updateHandler.removeCallbacks(updateTimer);
            }
        });
    }

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            timeElapsed = SystemClock.uptimeMillis() - startTime;
            currentTime = pauseTime + timeElapsed;
            int secs = (int) (currentTime / 1000);
            int mins = secs / 60;

            String timeString =
                    String.format("%02d", mins) + ":" +
                    String.format("%02d", secs) + ":" +
                    String.format("%03d", currentTime % 1000);

            timeView.setText(timeString);
            updateHandler.post(this);
        }
    };
}
