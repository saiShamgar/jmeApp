package com.servicesprovider.jme.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.servicesprovider.jme.BaseActivity;
import com.servicesprovider.jme.R;
import com.servicesprovider.jme.controller.LoginService;
import com.servicesprovider.jme.model.LoginResponse;
import com.servicesprovider.jme.model.SharedPrefrencesData;
import com.servicesprovider.jme.utils.AppConstant;
import com.servicesprovider.jme.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener,LoginService.OnLoginListener{

    @BindView(R.id.edt_username)
    EditText userName;
    @BindView(R.id.edt_password)
    EditText Password;
    @BindView(R.id.txt_login)
    TextView txt_login;

    private Context mContext;
    private LoginService loginService = null;
    private SharedPrefrencesData sharedPrefrencesData;
    public static String UserEmpId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = LoginActivity.this;

        Log.i( "run: ", "Here 4");

        init();
    }
    void init(){
        sharedPrefrencesData = SharedPrefrencesData.newInstance();
        txt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.txt_login:
//                Intent intent = new Intent(mContext,MenuActivity.class);
//                startActivity(intent);
                callService();
                break;
        }
    }

    /**
     *  call Service
     *
     */
    void callService(){
        if (AppUtils.isNetworkAvailable(mContext)){
            AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading...");
            loginService = new LoginService(mContext,this);
            loginService.getResponse(userName.getText().toString().trim(),Password.getText().toString().trim());

        }else {
            AppUtils.showToast(mContext,getString(R.string.error_network));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (loginService != null){
            loginService.cancelRequest();
        }
    }

    @Override
    public void onLoginSuccess(LoginResponse response) {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);
        if (response != null){
            if (response.getResponseCode() == AppConstant.SUCCESS){
                UserEmpId=response.getEmployeeid();
                Log.i("empid123",UserEmpId);

                sharedPrefrencesData.saveBoolean(mContext, true, SharedPrefrencesData.LOGIN_CHECK);
                sharedPrefrencesData.saveString(mContext, response.getEmployeeName(), SharedPrefrencesData.EMPLOYEE_NAME);
                sharedPrefrencesData.saveString(mContext, response.getEmployeeEmailId(), SharedPrefrencesData.EMPLOYEE_EMAIL_ID);
                sharedPrefrencesData.saveString(mContext, response.getEmployeeid(), SharedPrefrencesData.EMPLOYEE_ID);
                sharedPrefrencesData.saveString(mContext, response.getEmployeeContactNo(), SharedPrefrencesData.EMPLOYEE_PHONE_NUMBER);
//                sharedPrefrencesData.saveString(mContext,AppUtils.getAppVersion(mContext),SharedPrefrencesData.APP_VERSION_NUMBER);
                Intent intent = new Intent(mContext, MenuActivity.class);
                startActivity(intent);
                finish();

            }else {
                AppUtils.showCustomOkDialog(mContext, "", response.getResponseMessage(), "OK", null);
            }
        }  else {
            AppUtils.showCustomOkDialog(mContext,"",getResources().getString(R.string.error_default),"OK",null);
        }
    }

    @Override
    public void onLoginFailure(Throwable throwable) {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);

        AppUtils.showCustomOkDialog(this,
                "",
                getString(R.string.error_default),
                "OK", null);
    }
}
