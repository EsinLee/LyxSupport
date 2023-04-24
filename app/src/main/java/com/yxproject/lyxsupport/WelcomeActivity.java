package com.yxproject.lyxsupport;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.yxproject.RoomDataBase.DataBase;
import com.yxproject.RoomDataBase.SelfDictionary_entity;
import com.yxproject.lyxsupport.R;

public class WelcomeActivity extends AppCompatActivity {

    private ConstraintLayout act_welcome_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        act_welcome_root = (ConstraintLayout)findViewById(R.id.act_welcome_root);

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

        mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 1000); //多少毫秒跳轉
    }

    public void setMainBgimg(int alpha,int red,int green,int blue){
        act_welcome_root.setBackgroundColor(Color.argb(alpha,red,green,blue));
    }

    private static final int GOTO_MAIN_ACTIVITY = 0;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case GOTO_MAIN_ACTIVITY:
                    Intent intent = new Intent();
                    //將原本Activity的換成MainActivity
                    intent.setClass(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    WelcomeActivity.this.finish();
                    break;

                default:
                    break;
            }
        }

    };
}
