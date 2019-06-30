package com.zillion.tu1.model;

import lombok.Getter;

import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.Map;

@Getter
public class Commute {

    //@Getter
    private String mode = "100";
    private String arrivalTime;
    private String departureTime;
    private String totalDistance;
    private String duration;
    private String endAddress;
    private String startAddress;
    private ArrayList<String> direction;

    public Commute (Map<String, Object > commuteData) {

        arrivalTime   = ((commuteData.get("arrival_time") != null) ? (String) commuteData.get("arrival_time") : "-");
        departureTime = ((commuteData.get("departure_time") != null) ? (String) commuteData.get("departure_time") : "-");
        totalDistance = (String) commuteData.get ("distance");
        duration      = (String) commuteData.get("duration");
        endAddress    = (String) commuteData.get("end_address");
        startAddress  = (String) commuteData.get("start_address");
        direction     = (ArrayList <String>) commuteData.get("direction");
    }

    public Commute() {


    }

}