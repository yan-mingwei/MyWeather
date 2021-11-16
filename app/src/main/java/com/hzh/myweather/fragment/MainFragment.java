package com.hzh.myweather.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hzh.myweather.MainActivity;
import com.hzh.myweather.R;
import com.hzh.myweather.TipsActivity;
import com.hzh.myweather.adapter.FutureWeatherAdapter;
import com.hzh.myweather.bean.DayWeatherBean;
import com.hzh.myweather.bean.WeatherBean;
import com.hzh.myweather.utils.NetUtil;
import com.hzh.myweather.utils.getImgUtil;

import java.util.List;

/**
 * 这个很难
 */
public class MainFragment extends Fragment {
    private static final String ARG_PARAM1 = "city";
    private String city;

    View rootView;

    private TextView tvCity;
    private TextView tvWeather,tvTem,tvTemLowHigh,tvWin,tvAir;
    private ImageView ivWeather;
    List<DayWeatherBean> dayWeathers;  //总数据

    private RecyclerView rlvFutureWeather;  //未来天气ui
    private FutureWeatherAdapter mWeatherAdapter;  //未来天气适配器
    private DayWeatherBean todayWeather;  //当天数据


    public MainFragment() {
    }

    public static MainFragment newInstance(String city) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, city);
        fragment.setArguments(args);    //fragment中保存参数的方法
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView==null){
          rootView=  inflater.inflate(R.layout.fragment_main_layout, container, false);
        }
        initView();
        initEvent();
        getWeatherOfCity(city);
        return rootView;
    }

    /**
     * 给ui控件添加事件
     */
    private void initEvent() {
        tvAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), TipsActivity.class);
                intent.putExtra("tips",todayWeather);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        //其它
        tvCity=rootView.findViewById(R.id.tv_city);
        tvAir=rootView.findViewById(R.id.tv_air);
        tvWeather=rootView.findViewById(R.id.tv_weather);
        tvWin=rootView.findViewById(R.id.tv_win);
        tvTem=rootView.findViewById(R.id.tv_tem);
        tvTemLowHigh=rootView.findViewById(R.id.tv_tem_low_high);
        ivWeather=rootView.findViewById(R.id.iv_weather);
        rlvFutureWeather=rootView.findViewById(R.id.rlv_future_weather);
    }

    /**
     * 访问网络后，自动调用的方法，在这里更新ui
     */
    private Handler mHandler=new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                String weather= (String) msg.obj;
                Gson gson=new Gson();
                WeatherBean weatherBean=gson.fromJson(weather,WeatherBean.class);
                updateUiOfWeather(weatherBean);    //更新ui
            }
        }
    };

    /**
     * 开启子线程，网络访问数据
     * @param selectedCity
     */
    private void getWeatherOfCity(String selectedCity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String weatherOfCity= NetUtil.getWeatherOfCity(selectedCity);
                Message message=Message.obtain();
                message.what= 0;   //标志消息符
                message.obj=weatherOfCity;
                mHandler.sendMessage(message);
            }
        }).start();
        Log.d("abc", " 传递的数据:"+ selectedCity);
    }

    /**
     * 将数据渲染到ui
     * @param weatherBean
     */
    private void updateUiOfWeather(WeatherBean weatherBean) {
        if(weatherBean==null){
            return;
        }
        dayWeathers=weatherBean.getDayWeathers();
        todayWeather = dayWeathers.get(0);
        if (todayWeather == null) {
            return;
        }
        tvCity.setText(city);
        tvTem.setText(todayWeather.getTem());
        tvWeather.setText(todayWeather.getWea()+"("+todayWeather.getDate()+todayWeather.getWeek()+")");
        tvTemLowHigh.setText(todayWeather.getTem2() + "~" + todayWeather.getTem1());
        tvWin.setText(todayWeather.getWin()[0] + todayWeather.getWinSpeed());
        tvAir.setText("空气:" + todayWeather.getAir() + todayWeather.getAirLevel() + "\n" + todayWeather.getAirTips());
        ivWeather.setImageResource(getImgUtil.getImgResOfWeather(todayWeather.getWeaImg()));

        dayWeathers.remove(0);//去掉第一天的数据
        mWeatherAdapter=new FutureWeatherAdapter(getActivity(),dayWeathers);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        rlvFutureWeather.setLayoutManager(linearLayoutManager);
        rlvFutureWeather.setAdapter(mWeatherAdapter);
    }

}