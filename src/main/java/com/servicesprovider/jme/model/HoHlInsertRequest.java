package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muheeb on 11-Apr-18.
 */

public class HoHlInsertRequest
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

    @SerializedName("HOHL_Group_Total_Weight")
    @Expose
    String grandTotal;

    @SerializedName("ListOfWeighment")
    @Expose
    List<HoHlWeightDataBody> list = new ArrayList<>();

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

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandtotal) {
        this.grandTotal = grandTotal;
    }

    public List<HoHlWeightDataBody> getList() {
        return list;
    }

    public void setList(List<HoHlWeightDataBody> list) {
        this.list = list;
    }
}
