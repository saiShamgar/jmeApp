package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Akash on 3/24/2018.
 */

public class LoginResponse {

    @SerializedName("Code")
    @Expose
    private int responseCode;

    @SerializedName("Message")
    @Expose
    private String responseMessage;

    @SerializedName("Emp_id")
    @Expose
    private String employeeid;

    @SerializedName("Emp_name")
    @Expose
    private String employeeName;

    @SerializedName("User_MailId")
    @Expose
    private String employeeEmailId;
    @SerializedName("EmployeeContactNo")
    @Expose
    private String EmployeeContactNo;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmailId() {
        return employeeEmailId;
    }

    public void setEmployeeEmailId(String employeeEmailId) {
        this.employeeEmailId = employeeEmailId;
    }

    public String getEmployeeContactNo() {
        return EmployeeContactNo;
    }

    public void setEmployeeContactNo(String employeeContactNo) {
        EmployeeContactNo = employeeContactNo;
    }
}
