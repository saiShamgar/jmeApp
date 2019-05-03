package com.servicesprovider.jme.utils;

/**
 * Created by Akash on 3/24/2018.
 */

public class AppConfig {

    public static final String BASE_URL = "http://jmeapp.pg-erp.com/";
   // public static final String BASE_URL =  "phpphp.pg-erp.com/index.php/";

    public static final String METHOD_POST_LOGIN = "User/Login";
    public static final String GET_SHEDULE_NUMBER = "SiteWeighment/GetSiteScheduleNos";
    public static final String METHOD_POST_SITE_WEIGHMENT_DETAILS = "SiteWeighment/GetSiteWeighmentDetails";
    public static final String METHOD_POST_AGENT_DETAILS = "SiteWeighment/Agent_Farmer_Details";
    public static final String METHOD_POST_INSERT_SITE_WEIGHMENT = "SiteWeighment/InsertSiteWeighment";

    //FactoryWeightment URLs
    public static final String GET_FACTORY_SHEDULE_NUMBER = "FactoryWeighment/GetFactoryScheduleNos";
    public static final String METHOD_POST_FACTORY_WEIGHMENT_DETAILS = "FactoryWeighment/GetFactoryWeighmentDetails";
    public static final String METHOD_POST_FACTORY_AGENT_DETAILS = "FactoryWeighment/Factory_Agent_Farmer_Details";
    public static final String METHOD_POST_INSERT_FACTORY_WEIGHMENT = "FactoryWeighment/InsertFactoryWeighment";

    public static final String GET_LOT_NUMBER = "HeadonGrading/GetHeadon_Grading_LotNos";
    public static final String GET_HL_LOT_NUMBER = "HeadlessGrading/GetHeadless_Grading_LotNos";
    public static final String GET_HO_HL_LOT_NUMBER = "HeadonHeadless/GetHeadon_Headless_LotNos";



    public static final String METHOD_POST_HEAD_LESS_WEIGHMENT_DETAILS = "HeadlessGrading/GetHeadless_Grading_Details";
    public static final String METHOD_POST_HEAD_ON_WEIGHMENT_DETAILS = "HeadonGrading/GetHeadon_Grading_Details";
    public static final String METHOD_POST_HEAD_LESS_HEAD_ON_WEIGHMENT_DETAILS = "HeadonHeadless/GetHeadon_Headless_Details";

    public static final String METHOD_POST_INSERT_HEAD_ON_WEIGHMENT = "HeadonGrading/Insert_Headon_Grading";
    public static final String METHOD_POST_INSERT_HEAD_LESS_WEIGHMENT = "HeadlessGrading/Insert_Headless_Grading";
    public static final String METHOD_POST_INSERT_HO_HL_WEIGHMENT = "HeadonHeadless/Insert_Headon_Headless_Grading";


}
