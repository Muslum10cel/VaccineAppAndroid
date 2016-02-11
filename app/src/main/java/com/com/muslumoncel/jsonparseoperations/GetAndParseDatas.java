package com.com.muslumoncel.jsonparseoperations;

import android.os.AsyncTask;

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
    private static JSONObject completedVaccines, babies, vaccineDetails, vaccineNames;
    private String username;

    public GetAndParseDatas(String username) {
        this.username = username;
        new GetData(OperationTags.GETBABIES).execute();
        System.gc();
    }


    private class GetData extends AsyncTask<Void, Void, Void> {
        private String operation;
        private int baby_id;

        public GetData(String operation) {
            this.operation = operation;
        }

        public GetData(String operation, int baby_id) {
            this.operation = operation;
            this.baby_id = baby_id;
        }


        @Override
        protected Void doInBackground(Void... params) {

            try {
                if (operation.equals(OperationTags.GETBABIES))
                    babies = webServiceOperations.getBabies(username);
                else if (operation.equals(OperationTags.GETVACCINEDETAILS))
                    vaccineDetails = webServiceOperations.getBabyVaccineDetails(baby_id);
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
            if (operation.equals(OperationTags.GETBABIES))
                createBabies();
            else if (operation.equals(OperationTags.GETVACCINEDETAILS))
                addVaccineDetailstoBabies(baby_id);
        }
    }

    private void addVaccineDetailstoBabies(int baby_id) {
        
    }

    private void createBabies() {
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

    public void getVaccineDetails(int baby_id) {
        new GetData(OperationTags.GETVACCINEDETAILS, baby_id).execute();

    }
}
