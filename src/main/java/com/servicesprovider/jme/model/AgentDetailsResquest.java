package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Akash on 3/26/2018.
 */

public class AgentDetailsResquest {
    @SerializedName("Enquiry_No")
    @Expose
    private String enquiryNumber;

    @SerializedName("Emp_id")
    @Expose
    private String empId;

    public String getEnquiryNumber() {
        return enquiryNumber;
    }

    public void setEnquiryNumber(String enquiryNumber) {
        this.enquiryNumber = enquiryNumber;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
