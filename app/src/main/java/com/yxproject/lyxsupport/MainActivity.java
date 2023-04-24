package com.yxproject.lyxsupport;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.facebook.stetho.Stetho;
import com.google.android.material.tabs.TabLayout;
import com.yxproject.RoomDataBase.DataBase;
import com.yxproject.RoomDataBase.SelfDictionary_entity;
import com.yxproject.application_data.AppData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //1.宣告<ViewPager>標籤為viewPager

    //2.宣告使用轉換器
    private BlankFragmentAdapter adapter;

    //3.宣告變數為fragments
    //private Fragment[] fragments;
    private List<Fragment> fragments=new ArrayList<>();

    private MainAdapter mainAdapter;

    private ViewPager2 main_viewPager;
    private ConstraintLayout main_bg_img;
    //用來存放view並傳遞給viewPager的介面卡。
    private ArrayList<View> pageview;

    private TabLayout main_tabLayout;
    //起始頁面
    private int initialize_page = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Notify();
        // Stetho Init
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

        main_tabLayout = (TabLayout) findViewById(R.id.tabl_main_actmain);
        main_viewPager = (ViewPager2) findViewById(R.id.vp_main_actmain);
        main_bg_img = (ConstraintLayout) findViewById(R.id.main_bg_img);


        ////========================================================================================
        // Adapter setting =========================================================================
        //Initialize Mainadapter
        mainAdapter = new MainAdapter(MainActivity.this, AppData.MAIN_VIEWTYPE);

        //6.陣列內容
        fragments.add(new Fragment_T1_Features());
        fragments.add(new Fragment_T1_Home(this));
        fragments.add(new Fragment_T1_Setting(this));
        fragments.add(new Fragment_T1_Forwork(this));

        //7.初始化轉換器
        adapter = new BlankFragmentAdapter(getSupportFragmentManager(),getLifecycle(),fragments);

        //8.<ViewPager>標籤設定轉換器
        //main_viewPager.setAdapter(adapter);

        //Set adapter on vertical viewpaper
        //main_viewPager.setAdapter(mainAdapter);


        //Set clip padding
        main_viewPager.setClipToPadding(false);
        //Set clip children
        main_viewPager.setClipChildren(false);
        //Set page limit
        main_viewPager.setOffscreenPageLimit(3);
        //Set default start position
        main_viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        //Set adapter on vertical viewpaper--------------------------------------------------
        //main_viewPager.setAdapter(mainAdapter);
        main_viewPager.setAdapter(adapter);

        /**=======================================================================================*/
        /**初始化*/
        new Thread(() -> {
            Boolean noneedinit = false;
            noneedinit = DataBase.getInstance(this).getSelfDictionaryDataDao().isRowIsExistByword("bgimg_red_value");
            noneedinit = DataBase.getInstance(this).getSelfDictionaryDataDao().isRowIsExistByword("bgimg_green_value");
            noneedinit = DataBase.getInstance(this).getSelfDictionaryDataDao().isRowIsExistByword("bgimg_blue_value");
            if(!noneedinit){
                SelfDictionary_entity new_data = new SelfDictionary_entity("bgimg_red_value", 100+"", "setting_values", "");
                DataBase.getInstance(this).getSelfDictionaryDataDao().insertDictionaryData(new_data);
                new_data = new SelfDictionary_entity("bgimg_green_value", 100+"", "setting_values", "");
                DataBase.getInstance(this).getSelfDictionaryDataDao().insertDictionaryData(new_data);
                new_data = new SelfDictionary_entity("bgimg_blue_value", 100+"", "setting_values", "");
                DataBase.getInstance(this).getSelfDictionaryDataDao().insertDictionaryData(new_data);
            }

            this.setMainBgimg(100,Integer.valueOf(DataBase.getInstance(this).getSelfDictionaryDataDao().findDictionaryByWords("bgimg_red_value")),Integer.valueOf(DataBase.getInstance(this).getSelfDictionaryDataDao().findDictionaryByWords("bgimg_green_value")),Integer.valueOf(DataBase.getInstance(this).getSelfDictionaryDataDao().findDictionaryByWords("bgimg_blue_value")));
        }).start();
        /**=======================================================================================*/

        //Initialize composite page transform
        CompositePageTransformer transformer = new CompositePageTransformer();
        //Add margin between page
        transformer.addTransformer(new MarginPageTransformer(8));
        //Increase select page size
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float v = 1 - Math.abs(position);
                //Set scale y
                page.setScaleY(0.8f + v * 0.2f);
            }
        });

        //Set page transform
        main_viewPager.setPageTransformer(transformer);
        // Adapter setting =========================================================================
        ////========================================================================================
        // TabLayout button click event setting ====================================================
        //Set main ViewPagers can slide or not
        main_viewPager.setUserInputEnabled(false); //true:allow，false：disallow
        //Initialize PagerVier position
        main_viewPager.setCurrentItem(initialize_page, true);
        //Initialize Tablayout position
        main_tabLayout.getTabAt(initialize_page).select();
        //Tab layout events
        main_tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        main_viewPager.setCurrentItem(0, true);
                        break;
                    case 1:
                        main_viewPager.setCurrentItem(1, true);
                        break;
                    case 2:
                        main_viewPager.setCurrentItem(2, true);
                        break;
                    case 3:
                        main_viewPager.setCurrentItem(3, true);
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
        // TabLayout button click event setting ====================================================
        ////========================================================================================
    }

    public void connectFeatureTablayoutWithViewpager2 (TabLayout a, ViewPager2 b){
        a.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            private ViewPager2 feature_viewPager = (ViewPager2) findViewById(R.id.vp_main_actmain);
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        feature_viewPager.setCurrentItem(0, true);
                        break;
                    case 1:
                        feature_viewPager.setCurrentItem(1, true);
                        break;
                    case 2:
                        feature_viewPager.setCurrentItem(2, true);
                        break;
                    case 3:
                        feature_viewPager.setCurrentItem(3, true);
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
    }

    // new 一個 method
    public void Notify() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.lyx_app_smallicon)
                .setTicker("xx特價")
                .setContentTitle("標題example")
                .setContentText("內容example");

        Notification notification = builder.build();
        notificationManager.cancel(0); // 移除id值為0的通知
        notificationManager.notify(0, notification);
    }

    public void setMainBgimg(int alpha,int red,int green,int blue){
        main_bg_img.setBackgroundColor(Color.argb(alpha,red,green,blue));
    }
}
