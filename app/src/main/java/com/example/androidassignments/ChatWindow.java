package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
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
    private ArrayList<String> TextMessageList;
    private ChatAdapter messageAdapter;

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
        messageAdapter = new ChatAdapter(this);
        ChatView.setAdapter (messageAdapter);
    }

    //Handlers-------------------------------------------------------------------------------------
    public void SendButton_OnClick(android.view.View v)
    {
        //Adds  text message to list
        TextMessageList.add(TextMessageBox.getText().toString());

        //Resets the text message box
        TextMessageBox.setText("");

        //Notify the adapter that there are new messages
        messageAdapter.notifyDataSetChanged();
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