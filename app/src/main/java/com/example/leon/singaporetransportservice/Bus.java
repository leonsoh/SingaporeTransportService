package com.example.leon.singaporetransportservice;

import java.io.Serializable;

/**
 * Created by Leon on 23/2/19.
 */

public class Bus implements Serializable {
    private String busStop;
    private String busNumber;
    private String busArrival;
    private String busType;
    private String seatAvailability;

    public Bus(){
    }

    public Bus(String busStop, String busNumber, String busArrival, String busType, String seatAvailability) {
        this.busStop = busStop;
        this.busNumber = busNumber;
        this.busArrival = busArrival;
        this.busType = busType;
        this.seatAvailability = seatAvailability;
    }

    public String getBusType() {
        return busType;
    }


    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getBusStop() {
        return busStop;
    }

    public void setBusStop(String busStop) {
        this.busStop = busStop;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusArrival() {
        return busArrival;
    }

    public void setBusArrival(String busArrival) {
        this.busArrival = busArrival;
    }

    public String getSeatAvailability() {
        return seatAvailability;
    }

    public void setSeatAvailability(String seatAvailability) {
        this.seatAvailability = seatAvailability;
    }
}
