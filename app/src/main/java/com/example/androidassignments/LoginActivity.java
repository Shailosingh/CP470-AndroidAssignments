package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity
{
    //Constants
    protected static final String ACTIVITY_NAME = "LoginActivity";
    protected static final String PREFERENCES_FILENAME = "myPrefs";

    //Preference object
    private SharedPreferences preferences;

    //Keys
    protected static final String EMAIL_KEY = "currentEmail";

    //Views items
    private EditText LoginText;
    private EditText PasswordText;
    private Button LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);

        //Setup the shared preferences
        preferences = getSharedPreferences(PREFERENCES_FILENAME, MODE_PRIVATE);

        //Setup the view items
        LoginText = (EditText)findViewById(R.id.LoginText);
        PasswordText = (EditText)findViewById(R.id.PasswordText);
        LoginButton = (Button)findViewById(R.id.LoginButton);

        //Give email default value
        LoginText.setText(preferences.getString(EMAIL_KEY, "email@domain.com"));
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

    public static boolean IsEmailValid(String email)
    {
        //Null check
        if(email == null)
        {
            return false;
        }

        //Regular expression for emails
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        //Turn regular expression into pattern which works with any case letters
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);

        //Checks if email matches pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //Handlers-------------------------------------------------------------------------------------
    public void LoginButton_Handler(android.view.View v)
    {
        //Retrieves the editor
        SharedPreferences.Editor editor = preferences.edit();

        //Ensures email is valid
        String email = LoginText.getText().toString();
        if(!IsEmailValid(email))
        {
            return;
        }

        //Sets the new email
        editor.putString(EMAIL_KEY, email);
        editor.apply();

        //Ensure the password is not empty
        if (PasswordText.getText().toString().isEmpty())
        {
            return;
        }

        //Travel to the main activity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}