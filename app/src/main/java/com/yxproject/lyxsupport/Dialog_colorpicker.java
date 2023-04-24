package com.yxproject.lyxsupport;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.yxproject.RoomDataBase.DataBase;
import com.yxproject.RoomDataBase.SelfDictionary_entity;

import java.util.ArrayList;
import java.util.List;


public class Dialog_colorpicker implements DialogInterface.OnCancelListener, View.OnClickListener{

    private Context main_context;
    private int mResId;

    private Dialog dialog_colorpicker;

    private ImageView iv_dialog_colorpicker_show;
    private SeekBar seekBar_dialog_colorpicker_red;
    private TextView tv_dialog_colorpicker_red_value;
    private SeekBar seekBar_dialog_colorpicker_green;
    private TextView tv_dialog_colorpicker_green_value;
    private SeekBar seekBar_dialog_colorpicker_blue;
    private TextView tv_dialog_colorpicker_blue_value;
    private Button btn_dialog_colorpicker_submit;

    //顏色設定資料庫讀取暫存區
    private int bgimg_red_value = 300;
    private int bgimg_green_value = 300;
    private int bgimg_blue_value = 300;

    private MainActivity mActivity;

    public Dialog_colorpicker(Context context){
        this.main_context = context;
    }

    public Dialog_colorpicker show(){
        dialog_colorpicker = new Dialog(main_context);
        dialog_colorpicker.setContentView(R.layout.dialog_colorpicker);
        //dialog_colorpicker.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        dialog_colorpicker.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

        // 點邊取消
        dialog_colorpicker.setCancelable(true);
        dialog_colorpicker.setCanceledOnTouchOutside(true);

        mActivity = (MainActivity) main_context;

        iv_dialog_colorpicker_show = (ImageView) dialog_colorpicker.findViewById(R.id.iv_dialog_colorpicker_show);
        seekBar_dialog_colorpicker_red = (SeekBar) dialog_colorpicker.findViewById(R.id.seekBar_dialog_colorpicker_red);
        tv_dialog_colorpicker_red_value = (TextView) dialog_colorpicker.findViewById(R.id.tv_dialog_colorpicker_red_value);
        seekBar_dialog_colorpicker_green = (SeekBar) dialog_colorpicker.findViewById(R.id.seekBar_dialog_colorpicker_green);
        tv_dialog_colorpicker_green_value = (TextView) dialog_colorpicker.findViewById(R.id.tv_dialog_colorpicker_green_value);
        seekBar_dialog_colorpicker_blue = (SeekBar) dialog_colorpicker.findViewById(R.id.seekBar_dialog_colorpicker_blue);
        tv_dialog_colorpicker_blue_value = (TextView) dialog_colorpicker.findViewById(R.id.tv_dialog_colorpicker_blue_value);
        btn_dialog_colorpicker_submit = (Button) dialog_colorpicker.findViewById(R.id.btn_dialog_colorpicker_submit);

        iv_dialog_colorpicker_show.setBackgroundColor(Color.argb(100,seekBar_dialog_colorpicker_red.getProgress(),seekBar_dialog_colorpicker_green.getProgress(),seekBar_dialog_colorpicker_blue.getProgress()));

        /**=======================================================================================*/
        /**初始化*/
        new Thread(() -> {
            Boolean noneedinit = false;
            noneedinit = DataBase.getInstance(main_context).getSelfDictionaryDataDao().isRowIsExistByword("bgimg_red_value");
            noneedinit = DataBase.getInstance(main_context).getSelfDictionaryDataDao().isRowIsExistByword("bgimg_green_value");
            noneedinit = DataBase.getInstance(main_context).getSelfDictionaryDataDao().isRowIsExistByword("bgimg_blue_value");
            if(!noneedinit){
                SelfDictionary_entity new_data = new SelfDictionary_entity("bgimg_red_value", 100+"", "setting_values", "");
                DataBase.getInstance(main_context).getSelfDictionaryDataDao().insertDictionaryData(new_data);
                new_data = new SelfDictionary_entity("bgimg_green_value", 100+"", "setting_values", "");
                DataBase.getInstance(main_context).getSelfDictionaryDataDao().insertDictionaryData(new_data);
                new_data = new SelfDictionary_entity("bgimg_blue_value", 100+"", "setting_values", "");
                DataBase.getInstance(main_context).getSelfDictionaryDataDao().insertDictionaryData(new_data);
            }

            this.bgimg_red_value = Integer.valueOf(DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryByWords("bgimg_red_value"));
            this.bgimg_green_value = Integer.valueOf(DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryByWords("bgimg_green_value"));
            this.bgimg_blue_value = Integer.valueOf(DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryByWords("bgimg_blue_value"));

            System.out.println("--->"+bgimg_red_value+"-"+bgimg_green_value+"-"+bgimg_blue_value);

            //初始化UI介面
            seekBar_dialog_colorpicker_red.setProgress(bgimg_red_value);
            seekBar_dialog_colorpicker_green.setProgress(bgimg_green_value);
            seekBar_dialog_colorpicker_blue.setProgress(bgimg_blue_value);
            /*tv_dialog_colorpicker_red_value.setText(bgimg_red_value);
            tv_dialog_colorpicker_green_value.setText(bgimg_green_value);
            tv_dialog_colorpicker_blue_value.setText(bgimg_blue_value);*/
        }).start();

        iv_dialog_colorpicker_show.setBackgroundColor(Color.argb(100,bgimg_red_value,bgimg_green_value,bgimg_blue_value));
        /**=======================================================================================*/

        //紅
        seekBar_dialog_colorpicker_red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                tv_dialog_colorpicker_red_value.setText(progress+"");
                iv_dialog_colorpicker_show.setBackgroundColor(Color.argb(100,seekBar_dialog_colorpicker_red.getProgress(),seekBar_dialog_colorpicker_green.getProgress(),seekBar_dialog_colorpicker_blue.getProgress()));
            }
        });
        //綠
        seekBar_dialog_colorpicker_green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                tv_dialog_colorpicker_green_value.setText(progress+"");
                iv_dialog_colorpicker_show.setBackgroundColor(Color.argb(100,seekBar_dialog_colorpicker_red.getProgress(),seekBar_dialog_colorpicker_green.getProgress(),seekBar_dialog_colorpicker_blue.getProgress()));
            }
        });
        //藍
        seekBar_dialog_colorpicker_blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                tv_dialog_colorpicker_blue_value.setText(progress+"");
                iv_dialog_colorpicker_show.setBackgroundColor(Color.argb(100,seekBar_dialog_colorpicker_red.getProgress(),seekBar_dialog_colorpicker_green.getProgress(),seekBar_dialog_colorpicker_blue.getProgress()));
            }
        });

        btn_dialog_colorpicker_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.setMainBgimg(100,seekBar_dialog_colorpicker_red.getProgress(),seekBar_dialog_colorpicker_green.getProgress(),seekBar_dialog_colorpicker_blue.getProgress());
                /**=======================================================================================*/
                /**更新資料庫資料*/
                new Thread(() -> {
                    //暫存三種顏色設定的資料庫ID(紅0->綠1->藍2)
                    List<Integer> ids = new ArrayList();
                    ids.add(0);ids.add(0);ids.add(0);
                    ids.set(0,DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryIdByWords("bgimg_red_value"));
                    ids.set(1,DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryIdByWords("bgimg_green_value"));
                    ids.set(2,DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryIdByWords("bgimg_blue_value"));
                    //更新資料庫資料
                    SelfDictionary_entity new_data;
                    new_data = new SelfDictionary_entity(ids.get(0), "bgimg_red_value", seekBar_dialog_colorpicker_red.getProgress()+"", "setting_values", "");
                    DataBase.getInstance(main_context).getSelfDictionaryDataDao().updateData(new_data);
                    new_data = new SelfDictionary_entity(ids.get(1), "bgimg_green_value", seekBar_dialog_colorpicker_green.getProgress()+"", "setting_values", "");
                    DataBase.getInstance(main_context).getSelfDictionaryDataDao().updateData(new_data);
                    new_data = new SelfDictionary_entity(ids.get(2), "bgimg_blue_value", seekBar_dialog_colorpicker_blue.getProgress()+"", "setting_values", "");
                    DataBase.getInstance(main_context).getSelfDictionaryDataDao().updateData(new_data);
                }).start();
                /**=======================================================================================*/

                dialog_colorpicker.dismiss();
            }
        });
        dialog_colorpicker.show();

        return this;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dialog_colorpicker.dismiss();
    }

    @Override
    public void onClick(View v) {
        dialog_colorpicker.dismiss();
    }
}
