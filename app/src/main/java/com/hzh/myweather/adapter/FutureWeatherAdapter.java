package com.hzh.myweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hzh.myweather.R;
import com.hzh.myweather.bean.DayWeatherBean;
import com.hzh.myweather.utils.getImgUtil;

import java.util.List;


public class FutureWeatherAdapter extends RecyclerView.Adapter<FutureWeatherAdapter.WeatherViewHolder> {

    private Context mContext;
    private List<DayWeatherBean> mWeatherBeans;

    public FutureWeatherAdapter(Context context, List<DayWeatherBean> weatherBeans) {
        mContext = context;
        this.mWeatherBeans = weatherBeans;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.weather_item_layout, parent, false);
        WeatherViewHolder weatherViewHolder = new WeatherViewHolder(view);
        return weatherViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        DayWeatherBean weatherBean = mWeatherBeans.get(position);

        holder.tvWeather.setText(weatherBean.getWea());
        holder.tvTem.setText(weatherBean.getTem());
        holder.tvTemLowHigh.setText(weatherBean.getTem2() + "~" + weatherBean.getTem1());
        holder.tvWin.setText(weatherBean.getWin()[0] + weatherBean.getWinSpeed());
        holder.tvAir.setText("空气:" + weatherBean.getAir() + weatherBean.getAirLevel());
        holder.ivWeather.setImageResource(getImgUtil.getImgResOfWeather(weatherBean.getWeaImg()));

        holder.tvDate.setText(weatherBean.getDate());
    }

    @Override
    public int getItemCount() {

        return (mWeatherBeans == null) ? 0 : mWeatherBeans.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        TextView tvWeather, tvTem, tvTemLowHigh, tvWin, tvAir;
        TextView tvDate;
        ImageView ivWeather;
        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWeather = itemView.findViewById(R.id.tv_weather);
            tvAir = itemView.findViewById(R.id.tv_air);
            tvTem = itemView.findViewById(R.id.tv_tem);
            tvTemLowHigh = itemView.findViewById(R.id.tv_tem_low_high);
            tvWin = itemView.findViewById(R.id.tv_win);
            ivWeather = itemView.findViewById(R.id.iv_weather);

            tvDate=itemView.findViewById(R.id.tv_date);
        }
    }

}
