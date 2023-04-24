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

public class Fragment_T1_Forwork extends Fragment {
    //Input MainActivity
    private Context main_context;

    ViewPager2 vp2_forwork;
    TabLayout tabl_forwork;


    private BlankFragmentAdapter adapter;
    List<Fragment> fragments=new ArrayList<>();

    public Fragment_T1_Forwork(Context context) {
        main_context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.adapter_page_t1_forwork, container, false);
        // set view elements
        vp2_forwork = (ViewPager2) root.findViewById(R.id.vp_main_actforwork);
        tabl_forwork = (TabLayout) root.findViewById(R.id.tabl_title_actforwork);
        //6.陣列內容
        fragments.add(new Fragment_T2_Forwork_Calendar());
        fragments.add(new Fragment_T2_Forwork_DriveplanOutput(main_context));
        fragments.add(new Fragment_T2_Forwork_WorkplanOutput(main_context));
        fragments.add(new Fragment_T2_Forwork_Resources(main_context));
        fragments.add(new Fragment_T2_Forwork_Workplan(main_context));
        //7.初始化轉換器
        adapter = new BlankFragmentAdapter(getFragmentManager(),getLifecycle(),fragments);

        //Set clip padding
        vp2_forwork.setClipToPadding(false);
        //Set clip children
        vp2_forwork.setClipChildren(false);
        //Set page limit
        vp2_forwork.setOffscreenPageLimit(3);
        //Set default start position
        vp2_forwork.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        //Set adapter on vertical viewpaper
        //main_viewPager.setAdapter(mainAdapter);
        vp2_forwork.setAdapter(adapter);


        // TabLayout button click event setting ====================================================
        //Set main ViewPagers can slide or not
        vp2_forwork.setUserInputEnabled(false); //true:allow，false：disallow
        int current_page = 1;
        //Initialize PagerVier position
        vp2_forwork.setCurrentItem(current_page, true);
        //Initialize Tablayout position
        tabl_forwork.getTabAt(current_page).select();
        //Tab layout events
        tabl_forwork.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        vp2_forwork.setCurrentItem(0, true);
                        break;
                    case 1:
                        vp2_forwork.setCurrentItem(1, true);
                        break;
                    case 2:
                        vp2_forwork.setCurrentItem(2, true);
                        break;
                    case 3:
                        vp2_forwork.setCurrentItem(3, true);
                        break;
                    case 4:
                        vp2_forwork.setCurrentItem(4, true);
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
