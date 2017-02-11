package ductri.falldetection.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ductri.falldetection.R;

public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        TextView txtViewStatus = (TextView)findViewById(R.id.textView_Status);
        String status = getIntent().getStringExtra("status");
        txtViewStatus.setText(status);


    }


}
