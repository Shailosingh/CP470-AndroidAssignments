package com.example.androidassignments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "ShailoDB";
    public static String MESSAGE_TABLE_NAME = "MESSAGE_TABLE";
    private static final int VERSION_NUM = 1;
    private static final String KEY_ID = "id";
    public String KEY_MESSAGE = "MESSAGE";

    public String MESSAGE_TABLE_CREATE_COMMAND = String.format("CREATE TABLE %s (%s integer primary key autoincrement, %s text not null);", MESSAGE_TABLE_NAME, KEY_ID, KEY_MESSAGE);
    public String GET_ALL_MESSAGES_COMMAND = String.format("SELECT %s from %s", KEY_MESSAGE, MESSAGE_TABLE_NAME);

    public String ACTIVITY_NAME = "ChatDatabaseHelper";

    public ChatDatabaseHelper(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public void onCreate (SQLiteDatabase db)
    {
        Log.i(ACTIVITY_NAME, "Calling onCreate");
        db.execSQL(MESSAGE_TABLE_CREATE_COMMAND);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i(ACTIVITY_NAME, "Calling onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + MESSAGE_TABLE_NAME);
        onCreate(db);
    }
}
