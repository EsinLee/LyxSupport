package com.yxproject.lyxsupport;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.yxproject.application_data.AppData;

public class Fragment_T1_Home extends Fragment {
    private Context main_context;
    ViewPager2 vp2_main;
    TabLayout tabl_main;
    RecyclerView rvy_main;
    RecyclerViewItemAdapter recyclerViewItemAdapter;

    ViewPagerItemAdapter_t1 viewpageritemadapter;

    public Fragment_T1_Home(Context context) {
        main_context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.adapter_page_t1_home, container, false);
        // set view elements
        RecyclerView rvy_main = root.findViewById(R.id.rcv_main_acthome);

        // 垂直顯示------------------------------------------------------------------------------------------------------------------------------
        LinearLayoutManager childLayoutManager = new LinearLayoutManager(main_context, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        rvy_main.setLayoutManager(childLayoutManager);
        recyclerViewItemAdapter = new RecyclerViewItemAdapter(main_context, AppData.TEST_NAME_CN, AppData.TEST_ICON, AppData.TEST_DESCRIPTION,1);
        rvy_main.setAdapter(recyclerViewItemAdapter);


        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // view do any works
    }
}
