package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Akash on 3/26/2018.
 */

public class SiteWeighmentRequest {
    @SerializedName("ScheduleNumber")
    @Expose
    private String ScheduleNumber;
    @SerializedName("Emp_id")
    @Expose
    private String empId;

    public String getScheduleNumber() {
        return ScheduleNumber;
    }

    public void setScheduleNumber(String scheduleNumber) {
        ScheduleNumber = scheduleNumber;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
