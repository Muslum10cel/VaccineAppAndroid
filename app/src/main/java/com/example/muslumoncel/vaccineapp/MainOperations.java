package com.example.muslumoncel.vaccineapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.muslum.vaccineapp.ws.WebServiceOperations;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by muslumoncel on 04/02/16.
 */
public class MainOperations extends Activity implements AdapterView.OnItemClickListener {

    private final WebServiceOperations webServiceOperations = new WebServiceOperations();
    private EditText userName, passWord, confirmPassword, fullName, e_mail;
    private int registerStatus;
    private ListView ops;
    private List<Options> op = new ArrayList<>();
    private String user, pass, passconf, fullname, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        ops = (ListView) findViewById(R.id.optionList);
        PrivateAdapter adapter = new PrivateAdapter(this, op);
        op.add(new Options("Log In"));
        op.add(new Options("Register"));
        op.add(new Options("Exit"));
        ops.setAdapter(adapter);
        ops.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                break;
            case 1:
                Alert(1, parent.getItemAtPosition(position).toString());
                break;
            case 2:
                Alert(2, (ops.getItemAtPosition(position)).toString());

        }
    }

    private void Alert(int item, String message) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);
        alert.setTitle(message);
        switch (item) {
            case 0:
                break;
            case 1:
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View tempView = inflater.inflate(R.layout.register, null);
                userName = (EditText) tempView.findViewById(R.id.userRegEditText);
                passWord = (EditText) tempView.findViewById(R.id.passRegEditText);
                confirmPassword = (EditText) tempView.findViewById(R.id.confPassRegEdit);
                fullName = (EditText) tempView.findViewById(R.id.fullNameRegEdit);
                e_mail = (EditText) tempView.findViewById(R.id.emailRegEdit);
                alert.setView(tempView);
                alert.setCancelable(false);
                alert.setPositiveButton(getResources().getString(R.string.register), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user = userName.getText().toString();
                        pass = passWord.getText().toString();
                        passconf = confirmPassword.getText().toString();
                        fullname = fullName.getText().toString();
                        email = e_mail.getText().toString();
                        if ((user == null || user.length() == 0) || (pass == null || pass.length() == 0) || (passconf == null || passconf.length() == 0)
                                || (fullname == null || fullname.length() == 0) || (email == null || email.length() == 0))
                            return;
                        try {
                            registerStatus = new Register(user, pass, passconf, fullname, email).execute().get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                break;
            case 2:
                System.exit(1);
        }
        alert.show();
    }

    private class Register extends AsyncTask<Void, Void, Integer> {
        private final ProgressDialog progDailog = new ProgressDialog(MainOperations.this);
        private String user, pass, passConf, fullname, email;

        public Register(String user, String pass, String passconf, String fullname, String email) {
            this.user = user;
            this.pass = pass;
            this.passConf = passconf;
            this.fullname = fullname;
            this.email = email;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            progDailog.setMessage("Please Wait...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                return webServiceOperations.register(user, fullname, pass, passConf, email);
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
            if (registerStatus == -2) {
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.passnotmatch), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }
}
