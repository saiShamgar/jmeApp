package com.servicesprovider.jme.controller;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.servicesprovider.jme.model.HeadLessInsertRequest;
import com.servicesprovider.jme.model.HeadLessWeightDataBody;
import com.servicesprovider.jme.model.HeadOnInsertRequest;
import com.servicesprovider.jme.model.HeadOnInsertResponse;
import com.servicesprovider.jme.model.HeadOnWeightDataBody;
import com.servicesprovider.jme.model.HeadOnWeightmentResponse;
import com.servicesprovider.jme.model.SharedPrefrencesData;
import com.servicesprovider.jme.model.WeightDataBody;
import com.servicesprovider.jme.utils.AppConstant;
import com.servicesprovider.jme.utils.AppUtils;

import java.net.ContentHandler;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by muheeb on 04-Apr-18.
 */

public class HeadOnWeightInsertService extends BaseService {
    private Context mContext;
    OnHeadOnInsertListener listener = null;
    private Observable<HeadOnInsertResponse> observable = null;
    int fromActivity;

    public HeadOnWeightInsertService(Context mContext, int fromActivity, OnHeadOnInsertListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        this.fromActivity = fromActivity;
    }

    public interface OnHeadOnInsertListener {
        void onHeadOnWeightSuccess(HeadOnInsertResponse response);

        void onHeadOnWeightFailure(Throwable error);
    }

    @Override
    public void cancelRequest() {
        if (observable != null) {
            listener = null;
            observable = null;
        }
    }

    public void getResponse(final List<HeadOnWeightDataBody> list) {
        HeadOnInsertRequest request = new HeadOnInsertRequest();

        HeadOnWeightmentResponse response = getInitialData();
        Log.i("responseis", String.valueOf(response));

        request.setEmpId(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.EMPLOYEE_ID));
        request.setLotNo(response.getLotNo());
        request.setMaterialGroup(response.getMaterialGroup());
        request.setVarietyName(response.getVarietyName());
        request.setList(list);
        request.setGrantTotal(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.TOTAL_WEIGHT_OF_HEAD_ON_FOR_ALL_COUNT));

        IsServiceApi serviceApi = mRetrofit.create(IsServiceApi.class);
        observable = serviceApi.getResponse(request);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<HeadOnInsertResponse>() {
                    @Override
                    public void onNext(HeadOnInsertResponse value) {
                        if (listener != null) {
                            listener.onHeadOnWeightSuccess(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onHeadOnWeightFailure(e);
                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getResponseHl(final List<HeadLessWeightDataBody> list) {
        HeadLessInsertRequest request = new HeadLessInsertRequest();

        HeadOnWeightmentResponse response = getInitialData();

        request.setEmpId(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.EMPLOYEE_ID));
        request.setLotNo(response.getLotNo());
        request.setMaterialGroup(response.getMaterialGroup());
        request.setVarietyName(response.getVarietyName());
        request.setList(list);
        request.setGrantTotal(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.TOTAL_WEIGHT_OF_HEAD_ON_FOR_ALL_COUNT));

        IsServiceApi serviceApi = mRetrofit.create(IsServiceApi.class);
        observable = serviceApi.getResponse(request);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<HeadOnInsertResponse>() {
                    @Override
                    public void onNext(HeadOnInsertResponse value) {
                        if (listener != null) {
                            listener.onHeadOnWeightSuccess(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onHeadOnWeightFailure(e);
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
        if (fromActivity == AppConstant.FLAG_FROM_HEAD_ON)
            response = gson.fromJson(prefrencesData.getString(mContext, SharedPrefrencesData.HEAD_ON_WEIGHT), HeadOnWeightmentResponse.class);
        else if (fromActivity == AppConstant.FLAG_FROM_HEAD_ON_MANUAL)
            response = gson.fromJson(prefrencesData.getString(mContext, SharedPrefrencesData.HEAD_ON_WEIGHT), HeadOnWeightmentResponse.class);
        else if (fromActivity == AppConstant.FLAG_FROM_HEAD_LESS)
            response = gson.fromJson(prefrencesData.getString(mContext, SharedPrefrencesData.HEAD_LESS_WEIGHT), HeadOnWeightmentResponse.class);
        else if (fromActivity == AppConstant.FLAG_FROM_HEAD_LESS_MANUAL)
            response = gson.fromJson(prefrencesData.getString(mContext, SharedPrefrencesData.HEAD_LESS_WEIGHT), HeadOnWeightmentResponse.class);

        return response;
    }
}
