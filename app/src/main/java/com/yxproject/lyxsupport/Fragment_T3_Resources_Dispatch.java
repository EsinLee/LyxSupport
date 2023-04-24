package com.yxproject.lyxsupport;

import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import com.yxproject.RoomDataBase.DataBase;
import com.yxproject.RoomDataBase.Dispatch_entity;
import com.yxproject.RoomDataBase.Items_entity;
import com.yxproject.RoomDataBase.Members_entity;
import com.yxproject.RoomDataBase.SelfDictionary_entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Fragment_T3_Resources_Dispatch extends Fragment {
    private Context main_context;

    private static Fragment_T3_Resources_Dispatch instance = null;
    RoomRecyclerViewAdapter dispatch_roomRecyclerViewAdapter;
    Dispatch_entity nowSelectedData;//取得在畫面上顯示中的資料內容

    private ArrayList<Spinner> spin_database_cars_childitem = new ArrayList<Spinner>();
    private ArrayList<TextView> tv_database_cars_childitem = new ArrayList<TextView>();
    private ArrayList<Button> btn_database_cars_childitem = new ArrayList<Button>();
    private ArrayList<RecyclerView> rcy_database_cars_childitem = new ArrayList<RecyclerView>();

    private DataBase dataBase;

    private Boolean dateChanged = TRUE;
    private Boolean todayIsFriday = FALSE;

    //摺疊控制元件
    private LinearLayout linearLayout_main_actdispatch;
    private ArrayList<ImageView> iv_arrow_database_cars_childitem = new ArrayList<ImageView>();
    private Boolean planViewIsOpened = TRUE;
    private Boolean foldViewOriginHeightNotSaved = TRUE;
    private int foldViewOriginHeight = 0;
    //預設動畫播放速度
    private int view_animation_duration = 300;

    //spinner儲存Room資料使用之陣列
    //車長
    private List conductorList = new ArrayList();
    //駕駛
    private List driverList = new ArrayList();
    //車號
    private List carList = new ArrayList();
    //任務
    private List eventList = new ArrayList();
    //地區
    private List areaList = new ArrayList();

    public Fragment_T3_Resources_Dispatch(Context context) {
        main_context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.adapter_page_t3_databasedispatch, container, false);
        instance = this;
        // set view elements
        spin_database_cars_childitem.add((Spinner) root.findViewById(R.id.spin_event_database_driverplan));
        spin_database_cars_childitem.add((Spinner) root.findViewById(R.id.spin_area_database_driverplan));
        spin_database_cars_childitem.add((Spinner) root.findViewById(R.id.spin_car_database_driverplan));
        spin_database_cars_childitem.add((Spinner) root.findViewById(R.id.spin_conductor_database_driverplan));
        spin_database_cars_childitem.add((Spinner) root.findViewById(R.id.spin_driver_database_driverplan));

        btn_database_cars_childitem.add((Button) root.findViewById(R.id.bt_modify_database_driverplan));
        btn_database_cars_childitem.add((Button) root.findViewById(R.id.bt_clear_database_driverplan));
        btn_database_cars_childitem.add((Button) root.findViewById(R.id.bt_create_database_driverplan));
        btn_database_cars_childitem.add((Button) root.findViewById(R.id.bt_fold_driverplan));
        btn_database_cars_childitem.add((Button) root.findViewById(R.id.bt_search_database_driverplan));

        tv_database_cars_childitem.add((TextView) root.findViewById(R.id.tv_event_database_driverplan));
        tv_database_cars_childitem.add((TextView) root.findViewById(R.id.tv_area_database_driverplan));
        tv_database_cars_childitem.add((TextView) root.findViewById(R.id.tv_car_database_driverplan));
        tv_database_cars_childitem.add((TextView) root.findViewById(R.id.tv_conductor_database_driverplan));
        tv_database_cars_childitem.add((TextView) root.findViewById(R.id.tv_driver_database_driverplan));
        tv_database_cars_childitem.add((TextView) root.findViewById(R.id.tv_dateday_database_driverplan));
        tv_database_cars_childitem.add((TextView) root.findViewById(R.id.tv_elseinfo_database_driverplan));

        iv_arrow_database_cars_childitem.add((ImageView) root.findViewById(R.id.iv_fold_actdatabasedictionary_a));
        iv_arrow_database_cars_childitem.add((ImageView) root.findViewById(R.id.iv_fold_actdatabasedictionary_b));
        linearLayout_main_actdispatch = root.findViewById(R.id.linearLayout_input_actdispatch);

        rcy_database_cars_childitem.add((RecyclerView) root.findViewById(R.id.rcy_main_databasedriverplan));
        rcy_database_cars_childitem.get(0).setLayoutManager(new LinearLayoutManager(main_context));
        rcy_database_cars_childitem.get(0).addItemDecoration(new DividerItemDecoration(main_context, DividerItemDecoration.VERTICAL));//設置分隔線
        setRecyclerFunction(rcy_database_cars_childitem.get(0));//設置RecyclerView左滑刪除

        /**===================================================================================================================================*/
        /**初始化*/
        new Thread(() -> {
            List<Dispatch_entity> dispatchdata = DataBase.getInstance(main_context).getDispatchDataDao().displayAllfromDispatch();
            dispatch_roomRecyclerViewAdapter = new RoomRecyclerViewAdapter(main_context, dispatchdata, "Dispatch", 4);
            rcy_database_cars_childitem.get(0).setAdapter(dispatch_roomRecyclerViewAdapter);
            getActivity().runOnUiThread(() -> {
                //取得被選中的資料，並顯示於畫面
                //.setOnItemClickListener((myData)-> {//匿名函式(原貌在上方)
                dispatch_roomRecyclerViewAdapter.setOnItemClickListener((myData)-> {//匿名函式(原貌在上方)
                    nowSelectedData = (Dispatch_entity) myData;
                    tv_database_cars_childitem.get(0).setText(((Dispatch_entity) myData).getEvent());
                    tv_database_cars_childitem.get(1).setText(((Dispatch_entity) myData).getArea());
                    tv_database_cars_childitem.get(2).setText(((Dispatch_entity) myData).getCar());
                    tv_database_cars_childitem.get(3).setText(((Dispatch_entity) myData).getConductor());
                    tv_database_cars_childitem.get(4).setText(((Dispatch_entity) myData).getDriver());
                    tv_database_cars_childitem.get(5).setText(((Dispatch_entity) myData).getTime());
                    tv_database_cars_childitem.get(6).setText(((Dispatch_entity) myData).getElseInfo());
                });
            });
        }).start();
        //-------------------------------------------------------------------------------------------------------------------------------------
        // view do any works
        Calendar cld = Calendar.getInstance();
        cld.add(cld.DATE,+1);
        tv_database_cars_childitem.get(5).setText((cld.get(Calendar.YEAR)-1911) + "/" + (cld.get(Calendar.MONTH) + 1) + "/" + cld.get(Calendar.DAY_OF_MONTH) + " " + dateFormatOutput("dayofweek",cld));
        tv_database_cars_childitem.get(5).setInputType(InputType.TYPE_NULL);
        if(dateFormatOutput("dayofweek",cld) == "(一)"){todayIsFriday = TRUE;
        }else{todayIsFriday = FALSE;}
        tv_database_cars_childitem.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                dateChanged = TRUE;
                // date picker dialog
                DatePickerDialog picker;
                picker = new DatePickerDialog(main_context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Date target_date = new Date(year, monthOfYear, dayOfMonth-1);
                                tv_database_cars_childitem.get(5).setText((year-1911) + "/" + (monthOfYear + 1) + "/" + dayOfMonth + " " + dateFormatOutput("dayofweek",dateToCalendar(target_date)));
                                if(dateFormatOutput("dayofweek",dateToCalendar(target_date)) == "(一)"){todayIsFriday = TRUE;
                                }else{todayIsFriday = FALSE;}
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        //----------------------------------------------------------------------------------------------------------------------------
        new Thread(() -> {
            //任務------------------------------------------------------------------------------------------------------------------------
            List<SelfDictionary_entity> dictionarydata = DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryByTags("軍車任務");
            for(SelfDictionary_entity a:dictionarydata){
                eventList.add(a.getWord());
            }
            ArrayAdapter<String> adapteraa = new ArrayAdapter<>(getContext(), R.layout.spinner_style_a, eventList);
            adapteraa.setDropDownViewResource(R.layout.spinner_style_a);
            spin_database_cars_childitem.get(0).setAdapter(adapteraa);
            //spin_database_cars_childitem.get(2).setOnItemSelectedListener(spnOnItemSelected);
            spin_database_cars_childitem.get(0).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // 選項有選取時的動作
                    tv_database_cars_childitem.get(0).setText(parentView.getItemAtPosition(position).toString());
                }
                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // 選項未選取時的動作
                }
            });
            //地區------------------------------------------------------------------------------------------------------------------------
            dictionarydata = DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryByTags("軍車地區");
            for(SelfDictionary_entity a:dictionarydata){
                areaList.add(a.getWord());
            }
            adapteraa = new ArrayAdapter<>(getContext(), R.layout.spinner_style_a, areaList);
            adapteraa.setDropDownViewResource(R.layout.spinner_style_a);
            spin_database_cars_childitem.get(1).setAdapter(adapteraa);
            //spin_database_cars_childitem.get(2).setOnItemSelectedListener(spnOnItemSelected);
            spin_database_cars_childitem.get(1).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // 選項有選取時的動作
                    tv_database_cars_childitem.get(1).setText(parentView.getItemAtPosition(position).toString());
                }
                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // 選項未選取時的動作
                }
            });
            //車號------------------------------------------------------------------------------------------------------------------------
            List<Items_entity> itemsdata = DataBase.getInstance(main_context).getItemsDataDao().findItemsByitemtype("軍車");
            for(Items_entity a:itemsdata){
                carList.add(a.getName());
            }
            adapteraa = new ArrayAdapter<>(getContext(), R.layout.spinner_style_a, carList);
            adapteraa.setDropDownViewResource(R.layout.spinner_style_a);
            spin_database_cars_childitem.get(2).setAdapter(adapteraa);
            //spin_database_cars_childitem.get(2).setOnItemSelectedListener(spnOnItemSelected);
            spin_database_cars_childitem.get(2).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // 選項有選取時的動作
                    tv_database_cars_childitem.get(2).setText(parentView.getItemAtPosition(position).toString());
                }
                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // 選項未選取時的動作
                }
            });
            //車長------------------------------------------------------------------------------------------------------------------------
            List<Members_entity> conductorsdata = DataBase.getInstance(main_context).getMembersDataDao().findMembersByPersonalTags("車長");
            for(Members_entity a:conductorsdata){
                conductorList.add(a.getRank() + " " + a.getName());
            }
            adapteraa = new ArrayAdapter<>(getContext(), R.layout.spinner_style_a, conductorList);
            adapteraa.setDropDownViewResource(R.layout.spinner_style_a);
            spin_database_cars_childitem.get(3).setAdapter(adapteraa);
            //spin_database_cars_childitem.get(3).setOnItemSelectedListener(spnOnItemSelected);
            spin_database_cars_childitem.get(3).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // 選項有選取時的動作
                    tv_database_cars_childitem.get(3).setText(parentView.getItemAtPosition(position).toString());
                }
                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // 選項未選取時的動作
                }
            });
            //駕駛------------------------------------------------------------------------------------------------------------------------
            List<Members_entity> driversdata = DataBase.getInstance(main_context).getMembersDataDao().findMembersByPersonalTags("駕駛");
            for(Members_entity a:driversdata){
                driverList.add(a.getRank() + " " + a.getName());
            }
            adapteraa = new ArrayAdapter<>(getContext(), R.layout.spinner_style_a, driverList);
            adapteraa.setDropDownViewResource(R.layout.spinner_style_a);
            spin_database_cars_childitem.get(4).setAdapter(adapteraa);
            //spin_database_cars_childitem.get(4).setOnItemSelectedListener(spnOnItemSelected);
            spin_database_cars_childitem.get(4).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // 選項有選取時的動作
                    tv_database_cars_childitem.get(4).setText(parentView.getItemAtPosition(position).toString());
                }
                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // 選項未選取時的動作
                }
            });
        }).start();
        //----------------------------------------------------------------------------------------------------------------------------
        /**===================================================================================================================================*/
        /**設置修改資料的事件*/
        btn_database_cars_childitem.get(0).setOnClickListener((v) -> {
            new Thread(() -> {
                if(nowSelectedData ==null) return;//如果目前沒前台沒有資料，則以下程序不執行
                String time = tv_database_cars_childitem.get(5).getText().toString();
                String event = tv_database_cars_childitem.get(0).getText().toString();
                String area = tv_database_cars_childitem.get(1).getText().toString();
                String car = tv_database_cars_childitem.get(2).getText().toString();
                String conductor = tv_database_cars_childitem.get(3).getText().toString();
                String driver = tv_database_cars_childitem.get(4).getText().toString();
                String elseinfo = tv_database_cars_childitem.get(6).getText().toString();
                Dispatch_entity data = new Dispatch_entity(nowSelectedData.getId(), time, event, area,conductor, driver, car, elseinfo);
                DataBase.getInstance(main_context).getDispatchDataDao().updateData(data);
                getActivity().runOnUiThread(() -> {
                    tv_database_cars_childitem.get(6).setText("");
                    nowSelectedData = null;
                    dispatch_roomRecyclerViewAdapter.refreshView("Dispatch");
                    //Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Members");
                    Toast.makeText(main_context, "已更新資訊！", Toast.LENGTH_LONG).show();
                });
            }).start();
        });
        /**===================================================================================================================================*/
        /**清空資料*/
        btn_database_cars_childitem.get(1).setOnClickListener((v -> {
            tv_database_cars_childitem.get(6).setText("");
            nowSelectedData = null;
        }));
        /**===================================================================================================================================*/
        /**新增資料*/
        btn_database_cars_childitem.get(2).setOnClickListener((v -> {
            new Thread(() -> {
                String time = tv_database_cars_childitem.get(5).getText().toString();
                String event = tv_database_cars_childitem.get(0).getText().toString();
                String area = tv_database_cars_childitem.get(1).getText().toString();
                String car = tv_database_cars_childitem.get(2).getText().toString();
                String conductor = tv_database_cars_childitem.get(3).getText().toString();
                String driver = tv_database_cars_childitem.get(4).getText().toString();
                String elseinfo = tv_database_cars_childitem.get(6).getText().toString();
                Dispatch_entity data = new Dispatch_entity(time, event, area,conductor, driver, car, elseinfo);
                DataBase.getInstance(main_context).getDispatchDataDao().insertDispatchData(data);
                getActivity().runOnUiThread(() -> {
                    tv_database_cars_childitem.get(6).setText("");
                    nowSelectedData = null;
                    dispatch_roomRecyclerViewAdapter.refreshView("Dispatch");
                    Toast.makeText(main_context, "已更新資訊！", Toast.LENGTH_LONG).show();
                });
            }).start();
        }));
        /**===================================================================================================================================*/
        /**設置摺疊按鈕事件*/
        btn_database_cars_childitem.get(3).setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //箭頭圖片旋轉
                setImagesRotateAnimation(planViewIsOpened,iv_arrow_database_cars_childitem.get(0),iv_arrow_database_cars_childitem.get(1));
                //btn_database_cars_childitem.get(3).setText("派  車  預  劃" +  linearLayout_main_actdispatch.getHeight());
                //下方輸入區塊摺疊
                //------------------------------------------------------------------
                int target_height = linearLayout_main_actdispatch.getHeight();
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
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)linearLayout_main_actdispatch.getLayoutParams();
                        params.height = animatorValue;
                        linearLayout_main_actdispatch.setLayoutParams(params);
                    }
                });
                scaleY.setTarget(linearLayout_main_actdispatch);
                scaleY.setDuration(view_animation_duration);
                scaleY.start();
                //------------------------------------------------------------------
                //更新當前展開狀態
                planViewIsOpened = !planViewIsOpened;
            }
        });
        /**===================================================================================================================================*/
        /**設置搜尋按鈕事件*/
        btn_database_cars_childitem.get(4).setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                new Thread(() -> {
                    getActivity().runOnUiThread(() -> {
                        String dateori = tv_database_cars_childitem.get(5).getText().toString().substring(0,tv_database_cars_childitem.get(5).getText().toString().length()-4);
                        dateori = Integer.valueOf(dateori.substring(0,3)) + "" +  dateori.substring(3,dateori.length());
                        dispatch_roomRecyclerViewAdapter.refreshSearchDatatoView("Dispatch_time", dateori, "??");
                        //除錯-輸出搜尋之日期
                        Log.d("Current_date", dateori + "");
                        Toast.makeText(main_context, "已更新查詢資訊！", Toast.LENGTH_LONG).show();
                    });
                }).start();
            }
        });



        return root;
    }

    public void onAnimationUpdate(ValueAnimator animation) {
        int animatorValue = Integer.valueOf(animation.getAnimatedValue() + "");
        //判定原始高度
        if(foldViewOriginHeightNotSaved){
            foldViewOriginHeight = animatorValue;
            foldViewOriginHeightNotSaved = !foldViewOriginHeightNotSaved;
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)linearLayout_main_actdispatch.getLayoutParams();
        params.height = animatorValue;
        linearLayout_main_actdispatch.setLayoutParams(params);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // view do any works

    }

    private AdapterView.OnItemSelectedListener spnOnItemSelected = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
            // 選項有選取時的動作
            String sPos=String.valueOf(pos);
            String sInfo=parent.getItemAtPosition(pos).toString();
        }
        public void onNothingSelected(AdapterView<?> parent) {
            // 沒有選取時的動作
        }
    };

    private static String dateFormatOutput(String type, Calendar date){
        switch (type){
            case"year_ad":
                return "" + date.get(Calendar.YEAR);
            case"year_tw":
                return "" + (date.get(Calendar.YEAR) - 1911);
            case"month":
                return "" + (date.get(Calendar.MONTH) + 1);
            case"day":
                return "" + date.get(Calendar.DATE);
            case"dayofweek":
                switch(date.get(Calendar.DAY_OF_WEEK)){
                    case 1: return "(日)";
                    case 2:return "(一)";
                    case 3:return "(二)";
                    case 4:return "(三)";
                    case 5:return "(四)";
                    case 6:return "(五)";
                    case 7:return "(六)";
                    default:return "error";
                }
            default:
                return "error";
        }
    }

    public static Calendar dateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public void spinnersLiveupdate(String target){
        switch (target)
        {
            case "Members":
                new Thread(() -> {
                    //車長更新-------------------------------------------------------------------------------------------------------------------------------
                    List<Members_entity> mambersdata = DataBase.getInstance(main_context).getMembersDataDao().findMembersByPersonalTags("車長");
                    conductorList.clear();
                    for(Members_entity a:mambersdata){
                        conductorList.add(a.getRank() + " " + a.getName());
                    }
                    ArrayAdapter<String> adapteraa = (ArrayAdapter)spin_database_cars_childitem.get(3).getAdapter();
                    if(conductorList.size() != 0) {
                        tv_database_cars_childitem.get(3).setText(conductorList.get(0).toString());
                    }
                    adapteraa.notifyDataSetChanged();
                    //駕駛更新-------------------------------------------------------------------------------------------------------------------------------
                    mambersdata = DataBase.getInstance(main_context).getMembersDataDao().findMembersByPersonalTags("駕駛");
                    driverList.clear();
                    for(Members_entity a:mambersdata){
                        driverList.add(a.getRank() + " " + a.getName());
                    }
                    adapteraa = (ArrayAdapter)spin_database_cars_childitem.get(4).getAdapter();
                    //若為陣列為空將會報錯
                    if(driverList.size() != 0) {
                        tv_database_cars_childitem.get(4).setText(driverList.get(0).toString());
                    }
                    adapteraa.notifyDataSetChanged();
                    //車號更新-------------------------------------------------------------------------------------------------------------------------------
                    List<Items_entity> itemsdata = DataBase.getInstance(main_context).getItemsDataDao().findItemsByitemtype("軍車");
                    carList.clear();
                    for(Items_entity a:itemsdata){
                        carList.add(a.getName());
                    }
                    adapteraa = (ArrayAdapter)spin_database_cars_childitem.get(2).getAdapter();
                    //若為陣列為空將會報錯
                    if(carList.size() != 0) {
                        tv_database_cars_childitem.get(2).setText(carList.get(0).toString());
                    }
                    adapteraa.notifyDataSetChanged();
                }).start();
                break;
            case "Cars":
                new Thread(() -> {
                    List<Items_entity> itemsdata = DataBase.getInstance(main_context).getItemsDataDao().findItemsByitemtype("軍車");
                    carList.clear();
                    for(Items_entity a:itemsdata){
                        carList.add(a.getName());
                    }
                    ArrayAdapter<String> adapteraa = (ArrayAdapter)spin_database_cars_childitem.get(2).getAdapter();
                    //adapteraa.notifyDataSetChanged();
                }).start();
                break;
            case "Dictionary":
                new Thread(() -> {
                    //任務更新-------------------------------------------------------------------------------------------------------------------------------
                    List<SelfDictionary_entity> dictionarydata = DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryByTags("軍車任務");
                    eventList.clear();
                    for(SelfDictionary_entity a:dictionarydata){
                        eventList.add(a.getWord());
                    }
                    ArrayAdapter<String> adapteraa = (ArrayAdapter)spin_database_cars_childitem.get(0).getAdapter();
                    //若為陣列為空將會報錯
                    if(eventList.size() != 0) {
                        tv_database_cars_childitem.get(0).setText(eventList.get(0).toString());
                    }
                    //adapteraa.notifyDataSetChanged();
                    //地區更新-------------------------------------------------------------------------------------------------------------------------------
                    dictionarydata = DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryByTags("軍車地區");
                    areaList.clear();
                    for(SelfDictionary_entity a:dictionarydata){
                        areaList.add(a.getWord());
                    }
                    adapteraa = (ArrayAdapter)spin_database_cars_childitem.get(1).getAdapter();
                    //若為陣列為空將會報錯
                    if(areaList.size() != 0) {
                        tv_database_cars_childitem.get(1).setText(areaList.get(0).toString());
                    }
                    //adapteraa.notifyDataSetChanged();
                }).start();
                break;
        }
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
                        dispatch_roomRecyclerViewAdapter.deleteDBData("Dispatch",position);
                        /*new Thread(() -> {
                            getActivity().runOnUiThread(() -> {
                                //Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Cars");
                                //Fragment_T3_Resources_Driveplan.getInstance().spinnersLiveupdate("Cars");
                                //Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Members");
                            });
                        }).start();*/

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

    //設置對外端口
    public static Fragment_T3_Resources_Dispatch getInstance() {
        return instance;
    }
}
