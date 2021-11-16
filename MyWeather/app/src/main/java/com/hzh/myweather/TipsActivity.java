package com.hzh.myweather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hzh.myweather.adapter.TipsAdapter;
import com.hzh.myweather.bean.DayWeatherBean;
import com.hzh.myweather.bean.OtherTipsBean;

import java.util.List;

public class TipsActivity extends AppCompatActivity {
    private static final String TAG = "life";
    RecyclerView recyclerView;
    List<OtherTipsBean> DataList;
    TipsAdapter tipsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        initView();
        initData();
        initEvent();
        Log.d(TAG, "onCreate: ");
    }

    private void initEvent() {
        tipsAdapter=new TipsAdapter(this,DataList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(tipsAdapter);
    }

    private void initData() {
        Intent intent=getIntent();
        DayWeatherBean dayWeatherBean= (DayWeatherBean) intent.getSerializableExtra("tips");
        DataList=dayWeatherBean.getTipsBeans();
    }

    private void initView() {
        recyclerView=findViewById(R.id.rlv_1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed: ");
    }
}