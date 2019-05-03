package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akash on 3/26/2018.
 */

public class ScheduleResponse {
    @SerializedName("Emp_id")
    @Expose
    private String employeeid;
    @SerializedName("Code")
    @Expose
    private int responseCode;

    @SerializedName("Message")
    @Expose
    private String responseMessage;

    @SerializedName("LstScheduleNumbers")
    @Expose
    private List<ScheduleNumber> scheduleList = new ArrayList<>();

    public List<ScheduleNumber> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<ScheduleNumber> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }


}
