package com.example.muslumoncel.vaccineapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muslumoncel on 04/02/16.
 */
public class MainOperations extends Activity implements AdapterView.OnItemClickListener {

    private ListView ops;
    private List<Options> op = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        ops = (ListView) findViewById(R.id.optionList);
        PrivateAdapter adapter = new PrivateAdapter(this, op);
        op.add(new Options("Register"));
        op.add(new Options("Log In"));
        op.add(new Options("Exit"));
        ops.setAdapter(adapter);
        ops.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Alert(0, parent.getItemAtPosition(position).toString());
                break;
            case 1:
                Alert(1, parent.getItemAtPosition(position).toString());
                break;
            case 2:
                Alert(2, parent.getItemAtPosition(position).toString());

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
                alert.setView(tempView);

                break;
            case 2:
                System.exit(1);
        }
    }
}
