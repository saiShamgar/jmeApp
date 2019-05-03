package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Akash on 3/26/2018.
 */

public class ScheduleNumber {

    @SerializedName("ScheduleNumber")
    @Expose
    private String ScheduleNumber;

    public String getScheduleNumber() {
        return ScheduleNumber;
    }

    public void setScheduleNumber(String scheduleNumber) {
        ScheduleNumber = scheduleNumber;
    }
}

