package com.example.laura.project.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laura.project.Model.Session;
import com.example.laura.project.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RatingBar appRate;

    TextView tvWelcome;
    String emailHolder;
    Button logoutBtn ;
    String name, userID;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RATE
        appRate = (RatingBar) findViewById(R.id.ratingBar);
        appRate.setOnRatingBarChangeListener(ratingBarListener);

        logoutBtn = (Button)findViewById(R.id.btnLogout);

        Intent intent = getIntent();
        emailHolder = intent.getStringExtra(LoginActivity.UserEmail);
        name =intent.getStringExtra("UserName");
        tvWelcome=(TextView)findViewById(R.id.tvWelcome);
        userID=intent.getStringExtra("UserId");

        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                Toast.makeText(MainActivity.this,"Log Out Successfull", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void logout(){
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }

    private RatingBar.OnRatingBarChangeListener ratingBarListener =
            new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    Log.d(TAG, String.valueOf(rating));
                }
            };
    protected  void OnCLickNewConf(View view)
    {
        Intent intent = new Intent(this, ConfActivity.class);
        intent.putExtra("userID",userID);
        startActivity(intent);
    }


    protected  void OnClickConfList(View view)
    {
        Intent intent = new Intent(this, ConfListActivity.class);
        startActivity(intent);
    }
    protected  void OnClickPartners(View view)
    {
        Intent intent = new Intent(this, PartnersActivity.class);
        startActivity(intent);
    }
}
