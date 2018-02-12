package com.example.laura.project.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Laura on 28.12.2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static SQLiteHelper sInstance;
    private static final int DATABASE_VERSION = 1;

    public static String DATABASE_NAME = "ConferenceAppDB";

    //TUSER TABLE
    public static final String TABLE_NAME = "User";
    public static final String Table_Column_ID = "id";
    public static final String Table_Column_1_Name = "name";
    public static final String Table_Column_2_Email = "email";
    public static final String Table_Column_3_Password = "password";

    //CONF TABLE
    public static final String TABLE_NAME_CONF = "Conference";
    public static final String Table_Column_ID_Conf = "id";
    public static final String Table_Column_1_User_ID_FK = "user_id";
    public static final String Table_Column_2_Name_Conf = "name";
    public static final String Table_Column_3_Organizer = "organizer";
    public static final String Table_Column_4_Field = "field";
    public static final String Table_Column_5_Duration = "duration";
    public static final String Table_Column_6_Date = "date";
    public static final String Table_Column_7_Fee = "fee";
    public static final String Table_Column_8_Speakers = "speakers";
    public static final String Table_Column_9_Location = "location";


    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String CREATE_TABLE1 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                Table_Column_ID + " INTEGER PRIMARY KEY, " +
                Table_Column_1_Name + " VARCHAR, " +
                Table_Column_2_Email + " VARCHAR, " +
                Table_Column_3_Password + " VARCHAR)";
        database.execSQL(CREATE_TABLE1);

        String CREATE_TABLE2 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CONF + " (" +
                Table_Column_ID_Conf + " INTEGER PRIMARY KEY, " +
                Table_Column_1_User_ID_FK + " VARCHAR, " +
                Table_Column_2_Name_Conf + " VARCHAR, " +
                Table_Column_3_Organizer + " VARCHAR, " +
                Table_Column_4_Field + " VARCHAR, " +
                Table_Column_5_Duration + " VARCHAR, " +
                Table_Column_6_Date + " VARCHAR, " +
                Table_Column_7_Fee + " VARCHAR, " +
                Table_Column_8_Speakers + " VARCHAR, " +
                Table_Column_9_Location + " VARCHAR)";
        database.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + TABLE_NAME_CONF);
        onCreate(db);
    }

    public void delete(String id) {
        getWritableDatabase().delete("Conference", "id=?", new String[]{id});
    }

    public void update(String id, String name, String organizer, String relatedField, int duration,
                       String confDate, float feeAmount, String noSpeakers, String location)
    {
        ContentValues args = new ContentValues();
        args.put(Table_Column_ID_Conf, id);
        args.put(Table_Column_2_Name_Conf, name);
        args.put(Table_Column_3_Organizer,organizer);
        args.put(Table_Column_4_Field, relatedField);
        args.put(Table_Column_5_Duration, duration);
        args.put(Table_Column_6_Date, confDate);
        args.put(Table_Column_7_Fee, feeAmount);
        args.put(Table_Column_8_Speakers, noSpeakers);
        args.put(Table_Column_9_Location,location);

        getWritableDatabase().update(TABLE_NAME_CONF, args, "id=" + id,null);
    }

}
