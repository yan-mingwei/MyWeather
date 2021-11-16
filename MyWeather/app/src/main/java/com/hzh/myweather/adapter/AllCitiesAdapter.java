package com.hzh.myweather.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.hzh.myweather.AddCityActivity;
import com.hzh.myweather.CityMangerActivity;
import com.hzh.myweather.Interface.IAdapterCallback;
import com.hzh.myweather.R;

import java.util.List;
import java.util.Set;

public class AllCitiesAdapter extends RecyclerView.Adapter<AllCitiesAdapter.MyViewHolder>{
    IAdapterCallback iAdapterCallback;  //接口
    Context context;
    private List<String> citiesList;

    public AllCitiesAdapter(Context context, List<String> citiesList) {
        this.context = context;
        this.citiesList = citiesList;
    }

    public IAdapterCallback getiAdapterCallback() {
        return iAdapterCallback;
    }

    public void setiAdapterCallback(IAdapterCallback iAdapterCallback) {
        this.iAdapterCallback = iAdapterCallback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_allcity_layout, parent, false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvAllCity.setText(citiesList.get(position));
        holder.tvAllCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //回传数据,弹出一个对话框，
                AlertDialog.Builder dialog=new AlertDialog.Builder(context);
                dialog.setMessage("是否添加此城市");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newCity=holder.tvAllCity.getText().toString().trim();
                        iAdapterCallback.sendToActivity(newCity);
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvAllCity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAllCity=itemView.findViewById(R.id.tv_allCity);
        }
    }

    //从新加载数据
    public void refreshData(List<String> cities){
        citiesList=cities;
        notifyDataSetChanged();
    }
}
