package com.servicesprovider.jme.controller;

import com.servicesprovider.jme.model.AgentDetailsResponse;
import com.servicesprovider.jme.model.AgentDetailsResquest;
import com.servicesprovider.jme.model.HeadLessInsertRequest;
import com.servicesprovider.jme.model.HeadOnInsertRequest;
import com.servicesprovider.jme.model.HeadOnInsertResponse;
import com.servicesprovider.jme.model.HeadOnWeightmentRequest;
import com.servicesprovider.jme.model.HeadOnWeightmentResponse;
import com.servicesprovider.jme.model.HoHlInsertRequest;
import com.servicesprovider.jme.model.InsertSiteWeightmentRequest;
import com.servicesprovider.jme.model.InsertSiteWeightmentResponse;
import com.servicesprovider.jme.model.LoginRequest;
import com.servicesprovider.jme.model.LoginResponse;
import com.servicesprovider.jme.model.LotNumberResponse;
import com.servicesprovider.jme.model.ScheduleResponse;
import com.servicesprovider.jme.model.SiteWeighmentRequest;
import com.servicesprovider.jme.model.SiteWeighmentResponse;
import com.servicesprovider.jme.utils.AppConfig;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Akash on 3/24/2018.
 */

public interface IsServiceApi {

    @POST(AppConfig.METHOD_POST_LOGIN)
    Observable<LoginResponse> getResponse(@Body LoginRequest request);

    @POST(AppConfig.GET_SHEDULE_NUMBER)
    Observable<ScheduleResponse> getScheduleResponse(@Body ScheduleResponse response);

    /*@GET(AppConfig.GET_SHEDULE_NUMBER)
    Observable<ScheduleResponse> getScheduleResponse();*/

    @POST(AppConfig.GET_FACTORY_SHEDULE_NUMBER)
    Observable<ScheduleResponse> getFactoryScheduleResponse(@Body ScheduleResponse response);

    /*@GET(AppConfig.GET_FACTORY_SHEDULE_NUMBER)
    Observable<ScheduleResponse> getFactoryScheduleResponse();*/

    @POST(AppConfig.METHOD_POST_SITE_WEIGHMENT_DETAILS)
    Observable<SiteWeighmentResponse> getResponse(@Body SiteWeighmentRequest request);

    @POST(AppConfig.METHOD_POST_FACTORY_WEIGHMENT_DETAILS)
    Observable<SiteWeighmentResponse> getFactoryResponse(@Body SiteWeighmentRequest request);

    @POST(AppConfig.METHOD_POST_AGENT_DETAILS)
    Observable<AgentDetailsResponse> getResponse(@Body AgentDetailsResquest request);


    //Factory Weightment
    @GET(AppConfig.GET_LOT_NUMBER)
    Observable<LotNumberResponse> getHoLotNumberResponse();

    @GET(AppConfig.GET_HL_LOT_NUMBER)
    Observable<LotNumberResponse> getHlLotNumberResponse();

    @GET(AppConfig.GET_HO_HL_LOT_NUMBER)
    Observable<LotNumberResponse> getHoHlLotNumberResponse();

    @POST(AppConfig.METHOD_POST_HEAD_ON_WEIGHMENT_DETAILS)
    Observable<HeadOnWeightmentResponse> getHoResponse(@Body HeadOnWeightmentRequest request);

    @POST(AppConfig.METHOD_POST_HEAD_LESS_WEIGHMENT_DETAILS)
    Observable<HeadOnWeightmentResponse> getHlResponse(@Body HeadOnWeightmentRequest request);

    @POST(AppConfig.METHOD_POST_HEAD_LESS_HEAD_ON_WEIGHMENT_DETAILS)
    Observable<HeadOnWeightmentResponse> getHoHlResponse(@Body HeadOnWeightmentRequest request);

    @POST(AppConfig.METHOD_POST_INSERT_HEAD_ON_WEIGHMENT)
    Observable<HeadOnInsertResponse> getResponse(@Body HeadOnInsertRequest request);

    @POST(AppConfig.METHOD_POST_INSERT_HEAD_LESS_WEIGHMENT)
    Observable<HeadOnInsertResponse> getResponse(@Body HeadLessInsertRequest request);

    @POST(AppConfig.METHOD_POST_INSERT_HO_HL_WEIGHMENT)
    Observable<HeadOnInsertResponse> getResponse(@Body HoHlInsertRequest request);

    @POST(AppConfig.METHOD_POST_FACTORY_AGENT_DETAILS)
    Observable<AgentDetailsResponse> getFactotoryAgentResponse(@Body AgentDetailsResquest request);


    @POST(AppConfig.METHOD_POST_INSERT_SITE_WEIGHMENT)
    Observable<InsertSiteWeightmentResponse> getResponse(@Body InsertSiteWeightmentRequest request);

    @POST(AppConfig.METHOD_POST_INSERT_FACTORY_WEIGHMENT)
    Observable<InsertSiteWeightmentResponse> getFactoryResponse(@Body InsertSiteWeightmentRequest request);



}
