package com.weather.cherkassy;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CharkassyWeather extends AppCompatActivity {

    private TextView mCountry;
    private TextView mWeather;
    private TextView mPressure;
    private TextView mWindSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charkassy_weather);

        mCountry = (TextView) findViewById(R.id.Country);
        mWeather = (TextView) findViewById(R.id.MainWeather);
        mPressure = (TextView) findViewById(R.id.Pressure);
        mWindSpeed = (TextView) findViewById(R.id.WindSpeed);

        GetWeatherWork task = new GetWeatherWork();
        task.execute("234234");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_charkassy_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class GetWeatherWork extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject json = new JSONObject();
            String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=Cherkasy";
            InputStream weatherStream = null;
            try {
                weatherStream = new URL(WEATHER_URL).openStream();
                String weatherString = ConvertStreamToString(weatherStream);
                Log.v("response", weatherString);
                return new JSONObject(weatherString);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (weatherStream != null) {
                    try {
                        weatherStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        ///Convert stream to string
        protected String ConvertStreamToString(java.io.InputStream is) {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject == null) {
                Toast.makeText(CharkassyWeather.this, "Please connect to the Internet and restart the app", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject res = jsonObject.getJSONObject("sys");
                    String country = res.getString("country");

                    JSONObject res1 = jsonObject.getJSONArray("weather").getJSONObject(0);
                    String weather = res1.getString("main");

                    res = jsonObject.getJSONObject("wind");
                    String wind = res.getString("speed");

                    res = jsonObject.getJSONObject("main");
                    String pressure = res.getString("pressure");


                    mCountry.setText(country);
                    mWeather.setText(weather);
                    mPressure.setText(pressure + " hPa");
                    mWindSpeed.setText(wind + " meter/sec");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


