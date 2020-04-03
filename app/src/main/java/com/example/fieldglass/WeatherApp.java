package com.example.fieldglass;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherApp extends AppCompatActivity {

    TextView townName;
    Button searchButton, homeButton;
    TextView result;

    class Forecast extends AsyncTask<String,Void,String>{
        //String means URL is in the string, second String is the return type will be string

        @Override
        protected String doInBackground(String... address) {
            //String... means multiple addresses can be sent. It acts as an array.
           try {
               URL url = new URL(address[0]);
               HttpURLConnection connection = (HttpURLConnection) url.openConnection();
               //create a connection with address
               connection.connect();

               //retrieve data from url
               InputStream is = connection.getInputStream();
               InputStreamReader isr = new InputStreamReader(is);

               //Retrieve data and return it as string
               int data = isr.read();
               String content = "";
               char ch;
               while (data != -1){
                   ch = (char)data;
                   content = content + ch;
                   data = isr.read();
               }
               return content;

           } catch (MalformedURLException e) {
               e.printStackTrace();
           } catch ( IOException e) {
               e.printStackTrace();
           }

            return null;
        }
    }

    public void search(){

        String SearchCriteria;
        if (global.weatherSearch.equals("Home")){
            //Search for home address
            SearchCriteria = global.city;
        }
        else if (global.weatherSearch.equals("Search")){
            //Search for enetered LOCATION
            SearchCriteria = townName.getText().toString().trim();
        }
        else{
            SearchCriteria = "";
        }

        townName = findViewById(R.id.editTextTown);
        searchButton = findViewById(R.id.homeAddressButton);
        result = findViewById(R.id.result);

        //Weather app taken from Youtube tutorial by "Learn Through Code"
        String content;
        Forecast forecast = new Forecast();
        try {
            content = forecast.execute("https://openweathermap.org/data/2.5/weather?q="
                    + SearchCriteria +"&appid=b6907d289e10d714a6e88b30761fae22").get();
            //Check data is returning or not
            Log.i( "content", content);

            //JSON
            JSONObject jsonObject = new JSONObject(content);
            String forecastData = jsonObject.getString("weather");
            String mainTemperature = jsonObject.getString("main"); //main is a separate variable
            Log.i("ForecastData", forecastData);

            //Forecast data is in Array
            JSONArray array = new JSONArray(forecastData);

            String main = "";
            String description ="";
            String temperature = "";

            for (int i=0; i<array.length(); i++) {
                JSONObject forecastPart = array.getJSONObject(i);
                main = forecastPart.getString("main");
                description = forecastPart.getString("description");
            }

            JSONObject mainPart = new JSONObject(mainTemperature);
            temperature = mainPart.getString("temp");
            Log.i("Temperature",temperature);

            String resultText =
                    "Main: "+ main +
                    "\nDescription: " + description +
                    "\nTemperature: " + temperature + "*C";

            //Display weather
            result.setText(resultText);

            closeKeyboard();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //Hide keyboard after Search
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        searchButton = findViewById(R.id.searchAddressButton);
        homeButton = findViewById(R.id.homeAddressButton);

        //TEST
        Toast.makeText(WeatherApp.this, "Users City" + global.city, Toast.LENGTH_SHORT).show();



        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.weatherSearch = "Home";
                Toast.makeText(WeatherApp.this, global.weatherSearch, Toast.LENGTH_SHORT).show();
                search();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.weatherSearch = "Search";
                Toast.makeText(WeatherApp.this, global.weatherSearch, Toast.LENGTH_SHORT).show();
                search();
            }
        });

        //Floating action button to close new service
        FloatingActionButton fab = findViewById(R.id.fabE_btn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext()
                        , About.class));
                Toast.makeText(WeatherApp.this, "Return to About", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
