package com.example.laura.project.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laura.project.Model.SQLiteHelper;
import com.example.laura.project.R;

public class RegisterActivity extends AppCompatActivity {


    EditText email;
    EditText password;
    EditText name ;
    Button btnRegister;
    String nameHolder, emailHolder, passwordHolder;
    Boolean editTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";
    CheckBox terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btnRegister = (Button)findViewById(R.id.btnReg);

        name = (EditText)findViewById(R.id.etNameReg);
        email = (EditText)findViewById(R.id.etEmailReg);
        password = (EditText)findViewById(R.id.etPasswordReg);

        terms = ( CheckBox)findViewById(R.id.cbTerms);

        sqLiteHelper = new SQLiteHelper(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(terms.isChecked()) {
                   SQLiteDataBaseBuild();
                   SQLiteTableBuild();
                   CheckEditTextStatus();
                   CheckingEmailAlreadyExistsOrNot();
                   EmptyEditTextAfterDataInsert();
                   finish();
               }
               else
               {
                   Toast.makeText(RegisterActivity.this,"You must agree with the terms and conditions",Toast.LENGTH_LONG).show();
               }
            }
        });
    }

    public void SQLiteDataBaseBuild(){
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " +
                SQLiteHelper.TABLE_NAME + "(" +
                SQLiteHelper.Table_Column_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                SQLiteHelper.Table_Column_1_Name + " VARCHAR, " +
                SQLiteHelper.Table_Column_2_Email +
                " VARCHAR, " +
                SQLiteHelper.Table_Column_3_Password + " VARCHAR);");
    }

    public void InsertDataIntoSQLiteDatabase(){
        if(editTextEmptyHolder == true)
        {
            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (name,email,password) VALUES('"+nameHolder+"', '"+emailHolder+"', '"+passwordHolder+"');";
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();
            Toast.makeText(RegisterActivity.this,"User Registered Successfully", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(RegisterActivity.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();
        }
    }

    public void CheckingEmailAlreadyExistsOrNot(){
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_2_Email + "=?", new String[]{emailHolder},
                null, null, null);

        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                F_Result = "Email Found";
                cursor.close();
            }
        }
        CheckFinalResult();
    }

    public void CheckFinalResult(){
        if(F_Result.equalsIgnoreCase("Email Found"))
        {
            Toast.makeText(RegisterActivity.this,"Email Already Exists",Toast.LENGTH_LONG).show();
        }
        else {
            InsertDataIntoSQLiteDatabase();
        }
        F_Result = "Not_Found" ;
    }


    public void EmptyEditTextAfterDataInsert(){

        name.getText().clear();

        email.getText().clear();

        password.getText().clear();

    }

    public void CheckEditTextStatus(){
        nameHolder = name.getText().toString() ;
        emailHolder = email.getText().toString();
        passwordHolder = password.getText().toString();

        if(TextUtils.isEmpty(nameHolder) || TextUtils.isEmpty(emailHolder) || TextUtils.isEmpty(passwordHolder)){
            editTextEmptyHolder = false ;
        }
        else {
            editTextEmptyHolder = true ;
        }
    }

    public void TermsCond(View view) {
        Intent intent = new Intent(this, TermCondActivity.class);
        startActivityForResult(intent, 1);
    }

}

