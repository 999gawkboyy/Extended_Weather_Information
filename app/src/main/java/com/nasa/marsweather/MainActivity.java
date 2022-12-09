package com.nasa.marsweather;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jetradarmobile.snowfall.SnowfallView;
import com.kwabenaberko.openweathermaplib.constant.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {
    static WebView webv;
    static SnowfallView snow, rain;
    static TextView location;
    static TextView temp;
    static TextView weatherinfo;
    static TextView windspeed;
    static TextView coo;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //화면이 계속 켜짐
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        snow = (SnowfallView) findViewById(R.id.snowFall);
        rain = (SnowfallView) findViewById(R.id.rainFall);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission") Location loc_Current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double lat = loc_Current.getLatitude(); //위도
        double lon = loc_Current.getLongitude(); //경도
        GetWeatherMap.setWeatherByInfo(lat,lon);
        ImageButton mars = (ImageButton) findViewById(R.id.gotoMars);
        webv = (WebView) findViewById(R.id.webView);
        location = (TextView) findViewById(R.id.loc);
        temp = (TextView) findViewById(R.id.temp);
        weatherinfo = (TextView) findViewById(R.id.weatherInfo);
        windspeed = (TextView) findViewById(R.id.windSpeed);
        coo = (TextView) findViewById(R.id.cooo);
        ImageButton gotoearth = (ImageButton) findViewById(R.id.mainGoToEarth);
        ImageButton gotosolar = (ImageButton) findViewById(R.id.gotoSolar);
        getSupportActionBar().hide();
        mars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mars.class);
                startActivity(intent);
            }
        });

        gotoearth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Earth.class);
                startActivity(intent);
            }
        });

        gotosolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SolarSystem.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}