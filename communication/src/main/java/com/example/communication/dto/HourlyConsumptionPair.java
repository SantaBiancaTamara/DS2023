package com.example.communication.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class HourlyConsumptionPair {
    private String hour;
    private double consumption;

    // Constructors, getters, and setters
//
//    public HourlyConsumptionPair(String hour, double consumption) {
//        this.hour = hour;
//        this.consumption = consumption;
//    }
//
//    public String getHour() {
//        return hour;
//    }
//
//    public void setHour(String hour) {
//        this.hour = hour;
//    }
//
//    public double getConsumption() {
//        return consumption;
//    }
//
//    public void setConsumption(double consumption) {
//        this.consumption = consumption;
//    }


}
