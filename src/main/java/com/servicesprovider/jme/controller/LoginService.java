package com.servicesprovider.jme.controller;

import android.content.Context;
import android.util.Log;

import com.servicesprovider.jme.model.LoginRequest;
import com.servicesprovider.jme.model.LoginResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Akash on 3/24/2018.
 */

public class LoginService extends BaseService {

    private Context mContext;
    private OnLoginListener mCallBack = null;
    private Observable<LoginResponse> observable = null;

    public LoginService(Context mContext, OnLoginListener mCallBack) {
        this.mContext = mContext;
        this.mCallBack = mCallBack;
    }

    @Override
    public void cancelRequest() {

        if (observable != null){
            observable = null;
            mCallBack = null;
        }
    }

    public interface OnLoginListener{
        void onLoginSuccess(LoginResponse response);
        void onLoginFailure(Throwable throwable);
    }

    public void getResponse(String userName, String password){

        LoginRequest request = new LoginRequest();
        request.setEmpEmail(userName);
        request.setEmpName(userName);
        request.setEmpPassword(password);
        Log.i("userdetails",String.valueOf(userName)+String.valueOf(password));
        Log.i("userdetails",String.valueOf(request.getEmpName())+String.valueOf(request.getEmpPassword()));
        IsServiceApi serviceApi = mRetrofit.create(IsServiceApi.class);
        observable = serviceApi.getResponse(request);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginResponse>() {
                    @Override
                    public void onNext(LoginResponse value) {
                        if (mCallBack != null){
                            mCallBack.onLoginSuccess(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (mCallBack != null){
                            mCallBack.onLoginFailure(e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        }
    }
