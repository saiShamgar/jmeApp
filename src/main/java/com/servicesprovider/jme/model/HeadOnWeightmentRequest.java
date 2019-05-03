package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by muheeb on 02-Apr-18.
 */

public class HeadOnWeightmentRequest
{
    @SerializedName("Lot_No")
    @Expose
    String lotNo;

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }
}
