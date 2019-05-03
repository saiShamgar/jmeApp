package com.servicesprovider.jme.controller;

import android.content.Context;

import com.servicesprovider.jme.model.SharedPrefrencesData;
import com.servicesprovider.jme.model.SiteWeighmentRequest;
import com.servicesprovider.jme.model.SiteWeighmentResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Akash on 4/3/2018.
 */

public class FactoryWeighmentService extends BaseService {

    private Context mContext = null;
    private OnFactoryWeighmentListener mCallback = null;
    private Observable<SiteWeighmentResponse> observable = null;

    public FactoryWeighmentService(Context mContext, OnFactoryWeighmentListener mCallback) {
        this.mContext = mContext;
        this.mCallback = mCallback;
    }

    @Override
    public void cancelRequest() {
        if (observable != null){
            observable = null;
            mCallback = null;
        }
    }

    public interface OnFactoryWeighmentListener{
        void onWeighmentSuccess(SiteWeighmentResponse response);
        void onWeighmentFailure(Throwable error);
    }

    public void getResponse(String scheduleNumber){

        SiteWeighmentRequest request = new SiteWeighmentRequest();
        request.setEmpId(SharedPrefrencesData.newInstance().getString(mContext,SharedPrefrencesData.EMPLOYEE_ID));
        request.setScheduleNumber(scheduleNumber);

        IsServiceApi serviceApi = mRetrofit.create(IsServiceApi.class);
        observable = serviceApi.getFactoryResponse(request);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SiteWeighmentResponse>() {
                    @Override
                    public void onNext(SiteWeighmentResponse value) {
                        if(mCallback != null){
                            mCallback.onWeighmentSuccess(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(mCallback != null){
                            mCallback.onWeighmentFailure(e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
