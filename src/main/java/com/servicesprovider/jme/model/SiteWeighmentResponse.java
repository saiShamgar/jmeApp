package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akash on 3/26/2018.
 */

public class SiteWeighmentResponse {
    @SerializedName("Code")
    @Expose
    private int responseCode;

    @SerializedName("Message")
    @Expose
    private String responseMessage;

    @SerializedName("ScheduleNumber")
    @Expose
    private String scheduleNumber;

    @SerializedName("Grader_EmpName")
    @Expose
    private String graderEmpName;

    @SerializedName("Helper_EmpName_1")
    @Expose
    private String helperEmpNameOne;

    @SerializedName("Helper_EmpName_2")
    @Expose
    private String helperEmpNameTwo;

    @SerializedName("Procurement_Schdule_Date")
    @Expose
    private String ProcurementSchduleDate;

    @SerializedName("Vechile_ID")
    @Expose
    private String vechileID;

    @SerializedName("Box_Required")
    @Expose
    private String boxRequired;

    @SerializedName("Nets_Required")
    @Expose
    private String netsRequired;

    @SerializedName("LstEnquiryNos")
    @Expose
    private List<EnquiryNumber> enquiryNumberList = new ArrayList<>();

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

    public String getScheduleNumber() {
        return scheduleNumber;
    }

    public void setScheduleNumber(String scheduleNumber) {
        this.scheduleNumber = scheduleNumber;
    }

    public String getGraderEmpName() {
        return graderEmpName;
    }

    public void setGraderEmpName(String graderEmpName) {
        this.graderEmpName = graderEmpName;
    }

    public String getHelperEmpNameOne() {
        return helperEmpNameOne;
    }

    public void setHelperEmpNameOne(String helperEmpNameOne) {
        this.helperEmpNameOne = helperEmpNameOne;
    }

    public String getHelperEmpNameTwo() {
        return helperEmpNameTwo;
    }

    public void setHelperEmpNameTwo(String helperEmpNameTwo) {
        this.helperEmpNameTwo = helperEmpNameTwo;
    }

    public String getProcurementSchduleDate() {
        return ProcurementSchduleDate;
    }

    public void setProcurementSchduleDate(String procurementSchduleDate) {
        ProcurementSchduleDate = procurementSchduleDate;
    }

    public String getVechileID() {
        return vechileID;
    }

    public void setVechileID(String vechileID) {
        this.vechileID = vechileID;
    }

    public String getBoxRequired() {
        return boxRequired;
    }

    public void setBoxRequired(String boxRequired) {
        this.boxRequired = boxRequired;
    }

    public String getNetsRequired() {
        return netsRequired;
    }

    public void setNetsRequired(String netsRequired) {
        this.netsRequired = netsRequired;
    }

    public List<EnquiryNumber> getEnquiryNumberList() {
        return enquiryNumberList;
    }

    public void setEnquiryNumberList(List<EnquiryNumber> enquiryNumberList) {
        this.enquiryNumberList = enquiryNumberList;
    }
}
