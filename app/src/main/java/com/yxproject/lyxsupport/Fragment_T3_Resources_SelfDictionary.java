package com.yxproject.lyxsupport;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.stetho.Stetho;
import com.yxproject.RoomDataBase.DataBase;
import com.yxproject.RoomDataBase.SelfDictionary_entity;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;

public class Fragment_T3_Resources_SelfDictionary extends Fragment {
    private Context main_context;
    RoomRecyclerViewAdapter dictionary_roomRecyclerViewAdapter;
    SelfDictionary_entity nowSelectedData;//取得在畫面上顯示中的資料內容

    private ArrayList<TextView> tv_database_dictionary_childitem = new ArrayList<TextView>();
    private ArrayList<Button> btn_database_dictionary_childitem = new ArrayList<Button>();
    private ArrayList<RecyclerView> rcy_database_dictionary_childitem = new ArrayList<RecyclerView>();

    //摺疊控制元件
    private LinearLayout linearLayout_main_dictionary;
    private ArrayList<ImageView> iv_arrow_database_dictionary_childitem = new ArrayList<ImageView>();
    private Boolean planViewIsOpened = TRUE;
    private Boolean foldViewOriginHeightNotSaved = TRUE;
    private int foldViewOriginHeight = 0;
    //預設動畫播放速度
    private int view_animation_duration = 300;

    public Fragment_T3_Resources_SelfDictionary(Context context) {
        main_context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.adapter_page_t3_databaseselfdictionary, container, false);
        // set view elements
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(main_context);
        }

        btn_database_dictionary_childitem.add((Button) root.findViewById(R.id.bt_modify_actdatabasedictionary));
        btn_database_dictionary_childitem.add((Button) root.findViewById(R.id.bt_clear_actdatabasedictionary));
        btn_database_dictionary_childitem.add((Button) root.findViewById(R.id.bt_create_actdatabasedictionary));
        btn_database_dictionary_childitem.add((Button) root.findViewById(R.id.bt_fold_actdatabasedictionary));

        tv_database_dictionary_childitem.add((TextView) root.findViewById(R.id.edtv_word_actdatabasedictionary));
        tv_database_dictionary_childitem.add((TextView) root.findViewById(R.id.edtv_annotation_actdatabasedictionary));
        tv_database_dictionary_childitem.add((TextView) root.findViewById(R.id.edtv_tags_actdatabasedictionary));
        tv_database_dictionary_childitem.add((TextView) root.findViewById(R.id.edtv_elseinfo_actdatabasedictionary));

        rcy_database_dictionary_childitem.add((RecyclerView) root.findViewById(R.id.rcy_main_actdatabasedictionary));

        iv_arrow_database_dictionary_childitem.add((ImageView) root.findViewById(R.id.iv_fold_actdatabasedictionary_a));
        iv_arrow_database_dictionary_childitem.add((ImageView) root.findViewById(R.id.iv_fold_actdatabasedictionary_b));
        linearLayout_main_dictionary = root.findViewById(R.id.linearLayout_main_actdispatch);

        rcy_database_dictionary_childitem.get(0).setLayoutManager(new LinearLayoutManager(main_context));
        rcy_database_dictionary_childitem.get(0).addItemDecoration(new DividerItemDecoration(main_context, DividerItemDecoration.VERTICAL));//設置分隔線
        setRecyclerFunction(rcy_database_dictionary_childitem.get(0));//設置RecyclerView左滑刪除

        /**=======================================================================================*/
        /**初始化*/
        new Thread(() -> {
            List<SelfDictionary_entity> data = DataBase.getInstance(main_context).getSelfDictionaryDataDao().displayAllfromDictionary_exsetting();
            dictionary_roomRecyclerViewAdapter = new RoomRecyclerViewAdapter(main_context, data, "Dictionary", 4);
            rcy_database_dictionary_childitem.get(0).setAdapter(dictionary_roomRecyclerViewAdapter);
            getActivity().runOnUiThread(() -> {
                //取得被選中的資料，並顯示於畫面
                dictionary_roomRecyclerViewAdapter.setOnItemClickListener((myData)-> {//匿名函式(原貌在上方)
                    nowSelectedData = (SelfDictionary_entity) myData;
                    tv_database_dictionary_childitem.get(0).setText(((SelfDictionary_entity) myData).getWord());
                    tv_database_dictionary_childitem.get(1).setText(((SelfDictionary_entity) myData).getAnnotation());
                    tv_database_dictionary_childitem.get(2).setText(((SelfDictionary_entity) myData).getTags());
                    tv_database_dictionary_childitem.get(3).setText(((SelfDictionary_entity) myData).getElseInfo());
                });
            });
        }).start();
        /**=======================================================================================*/
        /**設置修改資料的事件*/
        btn_database_dictionary_childitem.get(0).setOnClickListener((v) -> {
            new Thread(() -> {
                if(nowSelectedData ==null) return;//如果目前沒前台沒有資料，則以下程序不執行
                String word = tv_database_dictionary_childitem.get(0).getText().toString();
                String annotation = tv_database_dictionary_childitem.get(1).getText().toString();
                String tags = tv_database_dictionary_childitem.get(2).getText().toString();
                String elseinfo = tv_database_dictionary_childitem.get(3).getText().toString();
                SelfDictionary_entity data = new SelfDictionary_entity(nowSelectedData.getId(), word, annotation, tags, elseinfo);
                DataBase.getInstance(main_context).getSelfDictionaryDataDao().updateData(data);
                getActivity().runOnUiThread(() -> {
                    tv_database_dictionary_childitem.get(0).setText("");
                    tv_database_dictionary_childitem.get(1).setText("");
                    tv_database_dictionary_childitem.get(2).setText("");
                    tv_database_dictionary_childitem.get(3).setText("");
                    nowSelectedData = null;
                    dictionary_roomRecyclerViewAdapter.refreshView("Dictionary");
                    //Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Cars");
                    Fragment_T3_Resources_Dispatch.getInstance().spinnersLiveupdate("Dictionary");
                    Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Dictionary");
                    Toast.makeText(main_context, "已更新資訊！", Toast.LENGTH_LONG).show();
                });
            }).start();
        });
        /**=======================================================================================*/
        /**清空資料*/
        btn_database_dictionary_childitem.get(1).setOnClickListener((v -> {
            tv_database_dictionary_childitem.get(0).setText("");
            tv_database_dictionary_childitem.get(1).setText("");
            tv_database_dictionary_childitem.get(2).setText("");
            tv_database_dictionary_childitem.get(3).setText("");
            nowSelectedData = null;
        }));
        /**=======================================================================================*/
        /**新增資料*/
        btn_database_dictionary_childitem.get(2).setOnClickListener((v -> {
            new Thread(() -> {
                String word = tv_database_dictionary_childitem.get(0).getText().toString();
                String annotation = tv_database_dictionary_childitem.get(1).getText().toString();
                String tags = tv_database_dictionary_childitem.get(2).getText().toString();
                String elseinfo = tv_database_dictionary_childitem.get(3).getText().toString();
                if (word.length() == 0) return;//如果名字欄沒填入任何東西，則不執行下面的程序
                SelfDictionary_entity data = new SelfDictionary_entity(word, annotation, tags, elseinfo);
                DataBase.getInstance(main_context).getSelfDictionaryDataDao().insertDictionaryData(data);
                getActivity().runOnUiThread(() -> {
                    tv_database_dictionary_childitem.get(0).setText("");
                    tv_database_dictionary_childitem.get(1).setText("");
                    tv_database_dictionary_childitem.get(2).setText("");
                    tv_database_dictionary_childitem.get(3).setText("");
                    nowSelectedData = null;
                    dictionary_roomRecyclerViewAdapter.refreshView("Dictionary");
                    //Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Cars");
                    Fragment_T3_Resources_Dispatch.getInstance().spinnersLiveupdate("Dictionary");
                    Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Dictionary");
                    Toast.makeText(main_context, "已新增資訊！", Toast.LENGTH_LONG).show();
                });
            }).start();
        }));
        /**=======================================================================================*/
        /**設置摺疊按鈕事件*/
        btn_database_dictionary_childitem.get(3).setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //箭頭圖片旋轉
                setImagesRotateAnimation(planViewIsOpened,iv_arrow_database_dictionary_childitem.get(0),iv_arrow_database_dictionary_childitem.get(1));
                //btn_database_cars_childitem.get(3).setText("派  車  預  劃" +  linearLayout_main_actdispatch.getHeight());
                //下方輸入區塊摺疊
                //------------------------------------------------------------------
                int target_height = linearLayout_main_dictionary.getHeight();
                ValueAnimator scaleY;
                if(planViewIsOpened) {//已展開->要收起
                    scaleY = ValueAnimator.ofInt(target_height,0);
                }else{//已收起->要展開
                    scaleY = ValueAnimator.ofInt(0,foldViewOriginHeight);
                }
                scaleY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatorValue = Integer.valueOf(animation.getAnimatedValue() + "");
                        //判定原始高度
                        if(foldViewOriginHeightNotSaved){
                            foldViewOriginHeight = animatorValue;
                            foldViewOriginHeightNotSaved = !foldViewOriginHeightNotSaved;
                        }
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)linearLayout_main_dictionary.getLayoutParams();
                        params.height = animatorValue;
                        linearLayout_main_dictionary.setLayoutParams(params);
                    }
                });
                scaleY.setTarget(linearLayout_main_dictionary);
                scaleY.setDuration(view_animation_duration);
                scaleY.start();
                //------------------------------------------------------------------
                //更新當前展開狀態
                planViewIsOpened = !planViewIsOpened;
            }
        });
        /**===================================================================================================================================*/

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // view do any works

    }

    /**設置RecyclerView的左滑刪除行為*/
    private void setRecyclerFunction(RecyclerView recyclerView){
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {//設置RecyclerView手勢功能
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction){
                    case ItemTouchHelper.LEFT:
                    case ItemTouchHelper.RIGHT:
                        dictionary_roomRecyclerViewAdapter.deleteDBData("Dictionary",position);
                        new Thread(() -> {
                            getActivity().runOnUiThread(() -> {
                                Fragment_T3_Resources_Dispatch.getInstance().spinnersLiveupdate("Dictionary");
                                Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Dictionary");
                            });
                        }).start();

                        break;
                }
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }
    /**
     * 摺疊按鍵兩側箭頭旋轉
     */
    private void setImagesRotateAnimation(boolean opened, ImageView a, ImageView b){
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



    //匯出檔案
    public void writeToStorage(Context mContext, String fileName, String jsonContent){
        File file = new File(mContext.getFilesDir(),"exportStudentJson");
        if(!file.exists()){
            file.mkdir();
        }
        try{
            File mFile = new File(file, fileName);
            FileWriter writer = new FileWriter(mFile);
            writer.append(jsonContent);
            writer.flush();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();

        }
    }
}
