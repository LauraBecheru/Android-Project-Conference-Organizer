package com.example.laura.project.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laura.project.Model.Conference;
import com.example.laura.project.Model.Location;
import com.example.laura.project.Model.SQLiteHelper;
import com.example.laura.project.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ConfActivity extends AppCompatActivity {

    private final static String TAG = "Conf Activity";
    private Spinner field;
    private EditText name;
    private SeekBar duration;
    private AutoCompleteTextView organizer;
    private Button date;
    private Button saveConf;
    private Switch fee;
    private EditText amountFee;
    private TextView location;

    //DB
    String nameHolder, organizerHolder,fieldHolder, dateHolder,speakersHolder, locationHolder, durationHolder,feeHolder;

    Boolean editTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    //String F_Result = "Not_Found";
    String userID, confID;


    private  ArrayList<Conference> confList;
    Conference conf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);
        //Log.d(TAG, "onCreate");

        //AmountFee
        amountFee = (EditText) findViewById(R.id.etAmountFee);
        //EDITBOX
        name = (EditText) findViewById(R.id.boxName);

        //SPINNER
        field = (Spinner) findViewById(R.id.spField);
        ArrayAdapter<CharSequence> genreOptions = ArrayAdapter.createFromResource(this, R.array.field_array,
                android.R.layout.simple_spinner_item);
        genreOptions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        field.setAdapter(genreOptions);

        //DATE PICKER
        date = (Button) findViewById(R.id.dateBtn);
        date.setOnClickListener(dateBtnClickList);

        //AUTOCOMPLETE
        organizer = (AutoCompleteTextView) findViewById(R.id.orgAutoText);
        String[] organiz = getResources().getStringArray(R.array.array_organiz);
        ArrayAdapter<String> organizAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, organiz);
        organizer.setAdapter(organizAdapter);

        //SEEK BAR
        duration = (SeekBar) findViewById(R.id.seekBar);
        duration.setOnSeekBarChangeListener(sbListener);

        //SWITCH
        fee = (Switch) findViewById(R.id.swtFee);
        fee.setOnCheckedChangeListener(swtList);

        //LIST
        confList = new ArrayList<Conference>();
        location = (TextView) findViewById(R.id.tvLoc);

        sqLiteHelper = new SQLiteHelper(this);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

        Intent in =getIntent();
        confID = in.getStringExtra("ListViewClickedItemValue");

    }

    public void updateConf()
    {
        String nameC = name.getText().toString();
        String organizerC =organizer.getText().toString();
        String fieldC = field.getSelectedItem().toString();
        String loc = location.getText().toString();
        int durationC = Integer.valueOf(duration.getProgress());
        String dateC = date.getText().toString();
        float feeC=Float.valueOf(amountFee.getText().toString());
        RadioGroup rg = (RadioGroup)findViewById(R.id.rGoup);
        String speakers = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();

        while (cursor.moveToNext()) {
            if (confID.equals(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_ID_Conf)))) {
                sqLiteHelper.update(confID, nameC, organizerC, fieldC, durationC, dateC, feeC, speakers, loc);
            }
        }
    }

    public void AddLocation(View view) {
        Intent intent = new Intent(this, LocActivity.class);
        startActivityForResult(intent, 1);
    }

    //SEEK BAR
    private SeekBar.OnSeekBarChangeListener sbListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int duration = progress * 2;
            Log.d(TAG, String.valueOf(duration));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    //DATE PICKER
    private void displayDatePicker() {
        Calendar cd = Calendar.getInstance(Locale.getDefault());
        int year = cd.get(Calendar.YEAR);
        int month = cd.get(Calendar.MONTH);
        final int day = cd.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker dp, int year, int month, int day) {
                date.setText(day + "/" + month + "/" + year);
            }
        }, year, month, day);
        dpd.show();
    }

    private View.OnClickListener dateBtnClickList = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.dateBtn:
                    displayDatePicker();
            }
        }
    };

    //SWITCH
    private CompoundButton.OnCheckedChangeListener swtList = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
            Log.d(TAG, "Fee" + checked);
            if (fee.isChecked()) {
                amountFee.setText("0");
            }
        }
    };

    //RADIO BTN
    public void onRadioButtonClick(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.firstRb:
                Log.d(TAG, "0-3 Speakers");
                //speakers="0-3";
                //speakers=findViewById(R.id.firstRb);
                break;
            case R.id.secondRb:
                Log.d(TAG, "3-5 Speakers");
                //speakers=findViewById(R.id.firstRb);
                //speakers= "3-5";
                break;
            case R.id.thirdRb:
                Log.d(TAG, "5-7 Speakers");
                //speakers = "5-7";
                //speakers=findViewById(R.id.firstRb);
                break;
            case R.id.fourthRb:
                Log.d(TAG, "7-10 Speakers");
                //speakers=findViewById(R.id.firstRb);
                //speakers = "7-10";
                break;
        }

    }

    //LIST
    public void addBtn(View view) {
        //float amount = Float.valueOf(amountFee.getText().toString();
        String nameC = name.getText().toString();
        String organizerC =organizer.getText().toString();
        String fieldC = field.getSelectedItem().toString();
        String loc = location.getText().toString();
        int durationC = Integer.valueOf(duration.getProgress());
        String dateC = date.getText().toString();
        float feeC=Float.valueOf(amountFee.getText().toString());
        RadioGroup rg = (RadioGroup)findViewById(R.id.rGoup);
        String speakers = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        //System.out.print(duration);

        //DB
        SQLiteDataBaseBuild();
        SQLiteTableBuild();
        CheckEditTextStatus();
        InsertDataIntoSQLiteDatabase();

        //
        conf = new Conference(nameC, organizerC, fieldC, durationC, dateC, feeC, speakers, loc);
        confList.add(conf);//adds the conf to the vector
        Intent intent = new Intent(this, ConfListActivity.class);//creates intent
        intent.putParcelableArrayListExtra("conf", confList);//adds the data to the intent
        startActivityForResult(intent,100);//starts the activity
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        if (reqCode == 1) {
            if (resCode == RESULT_OK) {
                Location loc = data.getParcelableExtra("loc");
                String var = "      Location: " + loc.getLocName() + "\n      Room: " +
                        loc.getRoom() + "\n      Seats: " + loc.getSeats();
                location.setText(var);
            }
        }
    }
    public void SQLiteDataBaseBuild(){
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " +
                SQLiteHelper.TABLE_NAME_CONF + "(" +
                SQLiteHelper.Table_Column_ID_Conf +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                SQLiteHelper.Table_Column_1_User_ID_FK+" VARCHAR, " +
                SQLiteHelper.Table_Column_2_Name_Conf+" VARCHAR, " +
                SQLiteHelper.Table_Column_3_Organizer+" VARCHAR, " +
                SQLiteHelper.Table_Column_4_Field+" VARCHAR, "+
                SQLiteHelper.Table_Column_5_Duration+" VARCHAR, "+
                SQLiteHelper.Table_Column_6_Date+" VARCHAR, "+
                SQLiteHelper.Table_Column_7_Fee+" VARCHAR, "+
                SQLiteHelper.Table_Column_8_Speakers+" VARCHAR, "+
                SQLiteHelper.Table_Column_9_Location+" VARCHAR);");
    }

    public void CheckEditTextStatus(){
        nameHolder = name.getText().toString();
        organizerHolder =organizer.getText().toString();
        fieldHolder = field.getSelectedItem().toString();
        locationHolder = location.getText().toString();
        durationHolder = Integer.valueOf(duration.getProgress()).toString();
        dateHolder = date.getText().toString();
        feeHolder=Float.valueOf(amountFee.getText().toString()).toString();
        RadioGroup rg = (RadioGroup)findViewById(R.id.rGoup);
        speakersHolder = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();

        if(TextUtils.isEmpty(nameHolder) || TextUtils.isEmpty(organizerHolder) || TextUtils.isEmpty(fieldHolder) ||
        TextUtils.isEmpty(locationHolder) || TextUtils.isEmpty(durationHolder) || TextUtils.isEmpty(fieldHolder) ||
        TextUtils.isEmpty(nameHolder) || TextUtils.isEmpty(organizerHolder) || TextUtils.isEmpty(fieldHolder))
        {
            editTextEmptyHolder = false ;
        }
        else {
            editTextEmptyHolder = true ;
        }
    }

    public void InsertDataIntoSQLiteDatabase(){
        if(editTextEmptyHolder == true)
        {
            SQLiteDataBaseQueryHolder = "INSERT INTO "+ SQLiteHelper.TABLE_NAME_CONF+
                    " (user_id,name,organizer,field,duration,date,fee,speakers,location) VALUES('"
                    + userID+"', '"+nameHolder+"', '"+organizerHolder+"', '"+ fieldHolder+"', '"+ durationHolder+"', '"+dateHolder+"', '"+ feeHolder+
                    "', '"+speakersHolder+ "', '" + locationHolder + "');";
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();
            Toast.makeText(ConfActivity.this,"Conference Recorded Successfully", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(ConfActivity.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();
        }
    }
}

