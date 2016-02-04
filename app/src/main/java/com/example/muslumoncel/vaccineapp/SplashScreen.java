package com.example.muslumoncel.vaccineapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
                } finally {
                    finish();
                    Intent intent=new Intent(SplashScreen.this, MainOperations.class);
                    startActivity(intent);
                }
            }
        };
        splash.start();

    }
}
