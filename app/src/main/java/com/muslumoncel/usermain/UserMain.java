package com.muslumoncel.usermain;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.com.muslumoncel.jsonparseoperations.Baby;
import com.com.muslumoncel.jsonparseoperations.GetAndParseDatas;
import com.com.muslumoncel.jsonparseoperations.Lists;
import com.com.muslumoncel.jsonparseoperations.OperationTags;
import com.example.muslumoncel.vaccineapp.R;
import com.muslum.vaccineapp.ws.WebServiceOperations;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class UserMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Spinner day, month, vaccine_names;
    private String username;
    private EditText year, babyname, comment_edit;
    private ViewGroup parent;
    private View addBabyView;
    private ListView babyList;
    private List<Baby> list;
    private PrivateAdapter privateAdapter;
    private Intent intent;
    private LayoutInflater inflater;
    private TextView userText, infoText;
    private GetAndParseDatas getAndParseDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        babyList = (ListView) findViewById(R.id.listViewBabies);
        privateAdapter = new PrivateAdapter(this, list);
        intent = getIntent();
        username = intent.getStringExtra("Username");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        addBabyView = inflater.inflate(R.layout.add_baby_layout, null);
        day = (Spinner) addBabyView.findViewById(R.id.babyBirthDay);
        month = (Spinner) addBabyView.findViewById(R.id.babyBirthMonth);
        year = (EditText) addBabyView.findViewById(R.id.babyBirthYear);
        babyname = (EditText) addBabyView.findViewById(R.id.babyNameEdit);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(UserMain.this);
                alert.setView(addBabyView);
                alert.setCancelable(false);
                alert.setTitle(getResources().getString(R.string.addBaby));
                alert.setPositiveButton(getResources().getString(R.string.addBaby), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Objects.equals(username.length(), 0) || Objects.equals(babyname.getText().length(), 0))
                            return;
                        new AddBaby(username, babyname.getText().toString(), year.getText().toString() + "-" + month.getSelectedItem().toString() + "-" + day.getSelectedItem().toString(), view).execute();
                        if (!Objects.equals(addBabyView, null)) {
                            parent = (ViewGroup) addBabyView.getParent();
                            if (!Objects.equals(parent, null)) {
                                parent.removeAllViews();
                            }
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!Objects.equals(addBabyView, null)) {
                            parent = (ViewGroup) addBabyView.getParent();
                            if (!Objects.equals(parent, null)) {
                                parent.removeAllViews();
                            }
                        }
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        userText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userInfoText);
        infoText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.babyCountInfoText);
        userText.setText(username);

        getAndParseDatas = new GetAndParseDatas(this, OperationTags.GETBABIES, intent.getStringExtra("Username"), babyList, privateAdapter, infoText);
        getAndParseDatas.getBabies();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.babies:
                babyList.setOnItemClickListener(null);
                babyList.setOnItemLongClickListener(null);
                break;
            case R.id.edit_completion:
                babyList.setOnItemLongClickListener(null);
                babyList.setOnItemClickListener(this);
                break;
            case R.id.show_dates:
                babyList.setOnItemClickListener(null);
                babyList.setOnItemLongClickListener(this);
                break;
            case R.id.write_comment:
                final View comment = inflater.inflate(R.layout.comment_layout, null);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Lists.vaccineNames);
                vaccine_names = (Spinner) comment.findViewById(R.id.comment_spinner);
                vaccine_names.setAdapter(arrayAdapter);
                comment_edit = (EditText) comment.findViewById(R.id.comment_edit);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle(getResources().getString(R.string.write_comment));
                builder.setView(comment);
                builder.setPositiveButton(getResources().getString(R.string.add_comment), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (comment_edit.getText().length() != 0) {
                            Log.i("Vaccine Name : ", vaccine_names.getSelectedItem().toString());
                            new AddComment(username, vaccine_names.getSelectedItem().toString(), comment_edit.getText().toString(), item.getActionView()).execute();
                        }
                        if (!Objects.equals(addBabyView, null)) {
                            parent = (ViewGroup) addBabyView.getParent();
                            if (!Objects.equals(parent, null)) {
                                parent.removeAllViews();
                            }
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!Objects.equals(addBabyView, null)) {
                            parent = (ViewGroup) addBabyView.getParent();
                            if (!Objects.equals(parent, null)) {
                                parent.removeAllViews();
                            }
                        }
                        dialog.dismiss();
                    }
                });
                builder.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.i("selected Item : ", parent.getItemAtPosition(position).toString());
        int temp = 0;
        for (Baby b : Lists.babies) {
            if (Objects.equals(parent.getItemAtPosition(position).toString(), b.getBaby_name())) {
                temp = b.getBaby_id();
                break;
            }
        }
        if (Objects.equals(temp, null))
            return;
        GetAndParseDatas getAndParseDatas = new GetAndParseDatas(this, OperationTags.GETVACCINEDETAILS, temp);
        getAndParseDatas.getBabyVaccineDetails();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("selected Item Long: ", parent.getItemAtPosition(position).toString());
        int temp = 0;
        for (Baby b : Lists.babies) {
            if (Objects.equals(parent.getItemAtPosition(position).toString(), b.getBaby_name())) {
                temp = b.getBaby_id();
                break;
            }
        }
        if (Objects.equals(temp, null))
            return false;
        GetAndParseDatas getAndParseDatas = new GetAndParseDatas(this, OperationTags.COMPLETEDVACCINES, temp);
        getAndParseDatas.getCompletionDetails();

        return true;
    }

    private class AddBaby extends AsyncTask<Void, Void, Void> {

        private WebServiceOperations webServiceOperations = new WebServiceOperations();
        private ProgressDialog progressDialog = new ProgressDialog(UserMain.this);
        private String babyName, dateOfBirth, username;
        private int addStatus;
        private View view;
        
        public AddBaby(String username, String babyName, String dateOfBirth, View view) {
            this.username = username;
            this.babyName = babyName;
            this.dateOfBirth = dateOfBirth;
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Baby is adding...");
            progressDialog.setIndeterminate(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                addStatus = webServiceOperations.addBaby(username, babyName, dateOfBirth);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            getAndParseDatas = new GetAndParseDatas(UserMain.this, OperationTags.GETBABIES, intent.getStringExtra("Username"), babyList, privateAdapter, infoText);
            getAndParseDatas.getBabies();
            switch (addStatus) {
                case 1:
                    Snackbar.make(view, getResources().getString(R.string.addSuccess), Snackbar.LENGTH_LONG).show();
                    break;
                case -1:
                    Snackbar.make(view, getResources().getString(R.string.addNotSuccess), Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private class AddComment extends AsyncTask<Void, Void, Void> {

        private String username, vaccine_name, comment;
        private WebServiceOperations webServiceOperations = new WebServiceOperations();
        private ProgressDialog progressDialog = new ProgressDialog(UserMain.this);
        private int commentAdd;
        private View view;

        public AddComment(String username, String vaccine_name, String comment, View view) {
            this.username = username;
            this.vaccine_name = vaccine_name;
            this.comment = comment;
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Comment is uploading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                commentAdd = webServiceOperations.addComment(username, vaccine_name, comment);
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
            progressDialog.dismiss();
            switch (commentAdd) {
                case 1:
                    Snackbar.make(view, getResources().getString(R.string.comment_add_success), Snackbar.LENGTH_LONG).show();
                    break;
                case -1:
                    Snackbar.make(view, getResources().getString(R.string.comment_add_notSuccess), Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
