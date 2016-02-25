package com.example.muslumoncel.vaccineapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.muslum.vaccineapp.ws.WebServiceOperations;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private View tempView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        tempView = inflater.inflate(R.layout.register, null);
        userName = (EditText) tempView.findViewById(R.id.userRegEditText);
        passWord = (EditText) tempView.findViewById(R.id.passRegEditText);
        confirmPassword = (EditText) tempView.findViewById(R.id.confPassRegEdit);
        fullName = (EditText) tempView.findViewById(R.id.fullNameRegEdit);
        e_mail = (EditText) tempView.findViewById(R.id.emailRegEdit);
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
                Intent intent = new Intent(this, Vaccine_App_LogIn.class);
                finish();
                startActivity(intent);
                break;
            case 1:
                Alert(1, parent.getItemAtPosition(position).toString(), view);
                break;
            case 2:
                Alert(2, (ops.getItemAtPosition(position)).toString(), view);

        }
    }

    private void Alert(int item, String message, final View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);
        alert.setTitle(message);
        switch (item) {
            case 1:
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
                        char[] pas = pass.toCharArray();
                        char[] confPass = passconf.toCharArray();

                        if (pas.length != confPass.length) {
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.passnotmatch), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return;
                        } else {
                            for (int i = 0; i < pas.length; i++) {
                                if (!Objects.equals(pas[i], confPass[i])) {
                                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.passnotmatch), Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    return;
                                }
                            }
                        }
                        new Register(user, pass, fullname, email, view).execute();

                        if (!Objects.equals(tempView, null)) {
                            ViewGroup parent = (ViewGroup) tempView.getParent();
                            if (!Objects.equals(parent, null)) {
                                parent.removeAllViews();
                            }
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
                alert.setMessage(getResources().getString(R.string.exit));
                alert.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(1);
                    }
                }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        }
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class Register extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog progressDialog = new ProgressDialog(MainOperations.this);
        private String user, pass, fullname, email;
        private View view;

        public Register(String user, String pass, String fullname, String email, View view) {
            this.user = user;
            this.pass = pass;
            this.fullname = fullname;
            this.email = email;
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Registering...");
            progressDialog.setIndeterminate(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                registerStatus = webServiceOperations.register(user, fullname, pass, email);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void integer) {
            super.onPostExecute(integer);
            progressDialog.dismiss();
            Snackbar snackbar = null;
            if (Objects.equals(registerStatus, 2)) {
                snackbar = Snackbar.make(view, getResources().getString(R.string.regSuccess), Snackbar.LENGTH_LONG);
            } else if (Objects.equals(registerStatus, -1)) {
                snackbar = Snackbar.make(view, getResources().getString(R.string.exception), Snackbar.LENGTH_LONG);
            } else if (Objects.equals(registerStatus, 1)) {
                snackbar = Snackbar.make(view, getResources().getString(R.string.regNotSuccess), Snackbar.LENGTH_LONG);
            }
            snackbar.show();
        }
    }
}
