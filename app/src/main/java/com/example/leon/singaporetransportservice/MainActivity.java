package com.example.leon.singaporetransportservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import api.HttpRequest;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    EditText editTextBusStopNumber, editTextBusNumber;

    TextView textViewBusArrival, textViewSeatsAvailability, textViewNextBus, textViewNextBusTwo;

    ImageView imageViewBusType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton floatingActionButtonSubmit = (FloatingActionButton)findViewById(R.id.fab);

        editTextBusStopNumber = (EditText)findViewById(R.id.layout_main_bus_stop_number);
        editTextBusNumber = (EditText)findViewById(R.id.layout_main_bus_number);
        textViewBusArrival = (TextView)findViewById(R.id.layout_main_bus_arrival);
        textViewSeatsAvailability = (TextView)findViewById(R.id.layout_main_bus_seat_availability);
        imageViewBusType = (ImageView)findViewById(R.id.layout_main_bus_image);
        textViewNextBus = (TextView)findViewById(R.id.layout_main_next_bus);
        textViewNextBusTwo = (TextView)findViewById(R.id.layout_main_next_bus_two);




        floatingActionButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String busStopCode = editTextBusStopNumber.getText().toString() + "";
                final String serviceNo = editTextBusNumber.getText().toString() + "";

                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    String url = "http://datamall2.mytransport.sg/ltaodataservice/BusArrivalv2?BusStopCode=" + busStopCode + "&ServiceNo=" + serviceNo ;
                    HttpRequest request = new HttpRequest(url);
                    request.setMethod("GET");
                    request.setAPIKey("AccountKey", "Ex/GVSHHRWGzi75Us2o/iA==");
                    request.execute();
                    try {
                        String jsonString = request.getResponse();
                        System.out.println(">>" + jsonString);
                        JSONObject jsonObject = new JSONObject(jsonString);
                        JSONArray jsonArr = jsonObject.getJSONArray("Services");
                        JSONObject param = jsonArr.getJSONObject(0);
                        JSONObject jsonBus = param.getJSONObject("NextBus");
                        JSONObject jsonBus2 = param.getJSONObject("NextBus2");
                        JSONObject jsonBus3 = param.getJSONObject("NextBus3");


                        Bus bus = new Bus();
                        Bus bus2 = new Bus();
                        Bus bus3 = new Bus();

                        bus.setBusArrival(jsonBus.getString("EstimatedArrival"));
                        bus.setSeatAvailability(jsonBus.getString("Load"));
                        bus.setBusType(jsonBus.getString("Type"));

                        bus2.setBusArrival(jsonBus2.getString("EstimatedArrival"));
                        bus3.setBusArrival(jsonBus3.getString("EstimatedArrival"));


                        String seatAvailabilityMessage;


                        if (bus.getSeatAvailability().equalsIgnoreCase("sea")) {
                            seatAvailabilityMessage = "Seats are available";
                        } else if (bus.getSeatAvailability().equalsIgnoreCase("sda")) {
                            seatAvailabilityMessage = "Standing are available";
                        } else {
                            seatAvailabilityMessage = "Super crowded!";
                        }


                        if (bus.getBusType().equalsIgnoreCase("sd")) {
                            imageViewBusType.setImageResource(R.drawable.single_bus);
                        } else if (bus.getBusType().equalsIgnoreCase("dd")) {
                            imageViewBusType.setImageResource(R.drawable.double_decker_bus);
                        } else {
                            imageViewBusType.setImageResource(R.drawable.bendy_bus);
                        }

                        Date currentDate = new Date();

                        String busArrivalDate = bus.getBusArrival();
                        String nextBusDate = bus2.getBusArrival();
                        String nextBusTwoDate = bus3.getBusArrival();

                        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
                        Date newBusArrivalDate = spf.parse(busArrivalDate);
                        Date newNextBusDate = spf.parse(nextBusDate);
                        Date newNextBusTwoDate = spf.parse(nextBusTwoDate);



                        long diff = currentDate.getTime() - newBusArrivalDate.getTime();
                        long seconds = diff / 1000;
                        long minutes = seconds / 60;

                        long nextBusDiff = currentDate.getTime() - newNextBusDate.getTime();
                        long seconds1 = nextBusDiff / 1000;
                        long minutes1 = seconds1 / 60;

                        long nextBusTwoDiff = currentDate.getTime() - newNextBusTwoDate.getTime();
                        long seconds2 = nextBusTwoDiff / 1000;
                        long minutes2 = seconds2 / 60;




                        String busArrivalTime = Long.toString(minutes).replaceAll("-", "");
                        String nextBusArrivalTime = Long.toString(minutes1).replaceAll("-", "");
                        String nextBusTwoArrivalTime = Long.toString(minutes2).replaceAll("-", "");

                        if (busArrivalTime.equals("0")) {
                            busArrivalTime = "Arriving!";
                        } else if (busArrivalTime.equals("1")) {
                            busArrivalTime =  busArrivalTime + " minute";
                        } else {
                            busArrivalTime = busArrivalTime + " minutes";
                        }




                        textViewBusArrival.setText(busArrivalTime);
                        textViewSeatsAvailability.setText(seatAvailabilityMessage);
                        textViewNextBus.setText(nextBusArrivalTime);
                        textViewNextBusTwo.setText(nextBusTwoArrivalTime);







                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });





    }
}
