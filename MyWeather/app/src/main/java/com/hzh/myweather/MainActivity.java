package com.hzh.myweather;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hzh.myweather.adapter.MainFragmentAdapter;
import com.hzh.myweather.fragment.MainFragment;
import com.hzh.myweather.utils.SPFUtil;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btCityManger;

    ArrayList<String> cityList=new ArrayList<>();
    ViewPager2 viewPager; //UI
    ArrayList<Fragment> fragmentList=new ArrayList<>();  //数据源
    MainFragmentAdapter mainFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPager();
    }

    private void initData() {
    }

    private void initEvent() {
        btCityManger.setOnClickListener(this);
    }

    private void initView() {
        btCityManger=findViewById(R.id.bt_cityManger);
    }

    /**
     * 初始化ui
     */
    private void initPager(){
        viewPager=findViewById(R.id.vp_weather);
        cityList=SPFUtil.queryList(this);  //查询了数据
        Log.d("showCity", "initPager: "+cityList);
        fragmentList.clear();  //清空数据源，避免重复
        for (String s:
             cityList) {
            fragmentList.add(MainFragment.newInstance(s));   //将对应城市的名字传入
        }
        mainFragmentAdapter=new MainFragmentAdapter(getSupportFragmentManager(),getLifecycle(),fragmentList);
        viewPager.setAdapter(mainFragmentAdapter);
        mainFragmentAdapter.refreshData(fragmentList);
    }

    /**
     * 点击事件响应
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_cityManger:
                Intent intent=new Intent(MainActivity.this,CityMangerActivity.class);
                startActivityForResult(intent,1);
                break;
            default:
        }
    }
    /**
     * 接收回传的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("city", "第一个活动接收的：" + data.getStringArrayListExtra("markCity"));
        cityList=data.getStringArrayListExtra("markCity");
        SPFUtil.saveList(this,cityList);  //接收后就保存起来
    }


}