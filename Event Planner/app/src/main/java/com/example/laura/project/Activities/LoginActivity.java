package com.example.laura.project.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laura.project.Model.SQLiteHelper;
import com.example.laura.project.Model.Session;
import com.example.laura.project.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnRegister ;
    EditText email;
    EditText password ;
    String emailHolder, passwordHolder, userNameHolder, userIDHolder;
    Boolean editTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteOpenHelper sqLiteHelper;
    Cursor cursor;
    String userID, userName;
    String tempPassword = "NOT_FOUND" ;
    public static final String UserEmail = "";
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.btnLog);
        btnRegister = (Button)findViewById(R.id.btnReg);

        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText)findViewById(R.id.etPassw);

        sqLiteHelper = new SQLiteHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextStatus();
                LoginFunction();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        session=new Session(this);
        if(session.loggedin())
        {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }

    }

    public void LoginFunction(){

        if(editTextEmptyHolder) {
            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

            cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " "
                    + SQLiteHelper.Table_Column_2_Email + "=?", new String[]{emailHolder}, null, null,
                    null);

            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst();
                    tempPassword = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Password));
                    userID = cursor.getString(cursor.getColumnIndex((SQLiteHelper.Table_Column_ID)));
                    userName = cursor.getString(cursor.getColumnIndex((SQLiteHelper.Table_Column_1_Name)));
                    cursor.close();
                }
            }
            CheckFinalResult();
        }
        else {
            Toast.makeText(LoginActivity.this,"Please Enter UserName or Password.",Toast.LENGTH_LONG).show();

        }

    }

    public void CheckFinalResult(){

        if(tempPassword.equalsIgnoreCase(passwordHolder)) {
            Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
            session.setLoggedin(true);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(UserEmail, emailHolder);
            intent.putExtra("UserName",userName);
            intent.putExtra("UserId",userID);
            startActivity(intent);
        }

        else {
            Toast.makeText(LoginActivity.this,"UserName or Password is Wrong, Please Try Again.",
                    Toast.LENGTH_LONG).show();
        }
        tempPassword = "NOT_FOUND" ;
    }
    public void CheckEditTextStatus(){

        emailHolder = email.getText().toString();
        passwordHolder = password.getText().toString();

        if( TextUtils.isEmpty(emailHolder) || TextUtils.isEmpty(passwordHolder)){
            editTextEmptyHolder = false ;
        }
        else {
            editTextEmptyHolder = true ;
        }
    }
}
