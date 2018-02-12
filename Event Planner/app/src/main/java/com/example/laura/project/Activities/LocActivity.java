package com.example.laura.project.Activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laura.project.MapsActivity;
import com.example.laura.project.Model.Location;
import com.example.laura.project.R;


import org.json.JSONException;


public class LocActivity extends AppCompatActivity {

    //private EditText name;
    private EditText room;
    private EditText capacity;
    //String nameLoc;
    TextView tvLocName;

    public LocActivity() throws JSONException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc);

        //name=(EditText)findViewById(R.id.locBox);
        room=(EditText)findViewById(R.id.roomBox);
        capacity=(EditText)findViewById(R.id.capacityBox);
        tvLocName=(TextView)findViewById(R.id.tvMap);

    }

    public void addButtonLoc(View view)
    {
        Intent intent = new Intent();
        Location loc = new Location(tvLocName.getText().toString(),room.getText().toString(),capacity.getText().toString());
        intent.putExtra("loc", (Parcelable) loc);
        setResult(RESULT_OK, intent);
        finish();
        //Toast.makeText(this,"Location Closed",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        if (reqCode == 2) {
            if (resCode == RESULT_OK) {
                String loc = data.getStringExtra("locMap");
                tvLocName.setText(loc);
            }
        }
    }


    public void addMapLoc(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent,2);
    }

    public void viewGalery(View view)
    {
        Intent intent = new Intent(this, LocationsGalleryActivity.class);
        startActivity(intent);
    }
}
