package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class WeatherForecast extends AppCompatActivity
{
    //Constants
    private final String ACTIVITY_NAME = "WeatherForecastActivity";
    protected static final String URL_HEADER = "https://api.openweathermap.org/data/2.5/weather?q=";
    protected static final String URL_END = ",ca&APPID=f1051780f89b442c0e36ab2def20b047&mode=xml&units=metric";

    //UI Elements
    private ProgressBar WeatherProgressBar;
    private TextView CurrentTempView;
    private TextView MinTempView;
    private TextView MaxTempView;
    private ImageView WeatherView;
    private Spinner CityMenu;

    //Helper Datafields
    private String CurrentCity = "Brampton";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        //Get UI objects
        WeatherProgressBar = findViewById(R.id.ProgressBar);
        CurrentTempView = findViewById(R.id.CurrentTemperatureValue);
        MinTempView = findViewById(R.id.MinTemperatureValue);
        MaxTempView = findViewById(R.id.MaxTemperatureValue);
        WeatherView = findViewById(R.id.WeatherView);
        CityMenu = findViewById(R.id.CityMenu);

        //Load city names
        String[] cities = new String[]{"Toronto", "Brampton", "Waterloo", "Edmonton", "Vancouver", "Hamilton", "Iqaluit"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        CityMenu.setAdapter(adapter);

        //Setup CityMenu handlers
        CityMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                CurrentCity = (String)adapterView.getItemAtPosition(i);
                ForecastQuery forecastObject = new ForecastQuery();
                forecastObject.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                //Do nothing if nothing is selected
            }
        });

        //Load the default weather info
        ForecastQuery forecastObject = new ForecastQuery();
        forecastObject.execute();
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String>
    {
        private String MinimumTemp;
        private String MaximumTemp;
        private String CurrentTemp;
        private Bitmap WeatherPicture;

        protected String doInBackground(String... strings)
        {
            try
            {
                //Connect to API and make request
                URL url = new URL(URL_HEADER + CityMenu.getSelectedItem().toString() + URL_END);
                HttpsURLConnection connectionObject = (HttpsURLConnection) url.openConnection();
                connectionObject.setReadTimeout(10000);
                connectionObject.setConnectTimeout(15000);
                connectionObject.setRequestMethod("GET");
                connectionObject.setDoInput(true);
                connectionObject.connect();

                //Gets the stream from API
                InputStream stream = connectionObject.getInputStream();

                try
                {
                    //Place the stream from API into an XML Parser to get the info out
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(stream, null);

                    while ((parser.getEventType()) != XmlPullParser.END_DOCUMENT)
                    {
                        if (parser.getEventType() == XmlPullParser.START_TAG)
                        {
                            //Get temp info
                            if (parser.getName().equals("temperature"))
                            {
                                CurrentTemp = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                MinimumTemp = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                MaximumTemp = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                            }

                            //Get icon info
                            else if (parser.getName().equals("weather"))
                            {
                                String iconName = parser.getAttributeValue(null, "icon");
                                String fileName = iconName + ".png";
                                String iconUrl = "https://openweathermap.org/img/w/" + fileName;

                                Log.i(ACTIVITY_NAME, "Looking for file: " + fileName);

                                //If the image is already downloaded, display it
                                if (FileExists(fileName))
                                {
                                    FileInputStream fis = null;
                                    try
                                    {
                                        fis = openFileInput(fileName);
                                    }

                                    catch (FileNotFoundException e)
                                    {
                                        e.printStackTrace();
                                    }

                                    Log.i(ACTIVITY_NAME, "Found the file");
                                    WeatherPicture = BitmapFactory.decodeStream(fis);

                                }

                                //If image not downloaded yet retrieve it from online
                                else
                                {
                                    //Download, set and cache image
                                    WeatherPicture = GetImage(new URL(iconUrl));
                                    FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                                    WeatherPicture.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    Log.i(ACTIVITY_NAME, "Downloaded the file");

                                    //Close the file stream
                                    outputStream.flush();
                                    outputStream.close();
                                }

                                publishProgress(100);
                            }
                        }
                        parser.next();
                    }
                }
                finally
                {
                    //Close up the stream from the API
                    stream.close();
                }
            }

            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onProgressUpdate(Integer... value)
        {
            super.onProgressUpdate(value);

            //Update progress bar
            if (WeatherProgressBar != null)
            {
                WeatherProgressBar.setProgress(value[0]);
            }
        }

        @Override
        protected void onPostExecute(String result)
        {
            //Update the UI with all the temperatures
            super.onPostExecute(result);
            WeatherProgressBar.setVisibility(View.INVISIBLE);
            CurrentTempView.setText(CurrentTemp + "C\u00b0");
            MinTempView.setText(MinimumTemp + "C\u00b0");
            MaxTempView.setText(MaximumTemp + "C\u00b0");
            WeatherView.setImageBitmap(WeatherPicture);
        }

        public Bitmap GetImage (URL url)
        {
            HttpsURLConnection connection = null;
            try
            {
                //Open up connection to download image
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();

                //If responseCode is OK, return the bitmap
                if (responseCode == 200)
                {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                }

                else
                {
                    return null;
                }

            }

            catch (Exception e)
            {
                return null;
            }

            finally
            {
                if (connection != null)
                {
                    //Close up the connection when all is done
                    connection.disconnect();
                }
            }
        }

        public boolean FileExists(String fileName)
        {
            //Check if file exists on device
            File file = getBaseContext().getFileStreamPath(fileName);
            return file.exists();
        }
    }
}