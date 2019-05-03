package com.servicesprovider.jme.controller;

import android.content.Context;
import android.util.Log;

import com.servicesprovider.jme.model.AgentDetailsResponse;
import com.servicesprovider.jme.model.AgentDetailsResquest;
import com.servicesprovider.jme.model.SharedPrefrencesData;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Akash on 3/26/2018.
 */

public class AgentDetailsService extends BaseService {

    private Context mContext = null;
    private OnAgentdetailsListener mCallback = null;
    private Observable<AgentDetailsResponse> observable = null;

    public AgentDetailsService(Context mContext, OnAgentdetailsListener mCallback) {
        this.mContext = mContext;
        this.mCallback = mCallback;
    }


    public interface OnAgentdetailsListener {
        void onAgentSuccess(AgentDetailsResponse response);

        void onAgentFailure(Throwable error);
    }

    @Override
    public void cancelRequest() {

        if (observable != null) {
            observable = null;
            mCallback = null;
        }
    }

    public void getResponse(String enquiryNumber, String status) {
        AgentDetailsResquest request = new AgentDetailsResquest();
        request.setEnquiryNumber(enquiryNumber);
        request.setEmpId(SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.EMPLOYEE_ID));
        Log.i("details", String.valueOf(enquiryNumber + "," + SharedPrefrencesData.newInstance().getString(mContext, SharedPrefrencesData.EMPLOYEE_ID)));
        IsServiceApi serviceApi = mRetrofit.create(IsServiceApi.class);

        switch (status) {
            case "1":
                observable = serviceApi.getResponse(request);
                Log.d("Status", "site side " + status);
                break;
            case "2":
                Log.d("Status", "Factory side " + status);
                observable = serviceApi.getFactotoryAgentResponse(request);
                break;
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<AgentDetailsResponse>() {
                    @Override
                    public void onNext(AgentDetailsResponse value) {

                        if (mCallback != null) {
                            mCallback.onAgentSuccess(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (mCallback != null) {
                            mCallback.onAgentFailure(e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
