package com.example.androidassignments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    //Constants
    protected static final String ACTIVITY_NAME = "MainActivity";
    protected static int REQUEST_CODE = 10;

    //Views items
    private Button MainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup the view items
        MainButton = (Button)findViewById(R.id.MainButton);
    }

    @Override
    protected void onResume()
    {
        Log.i(ACTIVITY_NAME, "In onResume()");
        super.onResume();
    }

    @Override
    protected void onStart()
    {
        Log.i(ACTIVITY_NAME, "In onStart()");
        super.onStart();
    }

    @Override
    protected void onPause()
    {
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE)
        {
            Log.i(ACTIVITY_NAME, "Returned to MainActivity.onActivityResult");

            if(resultCode == Activity.RESULT_OK)
            {
                String messagePassed = data.getStringExtra("Response");
                print(messagePassed);
            }
        }
    }

    public void print(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    //Handlers-------------------------------------------------------------------------------------
    public void MainButton_Handler(android.view.View v)
    {
        Log.i(ACTIVITY_NAME, "User clicked MainButton");
        Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void StartButton_OnClick(android.view.View v)
    {
        Log.i(ACTIVITY_NAME, "User clicked Start Chat");
        Intent intent = new Intent(MainActivity.this, ChatWindow.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void StartTestToolbar_OnClick(android.view.View v)
    {
        Log.i(ACTIVITY_NAME, "User clicked Start Test Toolbar");
        Intent intent = new Intent(MainActivity.this, TestToolbar.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void WeatherButton_OnClick(android.view.View v)
    {
        Intent intent = new Intent(MainActivity.this, WeatherForecast.class);
        startActivityForResult(intent, REQUEST_CODE);
    }
}