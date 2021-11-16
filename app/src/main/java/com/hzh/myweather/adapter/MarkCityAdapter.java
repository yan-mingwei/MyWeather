package com.hzh.myweather.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.hzh.myweather.Interface.IAdapterDel;
import com.hzh.myweather.R;

import java.util.ArrayList;


public class MarkCityAdapter extends RecyclerView.Adapter<MarkCityAdapter.MarkCityHolder> {
    private IAdapterDel iAdapterDel;

    private ArrayList<String>  markCityList;
    private Context context;

    public MarkCityAdapter(ArrayList<String> markCityList, Context context) {
        this.markCityList = markCityList;
        this.context = context;
    }

    public IAdapterDel getiAdapterDel() {
        return iAdapterDel;
    }

    public void setiAdapterDel(IAdapterDel iAdapterDel) {
        this.iAdapterDel = iAdapterDel;
    }

    @NonNull
    @Override
    public MarkCityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_markcity_layout, parent, false);
        MarkCityHolder markCityHolder=new MarkCityHolder(view);
        return markCityHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MarkCityHolder holder, int position) {
        holder.tvMarkCity.setText(markCityList.get(position));

        holder.tvMarkCity.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(context);
                dialog.setMessage("是否删除此城市");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iAdapterDel.delCity(holder.tvMarkCity.getText().toString().trim());
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return markCityList.size();
    }

    static class MarkCityHolder extends RecyclerView.ViewHolder{
        TextView tvMarkCity;
        public MarkCityHolder(@NonNull View itemView) {
            super(itemView);
            tvMarkCity=itemView.findViewById(R.id.tv_markCity);
        }
    }

    /**
     * 从新加载数据
     */
    public void refreshData(ArrayList<String> cities){
        markCityList=cities;
        notifyDataSetChanged();
    }
}
