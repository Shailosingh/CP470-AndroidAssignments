package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity
{
    //View Items
    ListView ChatView;
    EditText TextMessageBox;
    Button SendButton;

    //Datafields
    private String ACTIVITY_NAME = "ChatWindow";
    private ArrayList<String> TextMessageList;
    private ChatAdapter MessageAdapter;
    private static ChatDatabaseHelper DatabaseHelper;
    private static SQLiteDatabase ReadableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        //Setup view items
        ChatView = (ListView)findViewById(R.id.ChatView);
        TextMessageBox = (EditText)findViewById(R.id.TextMessageBox);
        SendButton = (Button)findViewById(R.id.SendButton);

        //Initialize datafields
        TextMessageList = new ArrayList<>();

        //In this case, “this” is the ChatWindow, which is-A Context object
        MessageAdapter = new ChatAdapter(this);
        ChatView.setAdapter (MessageAdapter);

        //Open up database
        DatabaseHelper = new ChatDatabaseHelper(this);
        ReadableDatabase = DatabaseHelper.getReadableDatabase();

        //Retrieve all messages
        Cursor cursor = ReadableDatabase.rawQuery(DatabaseHelper.GET_ALL_MESSAGES_COMMAND, null);
        cursor.moveToFirst();
        int columnIndex;
        while(!cursor.isAfterLast())
        {
            columnIndex = cursor.getColumnIndex(DatabaseHelper.KEY_MESSAGE);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(columnIndex));
            TextMessageList.add(cursor.getString(columnIndex));
            cursor.moveToNext();
        }
        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount() );

        for (int i = 0; i <cursor.getColumnCount();i++)
        {
            Log.i(ACTIVITY_NAME, "Column Name: "+ cursor.getColumnName(i));
        }
    }

    public void onDestroy()
    {
        super.onDestroy();
        ReadableDatabase.close();
    }

    //Handlers-------------------------------------------------------------------------------------
    public void SendButton_OnClick(android.view.View v)
    {
        //Adds  text message to list
        TextMessageList.add(TextMessageBox.getText().toString());

        //Put text in database
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_MESSAGE, TextMessageBox.getText().toString());
        ReadableDatabase.insert(ChatDatabaseHelper.MESSAGE_TABLE_NAME, null, values);

        //Resets the text message box
        TextMessageBox.setText("");

        //Notify the adapter that there are new messages
        MessageAdapter.notifyDataSetChanged();
    }

    //Inner Class----------------------------------------------------------------------------------
    private class ChatAdapter extends ArrayAdapter<String>
    {
        public ChatAdapter(Context ctx)
        {
            super(ctx, 0);
        }

        public int getCount()
        {
            return TextMessageList.size();
        }

        public String getItem(int position)
        {
            return TextMessageList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            //Load up the incoming or outcoming text
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0)
            {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            }

            else
            {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            //Load position's text message into the text message view
            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position));

            //Return the view for the text message
            return result;
        }
    }
}