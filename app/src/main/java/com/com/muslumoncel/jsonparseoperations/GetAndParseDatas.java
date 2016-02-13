package com.com.muslumoncel.jsonparseoperations;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.muslum.vaccineapp.ws.WebServiceOperations;
import com.muslumoncel.usermain.PrivateAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by muslumoncel on 11/02/16.
 */
public class GetAndParseDatas {
    private final WebServiceOperations webServiceOperations = new WebServiceOperations();
    private String userName, operationName;
    private ListView babyList;
    private TextView infoText;
    private Activity activity;
    private PrivateAdapter babyArrayAdapter;

    public GetAndParseDatas(Activity activity, String operationName, String userName, ListView babyList, PrivateAdapter babyArrayAdapter, TextView infoText) {
        this.activity = activity;
        this.operationName = operationName;
        this.userName = userName;
        this.babyList = babyList;
        this.babyArrayAdapter = babyArrayAdapter;
        this.infoText = infoText;
    }

    public void getBabies() {
        new GetDatas(activity, operationName, userName, babyList, babyArrayAdapter, infoText).execute();
    }

    private class GetDatas extends AsyncTask<Void, Void, Void> {
        private String userName, operationName;
        private Activity activity;
        private ProgressDialog progressDialog;
        private ListView babyList;
        private JSONObject babies;
        private TextView infoText;
        private PrivateAdapter babyArrayAdapter;

        public GetDatas(Activity activity, String operationName, String userName, ListView babyList, PrivateAdapter babyArrayAdapter, TextView infoText) {
            this.activity = activity;
            this.operationName = operationName;
            this.userName = userName;
            this.babyList = babyList;
            this.babyArrayAdapter = babyArrayAdapter;
            this.infoText = infoText;
            progressDialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Please Wait...");
            progressDialog.setMessage("Getting Datas...");
            progressDialog.setIndeterminate(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (Objects.equals(operationName, OperationTags.GETBABIES)) {
                try {
                    babies = webServiceOperations.getBabies(userName);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Objects.equals(operationName, OperationTags.GETBABIES)) {
                try {
                    Lists.babies.clear();
                    JSONArray jsonArray = babies.getJSONArray(Tags.BABIES_TAG);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject temp = jsonArray.getJSONObject(i);
                        Lists.babies.add(new Baby(temp.getString(Tags.BABY_NAME_TAG), temp.getInt(Tags.BABY_ID_TAG)));
                    }
                    babyArrayAdapter = new PrivateAdapter(activity, Lists.babies);
                    babyList.setAdapter(babyArrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            infoText.setText("Number of recorded baby : " + String.valueOf(Lists.babies.size()));
            progressDialog.dismiss();
        }
    }


}
