package com.servicesprovider.jme.controller;

import android.content.Context;

import com.servicesprovider.jme.model.HeadOnWeightmentRequest;
import com.servicesprovider.jme.model.HeadOnWeightmentResponse;
import com.servicesprovider.jme.utils.AppConstant;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by muheeb on 02-Apr-18.
 */

public class HeadOnWeightmentService extends BaseService {
    private Context mContext = null;
    private OnFactoryWeightmentListener mCallback = null;
    private Observable<HeadOnWeightmentResponse> observable = null;

    public HeadOnWeightmentService(Context mContext, OnFactoryWeightmentListener mCallback) {
        this.mContext = mContext;
        this.mCallback = mCallback;
    }

    @Override
    public void cancelRequest() {

    }

    public interface OnFactoryWeightmentListener {
        void onWeighmentSuccess(HeadOnWeightmentResponse response);

        void onWeighmentFailure(Throwable error);
    }

    public void getResponse(String lotNo, int fromStatus) {
        HeadOnWeightmentRequest request = new HeadOnWeightmentRequest();
        request.setLotNo(lotNo);
        IsServiceApi serviceApi = mRetrofit.create(IsServiceApi.class);

        switch (fromStatus) {
            case AppConstant.FLAG_FROM_HEAD_ON:
                observable = serviceApi.getHoResponse(request);
                break;

            case AppConstant.FLAG_FROM_HEAD_ON_MANUAL:
                observable = serviceApi.getHoResponse(request);
                break;

            case AppConstant.FLAG_FROM_HEAD_LESS:
                observable = serviceApi.getHlResponse(request);
                break;

            case AppConstant.FLAG_FROM_HEAD_LESS_MANUAL:
                observable = serviceApi.getHlResponse(request);
                break;

            default:
                observable = serviceApi.getHoHlResponse(request);
                break;

        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<HeadOnWeightmentResponse>() {
                    @Override
                    public void onNext(HeadOnWeightmentResponse value) {
                        if (mCallback != null) {
                            mCallback.onWeighmentSuccess(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mCallback != null) {
                            mCallback.onWeighmentFailure(e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
