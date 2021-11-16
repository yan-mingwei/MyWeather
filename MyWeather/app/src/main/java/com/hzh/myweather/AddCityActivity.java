package com.hzh.myweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hzh.myweather.Interface.IAdapterCallback;
import com.hzh.myweather.adapter.AllCitiesAdapter;
import com.hzh.myweather.database.CityOpenHelper;
import com.hzh.myweather.utils.XMLUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 展示数据库中存储的所有城市，并提供搜索功能，先查询，没有再存入。
 */
public class AddCityActivity extends AppCompatActivity {
    public static final int RESULTCODE_SECOND=21;

    private static final String TAG = "city";
    private RecyclerView rlvAllCities; //UI
    AllCitiesAdapter allCitiesAdapter; //适配器
    List<String> cityList=new ArrayList<>();  //数据

    CityOpenHelper cityOpenHelper;     //数据库

    private EditText etFind;
    private Layout layoutContainer;
    private Button btFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        btFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityName=etFind.getText().toString().trim();
                cityList=cityOpenHelper.findByCity(cityName);
                allCitiesAdapter.refreshData(cityList);  //更新适配器的数据，从而更新ui
            }
        });

        allCitiesAdapter=new AllCitiesAdapter(this,cityList);
        allCitiesAdapter.setiAdapterCallback(new IAdapterCallback() {

            @Override
            public void sendToActivity(String msg) {
                //这里的回调方法里面可以执行结束活动，并且回传数据
                Intent intent=new Intent();
                intent.putExtra("newCity",msg);
                setResult(RESULTCODE_SECOND,intent);
                Log.d("back", "回传的数据" +msg);
                finish();
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rlvAllCities.setLayoutManager(linearLayoutManager);
        rlvAllCities.setAdapter(allCitiesAdapter);
    }

    private void initData() {
        //先查询是否存在，不存在就创建数据库，并存入数据
        cityOpenHelper=new CityOpenHelper(this);
        cityList=cityOpenHelper.findAll();
        if(cityList.size()==0){
            //存入数据
            ArrayList<String> cityXml= XMLUtil.getCity(this,R.xml.cities);
            for (String city:
                 cityXml) {
                cityOpenHelper.insert(city);
            }
            cityList=cityOpenHelper.findAll(); //再次查询一遍
        }
    }

    private void initView() {
        rlvAllCities=findViewById(R.id.rlv_allCity);
        etFind=findViewById(R.id.et_find);
        btFind=findViewById(R.id.bt_find);
    }

}