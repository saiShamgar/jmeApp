package com.servicesprovider.jme.controller;

import android.content.Context;
import android.util.Log;

import com.servicesprovider.jme.model.ScheduleResponse;
import com.servicesprovider.jme.model.SharedPrefrencesData;
import com.servicesprovider.jme.view.LoginActivity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Akash on 3/26/2018.
 */

public class ScheduleNumberService extends BaseService {

    private Context mContext = null;
    private OnScheduleListener mCallback = null;
    private Observable<ScheduleResponse> observable = null;
    private SharedPrefrencesData sharedPrefrencesData = SharedPrefrencesData.newInstance();

    public ScheduleNumberService(Context mContext, OnScheduleListener mCallback) {
        this.mContext = mContext;
        this.mCallback = mCallback;
    }

    public interface OnScheduleListener{
        void onScheduleSuccess(ScheduleResponse response);
        void onScheduleFailure(Throwable error);
    }

    @Override
    public void cancelRequest() {
        if (observable != null){
            observable = null;
            mCallback = null;
        }
    }

    public void getResponse(){
        ScheduleResponse scheduleResponse=new ScheduleResponse();
        String tempEmpId=sharedPrefrencesData.getString(mContext,SharedPrefrencesData.EMPLOYEE_ID);
        Log.i("tempData",String.valueOf(sharedPrefrencesData.getString(mContext,SharedPrefrencesData.EMPLOYEE_ID)) );
        scheduleResponse.setEmployeeid(tempEmpId);
        IsServiceApi serviceApi = mRetrofit.create(IsServiceApi.class);
        observable = serviceApi.getScheduleResponse(scheduleResponse);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ScheduleResponse>() {
                    @Override
                    public void onNext(ScheduleResponse value) {
                        if (mCallback != null){
                            mCallback.onScheduleSuccess(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mCallback != null){
                            mCallback.onScheduleFailure(e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
