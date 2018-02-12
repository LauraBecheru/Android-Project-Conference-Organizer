package com.example.laura.project.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laura.project.Model.Conference;
import com.example.laura.project.Model.SQLiteHelper;
import com.example.laura.project.R;

import java.util.ArrayList;

public class ConfListActivity extends AppCompatActivity {

    public static final String TAG = "ListActivity";
    ArrayList<Conference> confList;
    ListView lv;
    Button delete, modify;
    SQLiteDatabase db;
    SQLiteHelper helper = new SQLiteHelper(this);
    Cursor cursor;
    String idConf;

   /* private AdapterView.OnItemClickListener confLvListener = new AdapterView.OnItemClickListener() {
        @Override
      /*  public void onItemClick(AdapterView<?> adapterViewParent, View view, int i, long id) {
            /*TextView tv = (TextView) view;
            String value = tv.getText().toString();

            Intent intent = new Intent();
            intent.putExtra("value", value);
            setResult(Activity.RESULT_OK, intent);
            finish();

            String name = adapterViewParent.getItemIdAtPosition(i);
            Cursor c = db.rawQuery("SELECT * FROM Conference", null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String confName = cursor.getString(cursor.getColumnIndex((SQLiteHelper.Table_Column_2_Name_Conf)));
                    if (name == confName) {
                        idConf = cursor.getString(cursor.getColumnIndex((SQLiteHelper.Table_Column_ID_Conf)));
                        db.delete(idConf);
                    }
                }
            }
        }
    };
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_list);

        lv = (ListView) findViewById(R.id.cListView);
        confList = new ArrayList<Conference>();
        db = helper.getReadableDatabase();


        cursor = db.rawQuery("SELECT * FROM Conference", null);

        if (cursor != null) {

            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_Name_Conf));
                String organizer = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Organizer));
                String field = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_4_Field));
                int duration = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.Table_Column_5_Duration));
                String date = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_6_Date));
                float fee = cursor.getFloat(cursor.getColumnIndex(SQLiteHelper.Table_Column_7_Fee));
                String speakers = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_8_Speakers));
                String location = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_9_Location));


                Conference conf = new Conference(name, organizer, field, duration, date, fee, speakers, location);
                confList.add(conf);cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_Name_Conf));
            }
        } else
            Toast.makeText(this, "No data!", Toast.LENGTH_SHORT).show();


        final ArrayAdapter<Conference> confListAdapter = new ArrayAdapter<Conference>(this, android.R.layout.simple_list_item_1, confList);
        lv.setAdapter(confListAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object confObj = parent.getItemAtPosition(position);
                Conference conf = (Conference) confObj;

                String confName = conf.getName();
                Cursor c = db.rawQuery("SELECT * FROM Conference", null);
                if (c != null) {

                    while (c.moveToNext()) {
                        String cName = c.getString(c.getColumnIndex(SQLiteHelper.Table_Column_2_Name_Conf));
                        if (cName.equals(confName)){
                            idConf = c.getString(c.getColumnIndex(SQLiteHelper.Table_Column_ID_Conf));
                            helper.delete(idConf);
                        }

                    }

                    confListAdapter.remove(conf);
                    confListAdapter.notifyDataSetChanged();
                }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Object confObj = parent.getItemAtPosition(position);
                Conference conf = (Conference) confObj;
                String confName = conf.getName();

                Cursor c = db.rawQuery("SELECT * FROM Conference", null);
                while (c.moveToNext()) {
                    String cName = c.getString(c.getColumnIndex(SQLiteHelper.Table_Column_2_Name_Conf));
                    if (cName.equals(confName)) {
                        idConf = c.getString(c.getColumnIndex(SQLiteHelper.Table_Column_ID_Conf));
                        Intent intent = new Intent(getApplicationContext(), ConfActivity.class);
                        intent.putExtra("ListViewClickedItemValue", idConf);
                        //setResult(Activity.RESULT_OK, intent);
                        startActivity(intent);
                        finish();
                    }

                }
                return false;
            }
        });
    }
}

