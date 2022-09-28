package com.example.solarcleaner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.Locale;
import android.os.Handler;

public class MainActivity2 extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    ToggleButton power;
    ImageButton up, down, left, right;
    TextView up_time;
    private int seconds = 0;
    // Is the stopwatch running?
    private boolean running;
    private boolean wasRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        power=(ToggleButton)findViewById(R.id.power);
        up=(ImageButton) findViewById(R.id.up_arrow);
        down=(ImageButton) findViewById(R.id.down_arrow);
        left=(ImageButton) findViewById(R.id.left_arrow);
        right=(ImageButton) findViewById(R.id.right_arrow);
        up_time=(TextView) findViewById(R.id.up_time);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        // pass the Open and Close toggle for the drawer layout listener to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState != null) {
            // Get the previous state of the stopwatch if the activity has been destroyed and recreated.
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(power.getText().toString().equals("ON")) {
                    power.setBackgroundColor(Color.GREEN);
                    running=true;
                }
                else {
                    running=false;
                    power.setBackgroundColor(Color.RED);
                    seconds=0;
                }
            }
        });
    }
    // Save the state of the stopwatch if it's about to be destroyed.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }
    // If the activity is paused, stop the stopwatch.
    @Override
    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }
    // If the activity is resumed, start the stopwatch again if it was running previously.
    @Override
    protected void onResume()
    {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }
    // Sets the NUmber of seconds on the timer. The runTimer() method uses a Handler to increment the seconds and update the text view.
    private void runTimer()
    {
        // Get the text view.
        final TextView timeView = (TextView)findViewById(R.id.up_time);
        // Creates a new Handler
        final Handler handler = new Handler();
        // Call the post() method, passing in a new Runnable. The post() method processes
        // code without a delay, so the code in the Runnable will run almost immediately.
        handler.post(new Runnable() {
            @Override
            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                // Format the seconds into hours, minutes, and seconds.
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, secs);
                // Set the text view text.
                timeView.setText(time);
                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++;
                }
                // Post the code again with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            Log.d("Hello","Happened");
            WebView webView = new WebView(getApplicationContext());
            switch (item.getItemId()) {
                case R.id.load_calculator:
                    webView.loadUrl("file:///android_asset/load_calculator.html");
                    setContentView(webView);
                    Toast.makeText(getApplicationContext(), "Load Calculator", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.solar_panel_calculator:
                    webView.loadUrl("file:///android_asset/solar_calculator.html");
                    setContentView(webView);
                    Toast.makeText(getApplicationContext(), "Solar-Panel Calculator", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.solar_panel_battery:
                    webView.loadUrl("file:///android_asset/solar_battery.html");
                    setContentView(webView);
                    Toast.makeText(getApplicationContext(), "Solar-Panel Battery", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.solar_panel_off_grid:
                    webView.loadUrl("file:///android_asset/solar_off_grid_calculator.html");
                    setContentView(webView);
                    Toast.makeText(getApplicationContext(), "Solar-Panel Off-Grid Calculator", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.logout:
                    Toast.makeText(getApplicationContext(), "Logout success", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MainActivity2.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}