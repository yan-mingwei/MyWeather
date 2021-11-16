package com.hzh.myweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hzh.myweather.R;
import com.hzh.myweather.bean.OtherTipsBean;

import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipsViewHolder> {
    Context context;
    List<OtherTipsBean> TipsLists;

    public TipsAdapter(Context context, List<OtherTipsBean> tipsLists) {
        this.context = context;
        TipsLists = tipsLists;
    }

    @NonNull
    @Override
    public TipsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建view
        View view = LayoutInflater.from(context).inflate(R.layout.tips_item_layout, parent, false);
        //viewHolder
        TipsViewHolder tipsViewHolder=new TipsViewHolder(view);
        return tipsViewHolder;
    }
    //赋值
    @Override
    public void onBindViewHolder(@NonNull TipsViewHolder holder, int position) {
        OtherTipsBean otherTipsBean = TipsLists.get(position);
        holder.tv_title.setText(otherTipsBean.getTitle());
        holder.tv_level.setText(otherTipsBean.getLevel());
        holder.tv_desc.setText(otherTipsBean.getDesc());
    }

    @Override
    public int getItemCount() {
        return (TipsLists==null)?0:TipsLists.size();
    }

    class TipsViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title,tv_level,tv_desc;

        public TipsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_level=itemView.findViewById(R.id.tv_level);
            tv_desc=itemView.findViewById(R.id.tv_desc);
        }
    }
}
