package com.yxproject.lyxsupport;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Fragment_T2_Forwork_Resources extends Fragment {
    private Context main_context;

    ViewPager2 vp2_database;
    TabLayout tabl_database;

    private BlankFragmentAdapter adapter;
    List<Fragment> fragments=new ArrayList<>();

    // default page
    private  int default_page = 1;

    public Fragment_T2_Forwork_Resources(Context context) {
        main_context = (MainActivity)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.adapter_page_t2_workresourcesmanagement, container, false);
        // set view elements

        // set view elements
        vp2_database = (ViewPager2) root.findViewById(R.id.vp_main_actworkresourcesmanagement);
        tabl_database = (TabLayout) root.findViewById(R.id.tabl_main_actworkresourcesmanagement);
        //6.陣列內容
        fragments.add(new Fragment_T3_Resources_Members(main_context));
        fragments.add(new Fragment_T3_Resources_Dispatch(main_context));
        fragments.add(new Fragment_T3_Resources_Items(main_context));
        fragments.add(new Fragment_T3_Resources_SelfDictionary(main_context));
        //7.初始化轉換器
        adapter = new BlankFragmentAdapter(getFragmentManager(),getLifecycle(),fragments);

        //Set clip padding
        vp2_database.setClipToPadding(false);
        //Set clip children
        vp2_database.setClipChildren(false);
        //Set page limit
        vp2_database.setOffscreenPageLimit(4);
        //Set default start position
        vp2_database.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        //Set adapter on vertical viewpaper
        //main_viewPager.setAdapter(mainAdapter);
        vp2_database.setAdapter(adapter);

        //Set main ViewPagers can slide or not
        vp2_database.setUserInputEnabled(false); //true:allow，false：disallow
        //Initialize PagerVier position
        vp2_database.setCurrentItem(default_page, true);
        //Initialize Tablayout position
        tabl_database.getTabAt(default_page).select();
        //Tab layout events
        tabl_database.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        vp2_database.setCurrentItem(0, true);
                        break;
                    case 1:
                        vp2_database.setCurrentItem(1, true);
                        break;
                    case 2:
                        vp2_database.setCurrentItem(2, true);
                        break;
                    case 3:
                        vp2_database.setCurrentItem(3, true);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // view do any works

    }
}
