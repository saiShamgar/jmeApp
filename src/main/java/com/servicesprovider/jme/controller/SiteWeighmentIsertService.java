package com.servicesprovider.jme.controller;

import android.content.Context;

import com.servicesprovider.jme.model.InsertSiteWeightmentRequest;
import com.servicesprovider.jme.model.InsertSiteWeightmentResponse;
import com.servicesprovider.jme.model.SharedPrefrencesData;
import com.servicesprovider.jme.model.WeightDataBody;
import com.servicesprovider.jme.view.WeightDetails;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Akash on 3/29/2018.
 */

public class SiteWeighmentIsertService extends BaseService {

    private Context mContext = null;
    private OnSiteInsertListener mCallback = null;
    private Observable<InsertSiteWeightmentResponse> observable = null;

    public SiteWeighmentIsertService(Context mContext, OnSiteInsertListener mCallback) {
        this.mContext = mContext;
        this.mCallback = mCallback;
    }

    public interface OnSiteInsertListener {
        void onSiteWeightSuccess(InsertSiteWeightmentResponse response);

        void onSiteWeightFailure(Throwable error);
    }

    @Override
    public void cancelRequest() {

        if (observable != null) {
            mCallback = null;
            observable = null;
        }
    }

    public void getResponse(List<WeightDataBody> dataBodyList, String status) {

        InsertSiteWeightmentRequest request = new InsertSiteWeightmentRequest();

        request.setEmpId(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.EMPLOYEE_ID));
        request.setAgentName(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.AGENT_NAME));
        request.setFarmerLocation(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.FARMER_LOCATION));
        request.setFarmerName(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.FARMER_NAME));
        request.setFarmerPondNavigation(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.FARMER_LATITUDE) + "," +
                SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.FARMER_LONGITUDE));
        request.setFarmerPondNo(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.FARMER_POND_NUMBER));
        request.setMaterialGroupName(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.MATERIAL_GROUP_NAME));
        request.setTotalWeightAllCounts(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.TOTAL_WEIGHT_FOR_ALL_COUNT));
        request.setProductVarietyName(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.PRODUCT_VARIETY_NAME));
        request.setEnquiryNo(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.ENQUIRY_NUMBER));
        request.setLotNumber(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.LOT_NUMBER));
        request.setScheduleNumber(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.SCHEDULE_NUMBER));
        request.setProductImage("");
        request.setListOfWeighment(dataBodyList);

        IsServiceApi serviceApi = mRetrofit.create(IsServiceApi.class);

        switch (status) {
            case WeightDetails.COMING_FOR_FACTORY:
                observable = serviceApi.getFactoryResponse(request);
                break;
            case WeightDetails.COMING_FOR_FACTORY_MANUAL:
                observable = serviceApi.getFactoryResponse(request);
                break;
            case WeightDetails.COMING_FOR_SITE:
                observable = serviceApi.getResponse(request);
                break;
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<InsertSiteWeightmentResponse>() {
                    @Override
                    public void onNext(InsertSiteWeightmentResponse value) {
                        if (mCallback != null) {
                            mCallback.onSiteWeightSuccess(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mCallback != null) {
                            mCallback.onSiteWeightFailure(e);
                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
