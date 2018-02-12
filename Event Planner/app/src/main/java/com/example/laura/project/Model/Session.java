package com.example.laura.project.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

/**
 * Created by Laura on 06.01.2018.
 */

public class Session {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public Session(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("ConferenceAppDB", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedin(boolean logggedin){
        editor.putBoolean("loggedInmode",logggedin);
        editor.commit();
    }

    public boolean loggedin(){
        return sharedPreferences.getBoolean("loggedInmode", false);
    }
}
