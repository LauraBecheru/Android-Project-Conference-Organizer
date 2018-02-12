package com.example.laura.project.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.laura.project.Model.PartnersAdapter;
import com.example.laura.project.R;

import java.util.ArrayList;

public class PartnersActivity extends AppCompatActivity {

    ArrayList<String> parteners;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parteners);


        parteners = new ArrayList<>();
        String p1="Microsoft";
        String p2="EY";
        String p3="Vodafone";
        String p4="Dell";
        String p5="Orange";

        parteners.add(p1);
        parteners.add(p2);
        parteners.add(p3);
        parteners.add(p4);
        parteners.add(p5);

        PartnersAdapter gva= new PartnersAdapter(parteners,this);
        GridView gv = findViewById(R.id.gridViewID);
        gv.setAdapter(gva);


    }


}
