package com.nasa.marsweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;


import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Mars extends AppCompatActivity {
    WebView webView;
    String date = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Creating Objects
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars);
        getSupportActionBar().hide();
        ImageButton earth = (ImageButton) findViewById(R.id.marsGoToEarth);
        TextView minTemp = (TextView) findViewById(R.id.webView_min_temp);
        TextView maxTemp = (TextView) findViewById(R.id.webView_max_temp);
        Switch ForC = (Switch) findViewById(R.id.ForC);
        Initial();
        SetupSpinner();
        // Set Values
        ForC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ForC.isChecked())
                {
                    setTempF(date);
                }
                else
                {
                    setTempC(date);
                }
            }
        });

        // events
        earth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Initial() {
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // allow the js

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //화면이 계속 켜짐
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://eyes.nasa.gov/apps/mrn/index.html#/mars");
    }

    private void SetupSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        String item[][] = null;
        try {
            item = GetMarsInfo.getDate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] items = new String[item.length];
        for (int i = 0; i < item.length; i++)
        {
            items[i] = item[i][0];
        }
        Switch ForC = (Switch) findViewById(R.id.ForC);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                date = spinner.getSelectedItem().toString();
                Log.e("d", date);
                if (ForC.isChecked())
                {
                    setTempF(date);
                }
                else
                {
                    setTempC(date);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void setTempC(String date) {
        TextView minTemp = (TextView) findViewById(R.id.webView_min_temp);
        TextView maxTemp = (TextView) findViewById(R.id.webView_max_temp);
        String temp[] = null;
        try {
            temp = GetMarsInfo.getTemp(date);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String min = temp[0], max = temp[1];
        minTemp.setText(min + "º");
        maxTemp.setText(max + "º");
    }

    private void setTempF(String date) {
        TextView minTemp = (TextView) findViewById(R.id.webView_min_temp);
        TextView maxTemp = (TextView) findViewById(R.id.webView_max_temp);
        String temp[] = new String[2];
        try {
            temp = GetMarsInfo.getTemp(date);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String min = Integer.toString(Integer.parseInt(temp[0]) *9/5 + 32), max = Integer.toString(Integer.parseInt(temp[1]) *9/5 + 32);
        minTemp.setText(min +"º");
        maxTemp.setText(max + "º");
    }
}
