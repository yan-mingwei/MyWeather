package com.hzh.myweather.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentAdapter extends FragmentStateAdapter {

    ArrayList<Fragment>  fragmentList;

    public MainFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,ArrayList<Fragment>  fragmentList) {
        super(fragmentManager, lifecycle);
        this.fragmentList=fragmentList;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    /**
     * 更新数据源
     * @param cities
     */
    public void refreshData(ArrayList<Fragment> cities){
        fragmentList=cities;
        notifyDataSetChanged();
    }
}
