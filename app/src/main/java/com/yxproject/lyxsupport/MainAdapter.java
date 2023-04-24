package com.yxproject.lyxsupport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.yxproject.application_data.AppData;

import java.util.ArrayList;


/**
 * 第一層
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    //Input MainActivity
    private Context context;
    //Initialize variable
    private ArrayList<String> viewtype; //View type

    private int type_transform = 0; //Position record
    private int rcy_transform = 0; //Position record for ArrayList<RecyclerView> rcy_childitem in ViewHolder
    private int vp2_transform = 0; //Position record for ArrayList<ViewPager2> vp2_childitem in ViewHolder

    public MainAdapter(Context context,String[] viewtypeinput){
        this.context = context;
        viewtype = new ArrayList<String>();
        for (int i = 0; i < viewtypeinput.length; i++) {
            viewtype.add("" + viewtypeinput[i]);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Initial variable
        ArrayList<RecyclerView> rcy_childitem = new ArrayList<RecyclerView>();
        ArrayList<ViewPager2> vp2_childitem = new ArrayList<ViewPager2>();
        ArrayList<TabLayout> tabl_childitem = new ArrayList<TabLayout>();

        // FEATURE
        ArrayList<ViewPager2> vp2_feature_childitem = new ArrayList<ViewPager2>();
        ArrayList<TabLayout> tabl_feature_childitem = new ArrayList<TabLayout>();

        // FORWORK
        ArrayList<ViewPager2> vp2_forwork_childitem = new ArrayList<ViewPager2>();
        ArrayList<TabLayout> tabl_forwork_childitem = new ArrayList<TabLayout>();

        RecyclerView recyclerview_child_pre;
        RecyclerViewItemAdapter recyclerViewItemAdapter;

        //TextView tv_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            //rcy_childitem = new ArrayList<RecyclerView>();

            // 主recyclerview傳入的View裡面的子recyclerview
            switch (viewtype.get(type_transform)) {
                case "FEATURES":
                    //rcy_childitem add nothing
                    vp2_feature_childitem.add((ViewPager2) itemView.findViewById(R.id.vp_main_actfeature));
                    tabl_feature_childitem.add((TabLayout) itemView.findViewById(R.id.tabl_title_actfeature));
                    break;
                case "HOME":
                    rcy_childitem.add((RecyclerView) itemView.findViewById(R.id.rcv_main_acthome));
                    vp2_childitem.add((ViewPager2) itemView.findViewById(R.id.vp_title_acthome));
                    tabl_childitem.add((TabLayout) itemView.findViewById(R.id.tabl_title_acthome));
                    break;
                case "SETTING":
                    //rcy_childitem.add((RecyclerView) itemView.findViewById(R.id.rcv_main_acthome));
                    break;
                case "FORWORK":
                    vp2_forwork_childitem.add((ViewPager2) itemView.findViewById(R.id.vp_main_actforwork));
                    tabl_forwork_childitem.add((TabLayout) itemView.findViewById(R.id.tabl_title_actforwork));
                    break;
            }
            //tv_title = itemView.findViewById(R.id.tv_title);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        //ViewHolder holder;
        final MainAdapter.ViewHolder holder;

        ViewPagerItemAdapter_t1 viewpageritemadapter;

        switch (viewtype.get(type_transform)){
            case"FEATURES":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_t1_featuer, parent, false);
                holder = new ViewHolder(view);

                viewpageritemadapter = new ViewPagerItemAdapter_t1(context, AppData.FEATURE_VIEWTYPE);
                //Set clip padding
                holder.vp2_feature_childitem.get(0).setClipToPadding(false);
                //Set clip children
                holder.vp2_feature_childitem.get(0).setClipChildren(false);
                //Set page limit
                holder.vp2_feature_childitem.get(0).setOffscreenPageLimit(3);
                //Set default start position
                holder.vp2_feature_childitem.get(0).getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
                //Set adapter on vertical viewpaper
                holder.vp2_feature_childitem.get(0).setAdapter(viewpageritemadapter);
                //Set main ViewPagers can slide or not
                holder.vp2_feature_childitem.get(0).setUserInputEnabled(false); //true:allow，false：disallow
                //-----------------------------------------------------------------------------------------------
                // Need to add tablayout events
                //context.connectFeatureTablayoutWithViewpager2(holder.tabl_childitem(0));

                // tablayout event in feature page
                holder.tabl_feature_childitem.get(0).addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        switch(tab.getPosition()) {
                            case 0:
                                holder.vp2_feature_childitem.get(0).setCurrentItem(0, true);
                                break;
                            case 1:
                                holder.vp2_feature_childitem.get(0).setCurrentItem(1, true);
                                break;
                            case 2:
                                holder.vp2_feature_childitem.get(0).setCurrentItem(2, true);
                                break;
                            case 3:
                                holder.vp2_feature_childitem.get(0).setCurrentItem(3, true);
                                break;
                            case 4:
                                holder.vp2_feature_childitem.get(0).setCurrentItem(4, true);
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
                //-----------------------------------------------------------------------------------------------
                type_transform++;
                break;
            case "HOME":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_t1_home, parent, false);
                holder = new ViewHolder(view);

                // 垂直顯示------------------------------------------------------------------------------------------------------------------------------
                LinearLayoutManager childLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
                    @Override
                    public boolean canScrollHorizontally() {
                        return false;
                    }

                    @Override
                    public boolean canScrollVertically() {
                        return true;
                    }
                };
                holder.rcy_childitem.get(0).setLayoutManager(childLayoutManager);
                holder.rcy_childitem.get(0).setAdapter(holder.recyclerViewItemAdapter = new RecyclerViewItemAdapter(context, AppData.TEST_NAME_CN, AppData.TEST_ICON, AppData.TEST_DESCRIPTION,1));

                //holder.rcy_childitem.get(1).setLayoutManager(childLayoutManager);
                //holder.rcy_childitem.get(1).setAdapter(holder.recyclerViewItemAdapter = new RecyclerViewItemAdapter(context, AppData.TEST_NAME_CN, AppData.TEST_ICON, AppData.TEST_DESCRIPTION,1));

                vp2_transform++;
                type_transform++;
                break;
            case "SETTING":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_t1_setting, parent, false);
                holder = new ViewHolder(view);
                type_transform++;
                break;
            case "FORWORK":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_t1_forwork, parent, false);
                holder = new ViewHolder(view);

                viewpageritemadapter = new ViewPagerItemAdapter_t1(context, AppData.FORWORK_VIEWTYPE);
                //Set clip padding
                holder.vp2_forwork_childitem.get(0).setClipToPadding(false);
                //Set clip children
                holder.vp2_forwork_childitem.get(0).setClipChildren(false);
                //Set page limit
                holder.vp2_forwork_childitem.get(0).setOffscreenPageLimit(3);
                //Set default start position
                holder.vp2_forwork_childitem.get(0).getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
                //Set adapter on vertical viewpaper
                holder.vp2_forwork_childitem.get(0).setAdapter(viewpageritemadapter);
                //Set main ViewPagers can slide or not
                holder.vp2_forwork_childitem.get(0).setUserInputEnabled(false); //true:allow，false：disallow
                //-----------------------------------------------------------------------------------------------
                // Need to add tablayout events
                //context.connectFeatureTablayoutWithViewpager2(holder.tabl_childitem(0));

                // tablayout event in feature page
                holder.tabl_forwork_childitem.get(0).addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        switch(tab.getPosition()) {
                            case 0:
                                holder.vp2_forwork_childitem.get(0).setCurrentItem(0, true);
                                break;
                            case 1:
                                holder.vp2_forwork_childitem.get(0).setCurrentItem(1, true);
                                break;
                            case 2:
                                holder.vp2_forwork_childitem.get(0).setCurrentItem(2, true);
                                break;
                            case 3:
                                holder.vp2_forwork_childitem.get(0).setCurrentItem(3, true);
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
                //-----------------------------------------------------------------------------------------------


                type_transform++;
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_error, parent, false);
                holder = new ViewHolder(view);
                type_transform++;
                break;
        }
        //Initialize view
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_home, parent, false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        //Return view

        return holder;
        //return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        //Set image on image view
        //holder.imageView.setBackgroundResource(images[position]);
        //holder.tv_title.setText(texts[position]);
    }

    @Override
    public int getItemCount() {
        //Return array length
        return viewtype.size();
    }
}