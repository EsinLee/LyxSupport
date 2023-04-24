package com.yxproject.lyxsupport;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Fragment_T2_Forwork_WorkplanOutput extends Fragment {
    //Input MainActivity
    private Context main_context;

    private static Fragment_T2_Forwork_WorkplanOutput instance = null;
    RoomSpinnerAdapter roomSpinnerAdapter;

    //各事項輸入區塊原始高度儲存狀態
    private ArrayList<String> array_view_name = new ArrayList<String>();
    //物件陣列
    private ArrayList<Button> bt_fold_actshowworkplan_childitem = new ArrayList<Button>();
    private ArrayList<ImageView> iv_arrow_actshowworkplan_childitem = new ArrayList<ImageView>();
    private ArrayList<LinearLayout> linearLayout_inputarea_actshowworkplan_childitem = new ArrayList<LinearLayout>();
    private ArrayList<CheckBox> chb_forwork_actshowworkplan_childitem = new ArrayList<CheckBox>();
    //預設動畫播放速度
    private int view_animation_duration = 300;
    //各事項開啟狀態
    private ArrayList<Boolean> array_inputarea_view_isopen = new ArrayList<Boolean>();
    //各事項輸入區塊高度
    private ArrayList<Integer> array_inputarea_view_height = new ArrayList<Integer>();
    //各事項輸入區塊原始高度儲存狀態
    private ArrayList<Boolean> array_original_height_notsaved = new ArrayList<Boolean>();



    Boolean dateChanged = TRUE;
    Boolean todayIsFriday = FALSE;

    //spinner儲存Room資料使用之陣列
    //車長
    private List conductorList = new ArrayList();
    //車長
    private List driverList = new ArrayList();

    public Fragment_T2_Forwork_WorkplanOutput(Context context) {
        main_context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.adapter_page_t2_showworkplan, container, false);
        instance = this;
        // set view elements-nightwork
        bt_fold_actshowworkplan_childitem.add((Button) root.findViewById(R.id.bt_nightwork_actshowworkplan));
        iv_arrow_actshowworkplan_childitem.add((ImageView) root.findViewById(R.id.iv_nightwork_actshowworkplan_a));
        iv_arrow_actshowworkplan_childitem.add((ImageView) root.findViewById(R.id.iv_nightwork_actshowworkplan_b));
        linearLayout_inputarea_actshowworkplan_childitem.add((LinearLayout) root.findViewById(R.id.linearLayout_nightwork_actshowworkplan));
        chb_forwork_actshowworkplan_childitem.add((CheckBox) root.findViewById(R.id.chb_nightwork_actshowworkplan));
        array_inputarea_view_isopen.add(true);
        array_inputarea_view_height.add(100);
        array_original_height_notsaved.add(true);
        array_view_name.add("nightwork");
        // set view elements-meeting
        bt_fold_actshowworkplan_childitem.add((Button) root.findViewById(R.id.bt_meeting_actshowworkplan));
        iv_arrow_actshowworkplan_childitem.add((ImageView) root.findViewById(R.id.iv_meeting_actshowworkplan_a));
        iv_arrow_actshowworkplan_childitem.add((ImageView) root.findViewById(R.id.iv_meeting_actshowworkplan_b));
        linearLayout_inputarea_actshowworkplan_childitem.add((LinearLayout) root.findViewById(R.id.linearLayout_meeting_actshowworkplan));
        chb_forwork_actshowworkplan_childitem.add((CheckBox) root.findViewById(R.id.chb_meeting_actshowworkplan));
        array_inputarea_view_isopen.add(true);
        array_inputarea_view_height.add(100);
        array_original_height_notsaved.add(true);
        array_view_name.add("meeting");
        // set view elements-roadimprovement
        bt_fold_actshowworkplan_childitem.add((Button) root.findViewById(R.id.bt_roadimprovement_actshowworkplan));
        iv_arrow_actshowworkplan_childitem.add((ImageView) root.findViewById(R.id.iv_roadimprovement_actshowworkplan_a));
        iv_arrow_actshowworkplan_childitem.add((ImageView) root.findViewById(R.id.iv_roadimprovement_actshowworkplan_b));
        linearLayout_inputarea_actshowworkplan_childitem.add((LinearLayout) root.findViewById(R.id.linearLayout_roadimprovement_actshowworkplan));
        chb_forwork_actshowworkplan_childitem.add((CheckBox) root.findViewById(R.id.chb_roadimprovement_actshowworkplan));
        array_inputarea_view_isopen.add(true);
        array_inputarea_view_height.add(100);
        array_original_height_notsaved.add(true);
        array_view_name.add("raodimprovement");
        // set view elements-workplan
        bt_fold_actshowworkplan_childitem.add((Button) root.findViewById(R.id.bt_workplan_actshowworkplan));
        iv_arrow_actshowworkplan_childitem.add((ImageView) root.findViewById(R.id.iv_workplan_actshowworkplan_a));
        iv_arrow_actshowworkplan_childitem.add((ImageView) root.findViewById(R.id.iv_workplan_actshowworkplan_b));
        linearLayout_inputarea_actshowworkplan_childitem.add((LinearLayout) root.findViewById(R.id.linearLayout_workplan_actshowworkplan));
        chb_forwork_actshowworkplan_childitem.add((CheckBox) root.findViewById(R.id.chb_workplan_actshowworkplan));
        array_inputarea_view_isopen.add(true);
        array_inputarea_view_height.add(100);
        array_original_height_notsaved.add(true);
        array_view_name.add("workplan");
        //數值設定=======================================================================================================================================
        //int arrow_image_index = 0;
        //夜間派遣=======================================================================================================================================
        bt_fold_actshowworkplan_childitem.get(0).setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //箭頭圖片旋轉
                setImagesRotateAnimation(array_inputarea_view_isopen.get(0),iv_arrow_actshowworkplan_childitem.get(0),iv_arrow_actshowworkplan_childitem.get(1));
                //下方輸入區塊摺疊
                //------------------------------------------------------------------
                int target_height = linearLayout_inputarea_actshowworkplan_childitem.get(0).getHeight();
                ValueAnimator scaleY;
                if(array_inputarea_view_isopen.get(0)) {//已展開->要收起
                    scaleY = ValueAnimator.ofInt(target_height,0);
                }else{//已收起->要展開
                    scaleY = ValueAnimator.ofInt(0,array_inputarea_view_height.get(0));
                }
                scaleY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatorValue = Integer.valueOf(animation.getAnimatedValue() + "");
                        //判定原始高度
                        if(array_original_height_notsaved.get(0)){
                            array_inputarea_view_height.set(0,animatorValue);
                            array_original_height_notsaved.set(0,!array_original_height_notsaved.get(0));
                        }
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)linearLayout_inputarea_actshowworkplan_childitem.get(0).getLayoutParams();
                        params.height = animatorValue;
                        linearLayout_inputarea_actshowworkplan_childitem.get(0).setLayoutParams(params);
                    }
                });
                scaleY.setTarget(linearLayout_inputarea_actshowworkplan_childitem.get(0));
                scaleY.setDuration(view_animation_duration);
                scaleY.start();
                //------------------------------------------------------------------
                //更新當前展開狀態
                array_inputarea_view_isopen.set(0,!array_inputarea_view_isopen.get(0));
            }
        });
        chb_forwork_actshowworkplan_childitem.get(0).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                //判斷CheckBox是否有勾選，同mCheckBox.isChecked()
                if(ischecked)
                {
                    //CheckBox狀態 : 已勾選，顯示TextView
                    bt_fold_actshowworkplan_childitem.get(0).setText("夜間派遣 哭阿");
                }
                else
                {
                    //CheckBox狀態 : 未勾選，隱藏TextView
                    bt_fold_actshowworkplan_childitem.get(0).setText("夜間派遣");
                }
            }
        });
        //=============================================================================================================================================
        //明日會議=======================================================================================================================================
        bt_fold_actshowworkplan_childitem.get(1).setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //箭頭圖片旋轉
                setImagesRotateAnimation(array_inputarea_view_isopen.get(1),iv_arrow_actshowworkplan_childitem.get(2),iv_arrow_actshowworkplan_childitem.get(3));
                //下方輸入區塊摺疊
                //------------------------------------------------------------------
                int target_height = linearLayout_inputarea_actshowworkplan_childitem.get(1).getHeight();
                ValueAnimator scaleY;
                if(array_inputarea_view_isopen.get(1)) {//已展開->要收起
                    scaleY = ValueAnimator.ofInt(target_height,0);
                }else{//已收起->要展開
                    scaleY = ValueAnimator.ofInt(0,array_inputarea_view_height.get(1));
                }
                scaleY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatorValue = Integer.valueOf(animation.getAnimatedValue() + "");
                        //判定原始高度
                        if(array_original_height_notsaved.get(1)){
                            array_inputarea_view_height.set(1,animatorValue);
                            array_original_height_notsaved.set(1,!array_original_height_notsaved.get(1));
                        }
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)linearLayout_inputarea_actshowworkplan_childitem.get(1).getLayoutParams();
                        params.height = animatorValue;
                        linearLayout_inputarea_actshowworkplan_childitem.get(1).setLayoutParams(params);
                    }
                });
                scaleY.setTarget(linearLayout_inputarea_actshowworkplan_childitem.get(1));
                scaleY.setDuration(view_animation_duration);
                scaleY.start();
                //------------------------------------------------------------------
                //更新當前展開狀態
                array_inputarea_view_isopen.set(1,!array_inputarea_view_isopen.get(1));
            }
        });
        chb_forwork_actshowworkplan_childitem.get(1).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                //判斷CheckBox是否有勾選，同mCheckBox.isChecked()
                if(ischecked)
                {
                    //CheckBox狀態 : 已勾選，顯示TextView
                    bt_fold_actshowworkplan_childitem.get(1).setText("明日會議 哭阿");
                }
                else
                {
                    //CheckBox狀態 : 未勾選，隱藏TextView
                    bt_fold_actshowworkplan_childitem.get(1).setText("明日會議");
                }
            }
        });
        //=============================================================================================================================================
        //改善管制=======================================================================================================================================
        bt_fold_actshowworkplan_childitem.get(2).setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //箭頭圖片旋轉
                setImagesRotateAnimation(array_inputarea_view_isopen.get(2),iv_arrow_actshowworkplan_childitem.get(4),iv_arrow_actshowworkplan_childitem.get(5));
                //下方輸入區塊摺疊
                //------------------------------------------------------------------
                int target_height = linearLayout_inputarea_actshowworkplan_childitem.get(2).getHeight();
                ValueAnimator scaleY;
                if(array_inputarea_view_isopen.get(2)) {//已展開->要收起
                    scaleY = ValueAnimator.ofInt(target_height,0);
                }else{//已收起->要展開
                    scaleY = ValueAnimator.ofInt(0,array_inputarea_view_height.get(2));
                }
                scaleY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatorValue = Integer.valueOf(animation.getAnimatedValue() + "");
                        //判定原始高度
                        if(array_original_height_notsaved.get(2)){
                            array_inputarea_view_height.set(2,animatorValue);
                            array_original_height_notsaved.set(2,!array_original_height_notsaved.get(2));
                        }
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)linearLayout_inputarea_actshowworkplan_childitem.get(2).getLayoutParams();
                        params.height = animatorValue;
                        linearLayout_inputarea_actshowworkplan_childitem.get(2).setLayoutParams(params);
                    }
                });
                scaleY.setTarget(linearLayout_inputarea_actshowworkplan_childitem.get(2));
                scaleY.setDuration(view_animation_duration);
                scaleY.start();
                //------------------------------------------------------------------
                //更新當前展開狀態
                array_inputarea_view_isopen.set(2,!array_inputarea_view_isopen.get(2));
            }
        });
        chb_forwork_actshowworkplan_childitem.get(2).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                //判斷CheckBox是否有勾選，同mCheckBox.isChecked()
                if(ischecked)
                {
                    //CheckBox狀態 : 已勾選，顯示TextView
                    bt_fold_actshowworkplan_childitem.get(2).setText("改善管制 哭阿");
                }
                else
                {
                    //CheckBox狀態 : 未勾選，隱藏TextView
                    bt_fold_actshowworkplan_childitem.get(2).setText("改善管制");
                }
            }
        });
        //=============================================================================================================================================
        //明日預劃=======================================================================================================================================
        bt_fold_actshowworkplan_childitem.get(3).setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //箭頭圖片旋轉
                setImagesRotateAnimation(array_inputarea_view_isopen.get(3),iv_arrow_actshowworkplan_childitem.get(6),iv_arrow_actshowworkplan_childitem.get(7));
                //下方輸入區塊摺疊
                //------------------------------------------------------------------
                int target_height = linearLayout_inputarea_actshowworkplan_childitem.get(3).getHeight();
                ValueAnimator scaleY;
                if(array_inputarea_view_isopen.get(3)) {//已展開->要收起
                    scaleY = ValueAnimator.ofInt(target_height,0);
                }else{//已收起->要展開
                    scaleY = ValueAnimator.ofInt(0,array_inputarea_view_height.get(3));
                }
                scaleY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatorValue = Integer.valueOf(animation.getAnimatedValue() + "");
                        //判定原始高度
                        if(array_original_height_notsaved.get(3)){
                            array_inputarea_view_height.set(3,animatorValue);
                            array_original_height_notsaved.set(3,!array_original_height_notsaved.get(3));
                        }
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)linearLayout_inputarea_actshowworkplan_childitem.get(3).getLayoutParams();
                        params.height = animatorValue;
                        linearLayout_inputarea_actshowworkplan_childitem.get(3).setLayoutParams(params);
                    }
                });
                scaleY.setTarget(linearLayout_inputarea_actshowworkplan_childitem.get(3));
                scaleY.setDuration(view_animation_duration);
                scaleY.start();
                //------------------------------------------------------------------
                //更新當前展開狀態
                array_inputarea_view_isopen.set(3,!array_inputarea_view_isopen.get(3));
            }
        });
        chb_forwork_actshowworkplan_childitem.get(3).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                //判斷CheckBox是否有勾選，同mCheckBox.isChecked()
                if(ischecked)
                {
                    //CheckBox狀態 : 已勾選，顯示TextView
                    bt_fold_actshowworkplan_childitem.get(3).setText("明日預劃 哭阿");
                }
                else
                {
                    //CheckBox狀態 : 未勾選，隱藏TextView
                    bt_fold_actshowworkplan_childitem.get(3).setText("明日預劃");
                }
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void setImagesRotateAnimation(boolean opened,ImageView a, ImageView b){
        float ori_arrow_rotation_angle;
        float new_arrow_rotation_angle;
        //確認當前展開狀態
        if(opened) {//已展開->要收起
            ori_arrow_rotation_angle = 0f;
            new_arrow_rotation_angle = 180;
        }else{//已收起->要展開
            ori_arrow_rotation_angle = 180;
            new_arrow_rotation_angle = 0f;
        }
        RotateAnimation rotateAnimation = new RotateAnimation(
                ori_arrow_rotation_angle,
                new_arrow_rotation_angle,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f
        );
        rotateAnimation.setDuration(view_animation_duration);
        rotateAnimation.setFillAfter(true);
        a.startAnimation(rotateAnimation);
        b.startAnimation(rotateAnimation);
    }

    //設置對外端口
    public static Fragment_T2_Forwork_WorkplanOutput getInstance() {
        return instance;
    }
}
