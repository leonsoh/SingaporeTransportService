package com.example.leon.singaporetransportservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Leon on 23/2/19.
 */

public class HomeActivity extends AppCompatActivity{

    Button busButton, trainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_activity);


        busButton = (Button)findViewById(R.id.home_bus_button);
        trainButton = (Button)findViewById(R.id.home_train_button);


        busButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BusSearchActivity.class);
                startActivity(intent);
            }
        });

        trainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TrainAlertActivity.class);
                startActivity(intent);
            }
        });




    }


}
