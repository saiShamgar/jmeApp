package com.servicesprovider.jme.controller;


import android.content.Context;

import com.google.gson.Gson;
import com.servicesprovider.jme.model.HeadOnInsertRequest;
import com.servicesprovider.jme.model.HeadOnInsertResponse;
import com.servicesprovider.jme.model.HeadOnWeightDataBody;
import com.servicesprovider.jme.model.HeadOnWeightmentResponse;
import com.servicesprovider.jme.model.HoHlInsertRequest;
import com.servicesprovider.jme.model.HoHlWeightDataBody;
import com.servicesprovider.jme.model.SharedPrefrencesData;
import com.servicesprovider.jme.utils.AppConstant;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by muheeb on 11-Apr-18.
 */

public class HoHlWeighmentInsertService extends BaseService
{
    private Context mContext;
    OnHeadOnHeadLessInsertListener listener = null;
    private Observable<HeadOnInsertResponse> observable = null;

    public HoHlWeighmentInsertService(Context mContext, OnHeadOnHeadLessInsertListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    public interface OnHeadOnHeadLessInsertListener{
        void onHOHLWeightSuccess(HeadOnInsertResponse response);
        void onHOHLWeightFailure(Throwable error);
    }
    @Override
    public void cancelRequest()
    {
        if (observable != null){
            listener = null;
            observable = null;
        }
    }

    public void getResponse(final List<HoHlWeightDataBody> list)
    {
        HoHlInsertRequest request = new HoHlInsertRequest();

        HeadOnWeightmentResponse response = getInitialData();

        request.setEmpId(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.EMPLOYEE_ID));
        request.setLotNo(response.getLotNo());
        request.setMaterialGroup(response.getMaterialGroup());
        request.setVarietyName(response.getVarietyName());
        request.setList(list);
        request.setGrandTotal(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.TOTAL_WEIGHT_OF_HEAD_ON_HEAD_LESS_FOR_ALL_COUNT));

        IsServiceApi serviceApi = mRetrofit.create(IsServiceApi.class);
        observable = serviceApi.getResponse(request);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<HeadOnInsertResponse>() {
                    @Override
                    public void onNext(HeadOnInsertResponse value) {
                        if (listener != null){
                            listener.onHOHLWeightSuccess(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null){
                            listener.onHOHLWeightFailure(e);
                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private HeadOnWeightmentResponse getInitialData() {

        Gson gson = new Gson();
        SharedPrefrencesData prefrencesData = SharedPrefrencesData.newInstance();
        HeadOnWeightmentResponse response = null;
        response = gson.fromJson(prefrencesData.getString(mContext, SharedPrefrencesData.HEAD_ON_HEAD_LESS_WEIGHT), HeadOnWeightmentResponse.class);
        return response;
    }
}
