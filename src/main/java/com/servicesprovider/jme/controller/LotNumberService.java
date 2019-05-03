package com.servicesprovider.jme.controller;

import android.content.Context;
import android.util.Log;

import com.servicesprovider.jme.model.LotNumberResponse;
import com.servicesprovider.jme.utils.AppConstant;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by muheeb on 02-Apr-18.
 */

public class LotNumberService extends BaseService {
    private Context mContext = null;
    private OnLotNoListener mCallback = null;
    private Observable<LotNumberResponse> observable = null;
    private String TAG = LotNumberService.class.getSimpleName();

    public LotNumberService(Context mContext, OnLotNoListener mCallback) {
        this.mContext = mContext;
        this.mCallback = mCallback;
    }

    public interface OnLotNoListener {
        void onLotSuccess(LotNumberResponse response);

        void onLotFailure(Throwable error);
    }

    @Override
    public void cancelRequest() {
        if (observable != null) {
            observable = null;
            mCallback = null;
        }

    }

    public void getResponse(int fromActivity) {
        IsServiceApi serviceApi = mRetrofit.create(IsServiceApi.class);

        switch (fromActivity) {
            case AppConstant.FLAG_FROM_HEAD_ON:
                observable = serviceApi.getHoLotNumberResponse();
                break;
            case AppConstant.FLAG_FROM_HEAD_ON_MANUAL:
                observable = serviceApi.getHoLotNumberResponse();
                break;

            case AppConstant.FLAG_FROM_HEAD_LESS:
                observable = serviceApi.getHlLotNumberResponse();
                break;

            case AppConstant.FLAG_FROM_HEAD_LESS_MANUAL:
                observable = serviceApi.getHlLotNumberResponse();
                break;

            default:
                observable = serviceApi.getHoHlLotNumberResponse();
                break;
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LotNumberResponse>() {
                    @Override
                    public void onNext(LotNumberResponse value) {
                        if (mCallback != null) {
                            Log.i(TAG, "onNext: " + value.getLotNumberList().toString());
                            mCallback.onLotSuccess(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mCallback != null) {
                            mCallback.onLotFailure(e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
