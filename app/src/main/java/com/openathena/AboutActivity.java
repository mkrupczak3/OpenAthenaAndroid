// AboutActivity.java
// OpenAthena for Android
// Copyright 2022
// Bobby Krupczak

package com.openathena;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.openathena.databinding.ActivityAboutBinding;

import java.io.FileOutputStream;
import java.io.PrintWriter;

public class AboutActivity extends AppCompatActivity {

    public static String TAG = AboutActivity.class.getSimpleName();
    TextView aboutText;
    String versionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG,"onCreate started");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        aboutText = (TextView)findViewById(R.id.aboutText);

        // try to get our version out of app/build.gradle
        // versionName field
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(),0).versionName;
            Log.d(TAG, "Got version " + versionName);
        }
        catch (Exception e) {
            versionName = "unknown";
        }

        // set About text
        aboutText.setText(Html.fromHtml( "OpenAthena for Android version "+versionName+"<br>"
                        + "Matthew Krupczak, Bobby Krupczak, et al.<br>"
                        + "Copyright 2022, "
                        + "<a href=\"https://openathena.com/\">OpenAthena.com</a> <br>"
                        + "<br>Open Athena is a project to enable precision indirect fires that disrupt conventional combined arms warfare.  "
                        + "This is accomplished by combining consumer rotary-wing aircraft (drones) sensor data with geospatial topography data "
                        + "to provide the instantaneous location of target(s)<br>"
                        + "<br><a href=\"https://github.com/mkrupczak3/OpenAthena\">View the project on GitHub</a><br>"
                        + "mkrupczak3/OpenAthena<br>"
                        + "<p><p><p><p>Project maintained by <a href=\"https://github.com/mkrupczak3\">mkrupczak3</a><br>"
                ,0,null, null)
        );

    } // onCreate()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;

        int id = item.getItemId();

        if (id == R.id.action_calculate) {
            // jump to main activity
            // its already created
            intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_prefs) {
            intent = new Intent(getApplicationContext(), PrefsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_log) {
            intent = new Intent(getApplicationContext(),ActivityLog.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            return true;
        }

        // don't do anything if about is selected as we are already there

        return super.onOptionsItemSelected(item);
    }

        @Override
    protected void onResume() {
        Log.d(TAG,"onResume");
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        Log.d(TAG,"onPause");

        //appendText("onPause\n");
        super.onPause();

    } // onPause()

    @Override
    protected void onDestroy()
    {
        Log.d(TAG,"onDestroy started");

        // close logfile
        //appendText("onDestroy\n");
        // do whatever here
        super.onDestroy();

    } // onDestroy()

    private void appendText(final String aStr)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                aboutText.append(aStr);
            }
        });

    } // appendText to textView but do so on UI thread

    private void appendLog(String str)
    {
        FileOutputStream fos;
        PrintWriter pw;

        Log.d(TAG,"appendLogLocal started");

        try {
            fos = openFileOutput(MainActivity.LOG_NAME, Context.MODE_PRIVATE|Context.MODE_APPEND);
            pw = new PrintWriter(fos);
            pw.print(str);
            pw.close();
            fos.close();
            Log.d(TAG,"appendLogLocal: wrote to logfile");

        } catch (Exception e) {
            Log.d(TAG,"appendLogLocal: failed to write log:"+e.getMessage());
        }

    } // appendLog()
}