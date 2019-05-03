package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Akash on 3/24/2018.
 */

public class LoginRequest {

    @SerializedName("Emp_name")
    @Expose
    private String empName;

    @SerializedName("User_MailId")
    @Expose
    private String empEmail;

    @SerializedName("password")
    @Expose
    private String empPassword;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }
}
