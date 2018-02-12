package com.example.laura.project.Activities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.laura.project.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class TermCondActivity extends AppCompatActivity {

    private static final String TAG = "TermCondActivity";

    private ImageView imgTerm;
    private TextView tvTerm;
    private ProgressBar progressTerm;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_cond);
        imgTerm = (ImageView) findViewById(R.id.imgTErm);
        tvTerm = (TextView)findViewById(R.id.textViewTerm);
        progressTerm = (ProgressBar) findViewById(R.id.progressTerms);
    }

    public void viewTermsCondClick(View view)
    {
        TermsAndConditions tc = new TermsAndConditions();
        tc.execute("http://i0.wp.com/peerpex.com/wp-content/uploads/2016/10/sample-terms-and-conditions-template-termsfeed.jpg");

    }

    class TermsAndConditions extends AsyncTask<String,Integer,Bitmap>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TermCondActivity.this);
            progressDialog.setMessage("Downloading image ...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap b) {
            super.onPostExecute(b);
            imgTerm.setImageBitmap(b);
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressTerm.setProgress(values[0]);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String urlString = params[0];
            HttpURLConnection connection = null;
            Bitmap b = null;
            try
            {
                URL url = new URL(urlString);
                URLConnection urlConnection = url.openConnection();
                if(urlConnection instanceof HttpURLConnection){
                    connection = (HttpURLConnection) urlConnection;
                    connection.connect();
                    int resultCode = connection.getResponseCode();
                    if(resultCode == HttpURLConnection.HTTP_OK){
                        InputStream is = connection.getInputStream();
                        b = BitmapFactory.decodeStream(is);
                    }
                }
                for(int i=0; i<=100; i+=10)
                {
                    publishProgress(i);
                }
            }catch(Exception ex){
                Log.e(TAG,ex.getMessage());
            }
            finally {
                if(connection != null)
                    connection.disconnect();
            }
            return b;
        }
    }
}

