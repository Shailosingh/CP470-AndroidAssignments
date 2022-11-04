package com.example.androidassignments;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidassignments.databinding.ActivityTestToolbarBinding;

public class TestToolbar extends AppCompatActivity
{
    //Constants
    protected static final String ACTIVITY_NAME = "TestToolbar";

    //Datafields
    private String selectionItem1Message = "Placeholder";
    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        selectionItem1Message = getString(R.string.selected_item_1);

        binding.fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, R.string.hello_world, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /*
    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    */

    public void print(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public boolean onCreateOptionsMenu (Menu m)
    {
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi)
    {
        switch(mi.getItemId())
        {
            case R.id.action_one:
                Log.d(ACTIVITY_NAME, "Option 1 selected");
                ActionOneClicked();
                break;

            case R.id.action_two:
                Log.d(ACTIVITY_NAME, "Option 2 selected");
                ActionTwoClicked();
                break;

            case R.id.action_three:
                Log.d(ACTIVITY_NAME, "Option 3 selected");
                ActionThreeClicked();
                break;

            case R.id.action_about:
                Log.d(ACTIVITY_NAME, "About selected");
                print(getString(R.string.about_toast));
                break;
        }

        return true;
    }

    private void ActionOneClicked()
    {
        Snackbar.make(binding.fab, selectionItem1Message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void ActionTwoClicked()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.go_back);

        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User clicked OK button
                finish();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void ActionThreeClicked()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.activity_custom_layout);

        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User clicked OK button
                selectionItem1Message = ((EditText)((AlertDialog)dialog).findViewById(R.id.NewMessageBox)).getText().toString();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog (set back to default)
                selectionItem1Message = getString(R.string.selected_item_1);
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}