package com.yxproject.lyxsupport;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MyPaperAdapter extends PagerAdapter {
    private List<View> mPager;
    private int childCount = 0;

    public MyPaperAdapter(List<View> mPager) {
        this.mPager = mPager;
    }

    /*@Override
    public int getItemPosition(@NonNull Object object) {
        if (childCount>0){
            childCount --;
            return POSITION_NONE;
        }
        return  super.getItemPosition(object);
    }*/

    @Override
    public int getCount() {
        return mPager.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mPager.get(position).setTag(position);
        ((ViewPager) container).addView(mPager.get(position));
        return mPager.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public void notifyDataSetChanged() {
        childCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
