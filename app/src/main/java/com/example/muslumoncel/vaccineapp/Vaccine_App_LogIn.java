package com.example.muslumoncel.vaccineapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.muslum.vaccineapp.ws.WebServiceOperations;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Vaccine_App_LogIn extends AppCompatActivity implements View.OnClickListener {

    private Button log_in_button;
    private EditText username, password;
    private WebServiceOperations webServiceOperations = new WebServiceOperations();
    private int Log_in_status;

    private String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        log_in_button = (Button) findViewById(R.id.lgnButton);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        log_in_button.setOnClickListener(this);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Objects.equals(s, null))
                    user = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Objects.equals(s, null))
                    pass = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.lgnButton:
                if ((user == null || user.length() == 0) || (pass == null || pass.length() == 0))
                    return;
                new LogIn(user, pass).execute();
                break;
        }
    }

    private class LogIn extends AsyncTask<Void, Void, Integer> {

        private final ProgressDialog progDailog = new ProgressDialog(Vaccine_App_LogIn.this);
        private String user, pass;

        public LogIn(String username, String password) {
            this.user = username;
            this.pass = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog.setMessage("Please Wait...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                Log_in_status = webServiceOperations.log_in(user, pass);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progDailog.dismiss();
            if (Objects.equals(Log_in_status, LogLevel.USER)) {
            }
        }
    }
}
