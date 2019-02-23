package com.example.leon.singaporetransportservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONObject;


import api.HttpRequest;

/**
 * Created by Leon on 23/2/19.
 */

public class TrainAlertActivity extends AppCompatActivity{

    Button trainAlertButton;
    TextView trainAlertMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_train_alert);


        trainAlertButton = (Button)findViewById(R.id.train_alert_button);
        trainAlertMessage = (TextView)findViewById(R.id.train_alert_textview);


        trainAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    String url = "http://datamall2.mytransport.sg/ltaodataservice/TrainServiceAlerts/";
                    HttpRequest request = new HttpRequest(url);
                    request.setMethod("GET");
                    request.setAPIKey("AccountKey", "Ex/GVSHHRWGzi75Us2o/iA==");
                    request.execute();
                    try {
                        String jsonString = request.getResponse();
                        System.out.println(">>" + jsonString);
                        JSONObject jsonObject = new JSONObject(jsonString);
                        JSONObject jsonTrainAlert = jsonObject.getJSONObject("value");
                        Integer trainAlertStatus = jsonTrainAlert.getInt("Status");



                        if (trainAlertStatus == 2) {
                            trainAlertMessage.setText("There is a train disruption going on!");
                        } else {
                            trainAlertMessage.setText("No train disruption.");
                        }

                        

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });



    }
}
