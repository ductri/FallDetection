package ductri.falldetection.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import ductri.falldetection.R;
import ductri.falldetection.service.AccelerationService;
import ductri.falldetection.utils.Utils;

public class MainActivity extends AppCompatActivity {


    // UI
    Switch trackingMode;
    TextView textView;
    TextView textView_Status;

    // Logic
    Intent trackingServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Must be initial within this function
        trackingServiceIntent = new Intent(MainActivity.this, AccelerationService.class);

        // Load UI
        textView = (TextView)findViewById(R.id.textView_trackingMode);
        textView_Status = (TextView)findViewById(R.id.textView_Status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        assert drawer != null;
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        assert navigationView != null;
//        navigationView.setNavigationItemSelectedListener(this);

        ((Switch)findViewById(R.id.switch_tracking)).setOnCheckedChangeListener(switchHandler);

        // Start service
        Log.i(Utils.TAG, "Start service to tracking");
        startService(trackingServiceIntent);
        textView.setText("You are protected!");
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        assert drawer != null;
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_setting) {
//            Intent intent = new Intent(this, SettingsActivity.class);
//            startActivity(intent);
//        } else if (id == R.id.nav_about) {
//            Toast.makeText(this, "Author:ductricse", Toast.LENGTH_SHORT).show();
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        assert drawer != null;
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    private CompoundButton.OnCheckedChangeListener switchHandler = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                // Start service
                Log.i(Utils.TAG, "Start service to tracking");
                startService(trackingServiceIntent);
                textView.setText("You are protected!");
            } else {
                // Stop service
                Log.i(Utils.TAG, "Stop tracking service");
                stopService(trackingServiceIntent);
                textView.setText("You are not protected!");
            }
        }
    };
}
