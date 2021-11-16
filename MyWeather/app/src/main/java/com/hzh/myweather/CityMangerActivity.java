package com.hzh.myweather;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hzh.myweather.Interface.IAdapterDel;
import com.hzh.myweather.adapter.MarkCityAdapter;
import com.hzh.myweather.utils.SPFUtil;
import com.hzh.myweather.utils.TranUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * 依旧需要列表展示数据，同时维护一个城市集合，
 */
public class CityMangerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUESTCODE_SECOND = 11;
    public static final int RESULTCODE_FIRST=1;

    private static final String TAG = "newCity";
    private FloatingActionButton btAddCity;

    HashSet<String> citySet=new HashSet<>();   //数据
    ArrayList<String> markCityList=new ArrayList<>();
    RecyclerView rlvMarkCity;//ui
    MarkCityAdapter markCityAdapter;//适配器

    private String newAddCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manger);
        initView();
        initEvent();
    }

    private void initView() {
        btAddCity=findViewById(R.id.bt_cityAdd);
        rlvMarkCity=findViewById(R.id.rlv_cityMark);
    }

    private void initEvent() {
        btAddCity.setOnClickListener(this);
    }

    private void initData() {
        HashSet<String> lastCitySet= SPFUtil.query(this);  //查询数据
        if(lastCitySet!=null && lastCitySet.size()>0){
            for (String s:
                 lastCitySet) {
                citySet.add(s);
            }
        }
        if(newAddCity!=null){
            citySet.add(newAddCity);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
        bindList();
        markCityAdapter.refreshData(TranUtil.setToList(citySet));  //从新更新数据
    }

    /**
     * 绑定列表
     */
    private void bindList(){
        //set需要转换为list,
        markCityList= TranUtil.setToList(citySet);
        markCityAdapter=new MarkCityAdapter(markCityList,this);
        IAdapterDel iAdapterDel=new IAdapterDel() {
            /**
             * 删除数据，同时也要更新ui
             * @param delCity
             */
            @Override
            public void delCity(String delCity) {
                citySet.remove(delCity);  //这里同时删掉了
                markCityAdapter.refreshData(TranUtil.setToList(citySet));  //从新更新数据
                SPFUtil.save(CityMangerActivity.this,citySet);
            }
        };
        markCityAdapter.setiAdapterDel(iAdapterDel);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rlvMarkCity.setAdapter(markCityAdapter);
        rlvMarkCity.setLayoutManager(linearLayoutManager);
    }


    /**
     * 活动销毁时保存收藏城市集合
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPFUtil.save(this,citySet);
    }

    /**
     * 接收回传的数据,要清楚此方法的调用时机
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUESTCODE_SECOND:
                if(resultCode==AddCityActivity.RESULTCODE_SECOND){
                    //这里需要给集合中添加新值，
                    String newCity=data.getStringExtra("newCity");
                    Log.d("city", "第二个活动接收到的" + newCity);
                   if(newCity!=null){
                       newAddCity=newCity;
                   }
                }
                break;
            default:
        }
    }

    /**
     * 响应点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_cityAdd:
                Intent intent =new Intent(CityMangerActivity.this,AddCityActivity.class);
                startActivityForResult(intent,REQUESTCODE_SECOND);
                break;
            default:
        }
    }

    /**
     * 返回到上一个活动，并回传数据
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();   //这句调用了父方法，会提前结束活动
        Intent intent=new Intent();
        intent.putStringArrayListExtra("markCity",TranUtil.setToList(citySet));
        setResult(RESULT_OK,intent);
        Log.d("city", "返回到第一个活动："+TranUtil.setToList(citySet));
        finish();
    }

}