package com.muslumoncel.usermain;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.com.muslumoncel.jsonparseoperations.Baby;
import com.com.muslumoncel.jsonparseoperations.GetAndParseDatas;
import com.com.muslumoncel.jsonparseoperations.OperationTags;
import com.example.muslumoncel.vaccineapp.R;
import com.muslum.vaccineapp.ws.WebServiceOperations;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class UserMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private Spinner day, month;
    private String username;
    private EditText year, babyname;
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
        babyList.setOnItemClickListener(this);
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
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(UserMain.this);
                alert.setView(addBabyView);
                alert.setCancelable(false);
                alert.setTitle(getResources().getString(R.string.addBaby));
                alert.setPositiveButton(getResources().getString(R.string.addBaby), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AddBaby(username, babyname.getText().toString(), year.getText().toString() + "-" + month.getSelectedItem().toString() + "-" + day.getSelectedItem().toString()).execute();
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



    }

    private class AddBaby extends AsyncTask<Void, Void, Void> {

        private WebServiceOperations webServiceOperations = new WebServiceOperations();
        private ProgressDialog progressDialog = new ProgressDialog(UserMain.this);
        private String babyName, dateOfBirth, username;
        private int addStatus;

        public AddBaby(String username, String babyName, String dateOfBirth) {
            this.username = username;
            this.babyName = babyName;
            this.dateOfBirth = dateOfBirth;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Adding...");
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
            Toast toast = null;
            if (Objects.equals(addStatus, 1)) {
                toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.addSuccess), Toast.LENGTH_LONG);
            } else if (Objects.equals(addStatus, -1)) {
                toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.addNotSuccess), Toast.LENGTH_LONG);
            }
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
    }
}
