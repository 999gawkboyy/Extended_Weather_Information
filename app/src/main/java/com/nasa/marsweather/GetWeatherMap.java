package com.nasa.marsweather;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.model.common.Main;
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather;

import java.time.LocalTime;

public class GetWeatherMap {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void setWeatherByInfo(double lat, double lon) {
        OpenWeatherMapHelper helper = new OpenWeatherMapHelper("36cce6e940f7ef1e1b3b6d436805522d");
        LocalTime currentTime = LocalTime.now();
        String s[] = String.valueOf(currentTime).split(":");
        String time = s[0];
        helper.getCurrentWeatherByGeoCoordinates(lat, lon, new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                setIcon(time);
                setBack(currentWeather.getWeather().get(0).getDescription());
                MainActivity.location.setText(currentWeather.getName() + ", " + currentWeather.getSys().getCountry());
                String t = Integer.toString((int) (Double.parseDouble(String.valueOf(currentWeather.getMain().getTempMax())) - 273));
                MainActivity.temp.setText(t + "º");
                MainActivity.weatherinfo.setText(currentWeather.getWeather().get(0).getDescription());
                MainActivity.windspeed.setText(String.valueOf(currentWeather.getWind().getSpeed()) + "m/s");
                MainActivity.coo.setText(currentWeather.getCoord().getLat() + ", "+currentWeather.getCoord().getLon());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.v("D", throwable.getMessage());
            }
        });
    }

    private static void setIcon(String time) {
        WebView web = MainActivity.webv;

        if ((Integer.parseInt(time) < 7) || (Integer.parseInt(time) > 20))
        {
            WebSettings webSettings = web.getSettings();
            webSettings.setJavaScriptEnabled(true); // allow the js

            web.setWebViewClient(new WebViewClient());
            web.loadUrl("https://solarsystem.nasa.gov/gltf_embed/2366/");
        }
        else
        {
            WebSettings webSettings = web.getSettings();
            webSettings.setJavaScriptEnabled(true); // allow the js

            web.setWebViewClient(new WebViewClient());
            web.loadUrl("https://solarsystem.nasa.gov/gltf_embed/2352/");
        }
    }

    private static void setBack(String weather) {
        if (!weather.equals("snow"))
        {
            MainActivity.snow.setVisibility(View.GONE);
        }
        if (!weather.equals("shower rain") && !weather.equals("rain") && !weather.equals("light rain"))
        {
            MainActivity.rain.setVisibility(View.GONE);
        }
    }
}
