package com.servicesprovider.jme.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.servicesprovider.jme.BaseActivity;
import com.servicesprovider.jme.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.txt_button)
    TextView txt_button;
    @BindView(R.id.txt_weight)
    TextView txt_weight;

    private static final int REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);

        // Initializing Toolbar and setting it as the actionbar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            getSupportActionBar().setIcon(R.drawable.ic_menu);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar_title.setText("Get Weight");
        }

        txt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(FirstActivity.this,WeightLoadMachine.class),REQ_CODE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("AkashValue","2: "+data.getStringExtra("weight"));
        if(requestCode == REQ_CODE){
            if (resultCode == Activity.RESULT_OK){
                Log.d("AkashValue","3: "+data.getStringExtra("weight"));
                txt_weight.setText(data.getStringExtra("weight"));
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
