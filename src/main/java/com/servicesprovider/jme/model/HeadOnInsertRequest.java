package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muheeb on 04-Apr-18.
 */

public class HeadOnInsertRequest
{
    @SerializedName("Emp_id")
    @Expose
    String empId;

    @SerializedName("Lot_No")
    @Expose
    String LotNo;

    @SerializedName("Material_Group_Name")
    @Expose
    String materialGroup;

    @SerializedName("Product_Variety_Name")
    @Expose
    String varietyName;

    @SerializedName("HOGR_Total_Weight")
    @Expose
    String grantTotal;

    @SerializedName("ListOfWeighment")
    @Expose
    List<HeadOnWeightDataBody> list = new ArrayList<>();

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getLotNo() {
        return LotNo;
    }

    public void setLotNo(String lotNo) {
        LotNo = lotNo;
    }

    public String getMaterialGroup() {
        return materialGroup;
    }

    public void setMaterialGroup(String materialGroup) {
        this.materialGroup = materialGroup;
    }

    public String getVarietyName() {
        return varietyName;
    }

    public void setVarietyName(String varietyName) {
        this.varietyName = varietyName;
    }

    public String getGrantTotal() {
        return grantTotal;
    }

    public void setGrantTotal(String grantTotal) {
        this.grantTotal = grantTotal;
    }

    public List<HeadOnWeightDataBody> getList() {
        return list;
    }

    public void setList(List<HeadOnWeightDataBody> list) {
        this.list = list;
    }
}
