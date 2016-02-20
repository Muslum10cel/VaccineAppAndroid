package com.example.muslumoncel.vaccineapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.com.muslumoncel.jsonparseoperations.Lists;
import com.com.muslumoncel.jsonparseoperations.Tags;
import com.muslum.vaccineapp.ws.WebServiceOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by muslumoncel on 01/02/16.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);
        // ToDo add your GUI initialization code here

        Thread splash = new Thread() {
            @Override
            public void run() {
                try {
                    Log.i("Status : ", " Sleeping...");
                    sleep(3000);
                } catch (Exception e) {
                    Log.e("Status : ", " Sleep Error!");
                }
            }
        };
        splash.start();
        new GetVaccineNames().execute();
    }

    private class GetVaccineNames extends AsyncTask<Void, Void, Void> {

        private JSONObject jsonObject = null;
        private WebServiceOperations webServiceOperations = new WebServiceOperations();
        ProgressDialog progressDialog = new ProgressDialog(SplashScreen.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Please Wait");
            progressDialog.setIndeterminate(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(true);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                jsonObject = webServiceOperations.getVaccineNames();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("Vaccines");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject temp = jsonArray.getJSONObject(i);
                    Lists.vaccineNames.add(temp.getString("Vaccine name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
            finish();
            Intent intent = new Intent(SplashScreen.this, MainOperations.class);
            startActivity(intent);
        }
    }


}
