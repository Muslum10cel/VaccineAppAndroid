package com.com.muslumoncel.jsonparseoperations;

import android.os.AsyncTask;
import android.widget.TextView;

import com.muslum.vaccineapp.ws.WebServiceOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by muslumoncel on 11/02/16.
 */
public class GetAndParseDatas {
    private final WebServiceOperations webServiceOperations = new WebServiceOperations();
    private static JSONObject babies, vaccineDetails;
    private String username;
    private TextView infoText;

    public GetAndParseDatas(String username, TextView infoText) {
        this.username = username;
        this.infoText = infoText;
        new getBabies(OperationTags.GETBABIES).execute();
    }

    private class getBabies extends AsyncTask<Void, Void, Void> {
        private String operation;

        public getBabies(String operation) {
            this.operation = operation;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                babies = webServiceOperations.getBabies(username);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            fillBabyList();
            infoText.setText("Number of recorded baby : " + String.valueOf(Lists.babies.size()));
        }
    }

    private void fillBabyList() {
        JSONArray jsonArray = null;
        try {
            jsonArray = babies.getJSONArray(Tags.BABIES_TAG);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject temp = jsonArray.getJSONObject(i);
                Lists.babies.add(new Baby(temp.getString(Tags.BABY_NAME_TAG), temp.getInt(Tags.BABY_ID_TAG)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class GetDetails extends AsyncTask<Void, Void, Void> {

        private int id;

        GetDetails(int id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                vaccineDetails = webServiceOperations.getBabyVaccineDetails(id);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public void getBabyVaccineDetail(int baby_id) {
        new GetDetails(baby_id).execute();
    }
}
