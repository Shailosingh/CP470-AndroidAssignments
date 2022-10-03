package com.example.androidassignments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity
{
    //Constants
    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    protected static int REQUEST_IMAGE_CAPTURE = 1;

    //View items
    ImageButton CamButton;
    Switch ListSwitch;
    CheckBox ListCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup the view items
        CamButton = (ImageButton)findViewById(R.id.CamButton);

        ListCheckBox = (CheckBox)findViewById(R.id.ListCheckBox);
        ListCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> CheckBoxChanged());

        ListSwitch = (Switch)findViewById(R.id.ListSwitch);
        ListSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> SwitchChanged(isChecked));
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

        //Checks if photo was taken
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");

            //If the photo was taken properly, set it to the button
            if(imageBitmap != null)
            {
                CamButton.setImageBitmap(imageBitmap);
            }
        }
    }

    //Handlers-------------------------------------------------------------------------------------
    public void CamButton_Handler(android.view.View v)
    {
        Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Ensure camera can be opened
        if(picIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(picIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void SwitchChanged(boolean isChecked)
    {
        if(isChecked)
        {
            Toast.makeText(this,R.string.toast_switch_on, Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(this,R.string.toast_switch_off, Toast.LENGTH_LONG).show();
        }
    }

    public void CheckBoxChanged()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message) //Add a dialog message to strings.xml

                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // User clicked OK button
                        Intent resultIntent = new Intent(  );
                        resultIntent.putExtra("Response", "Here is my response");
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // User cancelled the dialog
                    }
                })
                .show();
    }
}