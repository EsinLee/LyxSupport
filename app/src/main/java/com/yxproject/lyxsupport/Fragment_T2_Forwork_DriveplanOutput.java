package com.yxproject.lyxsupport;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.yxproject.RoomDataBase.DataBase;
import com.yxproject.RoomDataBase.Dispatch_entity;
import com.yxproject.RoomDataBase.Items_entity;
import com.yxproject.RoomDataBase.Members_entity;
import com.yxproject.RoomDataBase.SelfDictionary_entity;
import com.yxproject.application_data.AppData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Fragment_T2_Forwork_DriveplanOutput extends Fragment {
    //Input MainActivity
    private Context main_context;

    private static Fragment_T2_Forwork_DriveplanOutput instance = null;

    ArrayList<Spinner> spin_forwork_showdriveplan_childitem = new ArrayList<Spinner>();
    ArrayList<TextView> tv_forwork_showdriveplan_childitem = new ArrayList<TextView>();
    ArrayList<Button> btn_forwork_showdriveplan_childitem = new ArrayList<Button>();

    Boolean dateChanged = TRUE;
    Boolean todayIsFriday = FALSE;

    //spinner儲存Room資料使用之陣列
    //車長
    private List conductorList = new ArrayList();
    //駕駛
    private List driverList = new ArrayList();
    //車號
    private List carsList = new ArrayList();
    //任務
    private List eventList = new ArrayList();
    //地點
    private List areaList = new ArrayList();
    //線巡回報
    private List reportList = new ArrayList();
    //搜尋用字串暫存
    private String tempdriveplan = "";
    private String tempworkplan = "";
    //每日線巡回報字串暫存
    private String daywork_all = "";

    public Fragment_T2_Forwork_DriveplanOutput(Context context) {
        main_context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.adapter_page_t2_showdriveplan, container, false);
        instance = this;
        // set view elements

        spin_forwork_showdriveplan_childitem.add((Spinner) root.findViewById(R.id.spin_cont_event_actshowdriveplan));
        spin_forwork_showdriveplan_childitem.add((Spinner) root.findViewById(R.id.spin_cont_area_actshowdriveplan));
        spin_forwork_showdriveplan_childitem.add((Spinner) root.findViewById(R.id.spin_cont_conductor_actshowdriveplan));
        spin_forwork_showdriveplan_childitem.add((Spinner) root.findViewById(R.id.spin_cont_driver_actshowdriveplan));
        spin_forwork_showdriveplan_childitem.add((Spinner) root.findViewById(R.id.spin_cont_car_actshowdriveplan));
        spin_forwork_showdriveplan_childitem.add((Spinner) root.findViewById(R.id.spin_cont_daywork_actshowdriveplan_a));
        spin_forwork_showdriveplan_childitem.add((Spinner) root.findViewById(R.id.spin_cont_daywork_actshowdriveplan_b));
        spin_forwork_showdriveplan_childitem.add((Spinner) root.findViewById(R.id.spin_cont_daywork_actshowdriveplan_c));
        spin_forwork_showdriveplan_childitem.add((Spinner) root.findViewById(R.id.spin_cont_daywork_actshowdriveplan_d));
        spin_forwork_showdriveplan_childitem.add((Spinner) root.findViewById(R.id.spin_cont_daywork_actshowdriveplan_e));

        btn_forwork_showdriveplan_childitem.add((Button) root.findViewById(R.id.bt_sent_actshowdriveplan));
        btn_forwork_showdriveplan_childitem.add((Button) root.findViewById(R.id.bt_clear_actshowdriveplan));
        btn_forwork_showdriveplan_childitem.add((Button) root.findViewById(R.id.bt_addition_actshowdriveplan));
        btn_forwork_showdriveplan_childitem.add((Button) root.findViewById(R.id.bt_fast_actshowdriveplan));

        tv_forwork_showdriveplan_childitem.add((TextView) root.findViewById(R.id.tv_dateday_actshowdriveplan));
        tv_forwork_showdriveplan_childitem.add((TextView) root.findViewById(R.id.tv_result_actshowdriveplan));
        tv_forwork_showdriveplan_childitem.add((TextView) root.findViewById(R.id.tv_tommorrow_actshowdriveplan));
        tv_forwork_showdriveplan_childitem.add((TextView) root.findViewById(R.id.tv_addition_actshowdriveplan));
        tv_forwork_showdriveplan_childitem.add((TextView) root.findViewById(R.id.tv_dateday_end_actshowdriveplan));

        //spin_a = root.findViewById(R.id.spin_cont_event_actshowdriveplan);

        // view do any works
        Calendar cld = Calendar.getInstance();
        cld.add(cld.DATE,+1);
        tv_forwork_showdriveplan_childitem.get(0).setText((cld.get(Calendar.YEAR)-1911) + "/" + (cld.get(Calendar.MONTH) + 1) + "/" + cld.get(Calendar.DAY_OF_MONTH) + " " + dateFormatOutput("dayofweek",cld));
        tv_forwork_showdriveplan_childitem.get(0).setInputType(InputType.TYPE_NULL);
        if(dateFormatOutput("dayofweek",cld) == "(一)"){todayIsFriday = TRUE;
        }else{todayIsFriday = FALSE;}
        tv_forwork_showdriveplan_childitem.get(0).setOnClickListener(new View.OnClickListener() {
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
                                tv_forwork_showdriveplan_childitem.get(0).setText((year-1911) + "/" + (monthOfYear + 1) + "/" + dayOfMonth + " " + dateFormatOutput("dayofweek",dateToCalendar(target_date)));
                                if(dateFormatOutput("dayofweek",dateToCalendar(target_date)) == "(一)"){todayIsFriday = TRUE;
                                }else{todayIsFriday = FALSE;}
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        // view do any works
        //Calendar cld = Calendar.getInstance();
        //cld.add(cld.DATE,+1);
        tv_forwork_showdriveplan_childitem.get(4).setText((cld.get(Calendar.YEAR)-1911) + "/" + (cld.get(Calendar.MONTH) + 1) + "/" + cld.get(Calendar.DAY_OF_MONTH) + " " + dateFormatOutput("dayofweek",cld));
        tv_forwork_showdriveplan_childitem.get(4).setInputType(InputType.TYPE_NULL);
        if(dateFormatOutput("dayofweek",cld) == "(一)"){todayIsFriday = TRUE;
        }else{todayIsFriday = FALSE;}
        tv_forwork_showdriveplan_childitem.get(4).setOnClickListener(new View.OnClickListener() {
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
                                tv_forwork_showdriveplan_childitem.get(4).setText((year-1911) + "/" + (monthOfYear + 1) + "/" + dayOfMonth + " " + dateFormatOutput("dayofweek",dateToCalendar(target_date)));
                                if(dateFormatOutput("dayofweek",dateToCalendar(target_date)) == "(一)"){todayIsFriday = TRUE;
                                }else{todayIsFriday = FALSE;}
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        //----------------------------------------------------------------------------------------------------------------------------
        // set all spinners data and events
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(main_context,R.array.m_event_array,R.layout.spinner_style_a);
        spin_forwork_showdriveplan_childitem.get(0).setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.spinner_style_a);
        spin_forwork_showdriveplan_childitem.get(0).setOnItemSelectedListener(spnOnItemSelected);
        //----------------------------------------------------------------------------------------------------------------------------
        adapter = ArrayAdapter.createFromResource(main_context,R.array.m_area_array,R.layout.spinner_style_a);
        spin_forwork_showdriveplan_childitem.get(1).setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.spinner_style_a);
        spin_forwork_showdriveplan_childitem.get(1).setOnItemSelectedListener(spnOnItemSelected);
        //----------------------------------------------------------------------------------------------------------------------------
        //List<Members_entity> mambersdata = DataBase.getInstance(main_context).getDataUao().displayAllfromMembers();
        /*ArrayList nameList = new ArrayList<>();
        for (Members_entity aa : mambersdata) {
            nameList.add(aa.getName());
        }
        ArrayAdapter<String> adapteria = new ArrayAdapter<>(main_context, android.R.layout.simple_spinner_item, nameList);*/

        /**=======================================================================================*/
        /**初始化*/
        new Thread(() -> {
            //任務------------------------------------------------------------------------------------------------------------------------
            List<SelfDictionary_entity> dictionarydata = DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryByTags("軍車任務");
            for(SelfDictionary_entity a:dictionarydata){
                eventList.add(a.getWord());
            }
            ArrayAdapter<String> adapteraa = new ArrayAdapter<>(getContext(), R.layout.spinner_style_a, eventList);
            adapteraa.setDropDownViewResource(R.layout.spinner_style_a);
            spin_forwork_showdriveplan_childitem.get(0).setAdapter(adapteraa);
            //spin_database_cars_childitem.get(2).setOnItemSelectedListener(spnOnItemSelected);
            //地區------------------------------------------------------------------------------------------------------------------------
            dictionarydata = DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryByTags("軍車地區");
            for(SelfDictionary_entity a:dictionarydata){
                areaList.add(a.getWord());
            }
            adapteraa = new ArrayAdapter<>(getContext(), R.layout.spinner_style_a, areaList);
            adapteraa.setDropDownViewResource(R.layout.spinner_style_a);
            spin_forwork_showdriveplan_childitem.get(1).setAdapter(adapteraa);

            //線巡回報---------------------------------------------------------------------------------------------------------------------
            reportList.add("無");
            for(SelfDictionary_entity a:dictionarydata){
                reportList.add(a.getWord());
            }
            adapteraa = new ArrayAdapter<>(getContext(), R.layout.spinner_style_b, reportList);
            spin_forwork_showdriveplan_childitem.get(5).setAdapter(adapteraa);
            adapteraa.setDropDownViewResource(R.layout.spinner_style_b);
            spin_forwork_showdriveplan_childitem.get(5).setOnItemSelectedListener(spnOnItemSelected);
            spin_forwork_showdriveplan_childitem.get(6).setAdapter(adapteraa);
            adapteraa.setDropDownViewResource(R.layout.spinner_style_b);
            spin_forwork_showdriveplan_childitem.get(6).setOnItemSelectedListener(spnOnItemSelected);
            spin_forwork_showdriveplan_childitem.get(7).setAdapter(adapteraa);
            adapteraa.setDropDownViewResource(R.layout.spinner_style_b);
            spin_forwork_showdriveplan_childitem.get(7).setOnItemSelectedListener(spnOnItemSelected);
            spin_forwork_showdriveplan_childitem.get(8).setAdapter(adapteraa);
            adapteraa.setDropDownViewResource(R.layout.spinner_style_b);
            spin_forwork_showdriveplan_childitem.get(8).setOnItemSelectedListener(spnOnItemSelected);
            spin_forwork_showdriveplan_childitem.get(9).setAdapter(adapteraa);
            adapteraa.setDropDownViewResource(R.layout.spinner_style_b);
            spin_forwork_showdriveplan_childitem.get(9).setOnItemSelectedListener(spnOnItemSelected);
            //車長----------------------------------------------------------------------------------------------------------------------------
            List<Members_entity> conductorsdata = DataBase.getInstance(main_context).getMembersDataDao().findMembersByPersonalTags("車長");
            for(Members_entity a:conductorsdata){
                conductorList.add(a.getRank() + " " + a.getName());
            }
            adapteraa = new ArrayAdapter<>(getContext(), R.layout.spinner_style_a, conductorList);
            adapteraa.setDropDownViewResource(R.layout.spinner_style_a);
            spin_forwork_showdriveplan_childitem.get(2).setAdapter(adapteraa);
            spin_forwork_showdriveplan_childitem.get(2).setOnItemSelectedListener(spnOnItemSelected);
            //駕駛----------------------------------------------------------------------------------------------------------------------------
            List<Members_entity> driversdata = DataBase.getInstance(main_context).getMembersDataDao().findMembersByPersonalTags("駕駛");
            for(Members_entity a:driversdata){
                driverList.add(a.getRank() + " " + a.getName());
            }
            adapteraa = new ArrayAdapter<>(getContext(), R.layout.spinner_style_a, driverList);
            adapteraa.setDropDownViewResource(R.layout.spinner_style_a);
            spin_forwork_showdriveplan_childitem.get(3).setAdapter(adapteraa);
            spin_forwork_showdriveplan_childitem.get(3).setOnItemSelectedListener(spnOnItemSelected);
            //車號----------------------------------------------------------------------------------------------------------------------------
            List<Items_entity> itemsdata = DataBase.getInstance(main_context).getItemsDataDao().findItemsByitemtype("軍車");
            for(Items_entity a:itemsdata){
                carsList.add(a.getName());
            }
            adapteraa = new ArrayAdapter<>(getContext(), R.layout.spinner_style_a, carsList);
            adapteraa.setDropDownViewResource(R.layout.spinner_style_a);
            spin_forwork_showdriveplan_childitem.get(4).setAdapter(adapteraa);
            spin_forwork_showdriveplan_childitem.get(4).setOnItemSelectedListener(spnOnItemSelected);
        }).start();

        /*adapter = ArrayAdapter.createFromResource(main_context,R.array.m_conductor_array,R.layout.spinner_style_a);
        spin_forwork_showdriveplan_childitem.get(2).setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.spinner_style_a);
        spin_forwork_showdriveplan_childitem.get(2).setOnItemSelectedListener(spnOnItemSelected);*/
        //----------------------------------------------------------------------------------------------------------------------------
        /*adapter = ArrayAdapter.createFromResource(main_context,R.array.m_driver_array,R.layout.spinner_style_a);
        spin_forwork_showdriveplan_childitem.get(3).setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.spinner_style_a);
        spin_forwork_showdriveplan_childitem.get(3).setOnItemSelectedListener(spnOnItemSelected);*/
        //----------------------------------------------------------------------------------------------------------------------------
        /*adapter = ArrayAdapter.createFromResource(main_context,R.array.m_car_array,R.layout.spinner_style_a);
        spin_forwork_showdriveplan_childitem.get(4).setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.spinner_style_a);
        spin_forwork_showdriveplan_childitem.get(4).setOnItemSelectedListener(spnOnItemSelected);*/
        //----------------------------------------------------------------------------------------------------------------------------
        //線巡
        /*adapter = ArrayAdapter.createFromResource(main_context,R.array.m_daywork_simple_array,R.layout.spinner_style_a);
        spin_forwork_showdriveplan_childitem.get(5).setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.spinner_style_a);
        spin_forwork_showdriveplan_childitem.get(5).setOnItemSelectedListener(spnOnItemSelected);
        spin_forwork_showdriveplan_childitem.get(6).setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.spinner_style_a);
        spin_forwork_showdriveplan_childitem.get(6).setOnItemSelectedListener(spnOnItemSelected);
        spin_forwork_showdriveplan_childitem.get(7).setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.spinner_style_a);
        spin_forwork_showdriveplan_childitem.get(7).setOnItemSelectedListener(spnOnItemSelected);*/

        // set '暫存' button event
        btn_forwork_showdriveplan_childitem.get(0).setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                // set result text
                if(dateChanged){
                    tv_forwork_showdriveplan_childitem.get(1).setText(
                            tv_forwork_showdriveplan_childitem.get(1).getText().toString() +
                                    tv_forwork_showdriveplan_childitem.get(0).getText().toString() + "\n" +
                                    "任務:" + spin_forwork_showdriveplan_childitem.get(0).getSelectedItem().toString() + "\n" +
                                    "地點:" + spin_forwork_showdriveplan_childitem.get(1).getSelectedItem().toString() + "\n" +
                                    "車輛:" + spin_forwork_showdriveplan_childitem.get(4).getSelectedItem().toString() + "\n" +
                                    "車長:" + spin_forwork_showdriveplan_childitem.get(2).getSelectedItem().toString() + "\n" +
                                    "駕駛:" + spin_forwork_showdriveplan_childitem.get(3).getSelectedItem().toString() + "\n" + "\n"
                    );
                }else{
                    tv_forwork_showdriveplan_childitem.get(1).setText(
                            tv_forwork_showdriveplan_childitem.get(1).getText().toString() +
                                    "任務:" + spin_forwork_showdriveplan_childitem.get(0).getSelectedItem().toString() + "\n" +
                                    "地點:" + spin_forwork_showdriveplan_childitem.get(1).getSelectedItem().toString() + "\n" +
                                    "車輛:" + spin_forwork_showdriveplan_childitem.get(4).getSelectedItem().toString() + "\n" +
                                    "車長:" + spin_forwork_showdriveplan_childitem.get(2).getSelectedItem().toString() + "\n" +
                                    "駕駛:" + spin_forwork_showdriveplan_childitem.get(3).getSelectedItem().toString() + "\n" + "\n"
                    );
                }

                tv_forwork_showdriveplan_childitem.get(2).setText(
                        /*tv_forwork_showdriveplan_childitem.get(2).getText().toString() +
                                "()至" + spin_forwork_showdriveplan_childitem.get(1).getSelectedItem().toString() + "徑路巡查，" +
                                "由" + spin_forwork_showdriveplan_childitem.get(2).getSelectedItem().toString() + "負責" +
                                "(" +spin_forwork_showdriveplan_childitem.get(4).getSelectedItem().toString() + ")\n" + "\n"*/

                        tv_forwork_showdriveplan_childitem.get(2).getText().toString() +
                                "()至" + spin_forwork_showdriveplan_childitem.get(1).getSelectedItem().toString() + "徑路巡查，" +
                                "由" + spin_forwork_showdriveplan_childitem.get(2).getSelectedItem().toString() + "負責" +
                                "(" +spin_forwork_showdriveplan_childitem.get(4).getSelectedItem().toString() + ")\n" + "\n"
                );
                dateChanged = FALSE;
            }
        });

        // set '清除' button event
        btn_forwork_showdriveplan_childitem.get(1).setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                // set result text
                tv_forwork_showdriveplan_childitem.get(1).setText("");
                tv_forwork_showdriveplan_childitem.get(2).setText("");
                tv_forwork_showdriveplan_childitem.get(3).setText("");

                dateChanged = TRUE;
            }
        });



        // set '合成' button event
        btn_forwork_showdriveplan_childitem.get(3).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /**===================================================================================================================================
                 /**設置搜尋功能**/
                new Thread(() -> {
                    //================================================================================================================
                    //將取得的字串轉譯成Date及Calendar型式================================================================================
                    String dateori =  tv_forwork_showdriveplan_childitem.get(0).getText().toString().substring(0,tv_forwork_showdriveplan_childitem.get(0).getText().toString().length()-4);
                    String dateend =  tv_forwork_showdriveplan_childitem.get(4).getText().toString().substring(0,tv_forwork_showdriveplan_childitem.get(4).getText().toString().length()-4);
                    dateori = (Integer.valueOf(dateori.substring(0,3)) + 1911) + "" +  dateori.substring(3,dateori.length());
                    dateend = (Integer.valueOf(dateend.substring(0,3)) + 1911) + "" +  dateend.substring(3,dateend.length());
                    Date targetdateori = null;
                    Date targetdateend = null;
                    Calendar clr_targetdateori = Calendar.getInstance();
                    Calendar clr_targetdateend = Calendar.getInstance();
                    try {
                        targetdateori = new SimpleDateFormat("yyyy/MM/dd").parse(dateori);
                        targetdateend = new SimpleDateFormat("yyyy/MM/dd").parse(dateend);
                        clr_targetdateori.setTime(targetdateori);
                        clr_targetdateend.setTime(targetdateend);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //================================================================================================================
                    //利用轉譯成Date及Calendar的日期資訊至資料庫搜尋資料並錄入預設陣列==========================================================
                    List<Dispatch_entity> diapatchdata = null;
                    if(targetdateori.compareTo(targetdateend) < 0) {//初始日期比結束日期早(合法日期資料)
                        diapatchdata = DataBase.getInstance(main_context).getDispatchDataDao().findDispatchDataByTime(
                                (clr_targetdateori.get(Calendar.YEAR) - 1911) + "/" + (clr_targetdateori.get(Calendar.MONTH) + 1) + "/" + clr_targetdateori.get(Calendar.DAY_OF_MONTH));
                        //初始日期跟結束日期之間的差距(差幾天)
                        int dis = clr_targetdateend.get(Calendar.DAY_OF_YEAR) - clr_targetdateori.get(Calendar.DAY_OF_YEAR);
                        //依據初始日期跟結束日期之間的差距(差幾天)去遍歷資料庫相關的日期
                        for (int i = 1; i <= dis; i++) {
                            //日期變更(往後推一天)
                            clr_targetdateori.add(Calendar.DATE, 1);
                            //除錯-輸出搜尋之日期
                            Log.d("Current_date", "與初始日期間隔 : " + i + " 天 -> " + (clr_targetdateori.get(Calendar.YEAR)-1911) + "年" + (clr_targetdateori.get(Calendar.MONTH)+1) + "月" + clr_targetdateori.get(Calendar.DAY_OF_MONTH) + "日" + "");
                            //檢查取出資料是否為空
                            if(DataBase.getInstance(main_context).getDispatchDataDao().findDispatchDataByTime((clr_targetdateori.get(Calendar.YEAR) - 1911) + "/" + (clr_targetdateori.get(Calendar.MONTH) + 1) + "/" + clr_targetdateori.get(Calendar.DAY_OF_MONTH)).size() !=0){
                                //資料不為空-資料錄入預設陣列
                                for (Dispatch_entity a : DataBase.getInstance(main_context).getDispatchDataDao().findDispatchDataByTime(
                                        (clr_targetdateori.get(Calendar.YEAR) - 1911) + "/" + (clr_targetdateori.get(Calendar.MONTH) + 1) + "/" + clr_targetdateori.get(Calendar.DAY_OF_MONTH))) {
                                    diapatchdata.add(a);
                                }
                            }else{
                                //資料為空值-除錯
                                //除錯-輸出搜尋之日期
                                Log.d("Current_date", "無派車資料 -> " + (clr_targetdateori.get(Calendar.YEAR)-1911) + "年" + (clr_targetdateori.get(Calendar.MONTH)+1) + "月" + clr_targetdateori.get(Calendar.DAY_OF_MONTH) + "日" + "");
                            }
                        }
                        //================================================================================================================
                        //清除字串資料
                        tempdriveplan = "";
                        tempworkplan = "";
                        //將資料庫取出之資料轉換為便於閱讀的格式
                        String tempdate = "";//暫存日期(避免重複輸出日期)
                        //檢查取出資料是否為空
                        if(diapatchdata.size() != 0) {
                            for (Dispatch_entity a : diapatchdata) {
                                if(tempdate.compareTo(a.getTime()) != 0){//若暫存日期與新讀到的日期不同->輸出新日期並將當前日期存到暫存日期
                                    tempdriveplan += a.getTime() + "\n";
                                    tempdate = a.getTime();
                                }
                                tempdriveplan += "任務:" + a.getEvent() + "\n" + "地點:" + a.getArea() + "\n" + "車輛:" + a.getCar() + "\n" + "車長:" + a.getConductor() + "\n" + "駕駛:" + a.getDriver() + "\n" + "\n";
                                tempworkplan += "()至" + a.getArea() + "徑路巡查，" + "由" + a.getConductor() + "負責" + "(" + a.getCar() + ")\n" + "\n";
                            }
                        }else{
                            tempdriveplan = "查無資料";
                            tempworkplan = "";
                        }
                    }else if(targetdateori.compareTo(targetdateend) > 0) {//初始日期比結束日期晚(不合法日期資料)
                        tempdriveplan = "不合法日期-結束日期不可比初始日期早";
                        tempworkplan = "";
                    }else if(targetdateori.compareTo(targetdateend) == 0) {//初始日期與結束日期相同(合法日期資料)
                        diapatchdata = DataBase.getInstance(main_context).getDispatchDataDao().findDispatchDataByTime(
                                (clr_targetdateori.get(Calendar.YEAR)-1911) + "/" + (clr_targetdateori.get(Calendar.MONTH) + 1) + "/" + clr_targetdateori.get(Calendar.DAY_OF_MONTH));
                        tempdriveplan = "";
                        tempworkplan = "";
                        //將資料庫取出之資料轉換為便於閱讀的格式
                        //檢查取出資料是否為空
                        if(diapatchdata.size() != 0) {
                            tempdriveplan = diapatchdata.get(0).getTime() + "\n";
                            for (Dispatch_entity a : diapatchdata) {
                                tempdriveplan += "任務:" + a.getEvent() + "\n" + "地點:" + a.getArea() + "\n" + "車輛:" + a.getCar() + "\n" + "車長:" + a.getConductor() + "\n" + "駕駛:" + a.getDriver() + "\n" + "\n";
                                tempworkplan += "()至" + a.getArea() + "徑路巡查，" + "由" + a.getConductor() + "負責" + "(" + a.getCar() + ")\n" + "\n";
                            }
                        }else{
                            tempdriveplan = "查無資料";
                            tempworkplan = "";
                        }
                    }else{
                        tempdriveplan = "看到這則訊息應該就沒救了";
                        tempworkplan = "";
                    }

                    //View元件設定直接寫在外面會導致程式Crash
                    getActivity().runOnUiThread(() -> {
                        tv_forwork_showdriveplan_childitem.get(1).setText(tempdriveplan);
                        tv_forwork_showdriveplan_childitem.get(2).setText(tempworkplan);
                    });
                }).start();
                /**===================================================================================================================================**/
            }
        });

        // set '合成' button event
        btn_forwork_showdriveplan_childitem.get(2).setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //----------------------------------------------------------------------------------------------------------------------------
                /*ArrayList<Integer> worknum = new ArrayList<Integer>();
                worknum.add(spin_forwork_showdriveplan_childitem.get(5).getSelectedItemPosition());
                worknum.add(spin_forwork_showdriveplan_childitem.get(6).getSelectedItemPosition());
                worknum.add(spin_forwork_showdriveplan_childitem.get(7).getSelectedItemPosition());
                String daywork_all = "";
                int dayworknum = 1;
                for(int i = 0; i < worknum.size(); i ++){
                    if(worknum.get(i) != 0){
                        daywork_all = daywork_all + "(" + dayworknum + ")" + AppData.M_EVENT_RESULTS[worknum.get(i)];
                        dayworknum++;
                    }
                }*/

                ArrayList<String> workstring = new ArrayList<String>();
                //讀取下拉式選單資料(字串)並轉成陣列
                for(int i = 5; i <= 9; i++){
                    if(spin_forwork_showdriveplan_childitem.get(i).getSelectedItemPosition() != 0){
                        workstring.add(spin_forwork_showdriveplan_childitem.get(i).getSelectedItem().toString());
                    }
                }
                new Thread(() -> {
                    int dayworknum = 1;
                    daywork_all = "";
                    for(int i = 0; i < workstring.size(); i ++){
                        if(DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryByWords(workstring.get(i)) != null){
                            daywork_all = daywork_all + "(" + dayworknum + ")" + DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryByWords(workstring.get(i)) + "\n";
                            dayworknum++;
                        }
                    }
                }).start();

                /**-------------------------------------------------------------------------------------------------------------------------------------**/
                String aa ;
                if(todayIsFriday){aa = "4.週一工作預劃:";
                }else{aa = "4.明日工作預劃:";}
                // set result text
                tv_forwork_showdriveplan_childitem.get(3).setText(
                    "1.派遣車輛" + "\n" +
                        tv_forwork_showdriveplan_childitem.get(1).getText().toString() +
                        //"今日完成工作:\n" + AppData.M_EVENT_RESULTS[worknum_a] + AppData.M_EVENT_RESULTS[worknum_b] + AppData.M_EVENT_RESULTS[worknum_c] + "\n" +
                        "2.今日1800後還在營外車輛：無" + "\n\n"+
                        "3.今日完成任務:\n" + daywork_all + "\n" +
                        aa + "\n" +
                        tv_forwork_showdriveplan_childitem.get(2).getText().toString()
                );
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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
                    ArrayAdapter<String> adapteraa = (ArrayAdapter)spin_forwork_showdriveplan_childitem.get(2).getAdapter();
                    adapteraa.notifyDataSetChanged();
                    //駕駛更新-------------------------------------------------------------------------------------------------------------------------------
                    mambersdata = DataBase.getInstance(main_context).getMembersDataDao().findMembersByPersonalTags("駕駛");
                    driverList.clear();
                    for(Members_entity a:mambersdata){
                        driverList.add(a.getRank() + " " + a.getName());
                    }
                    adapteraa = (ArrayAdapter)spin_forwork_showdriveplan_childitem.get(3).getAdapter();
                    adapteraa.notifyDataSetChanged();
                    //車號更新-------------------------------------------------------------------------------------------------------------------------------
                    /*List<Items_entity> itemsdata = DataBase.getInstance(main_context).getItemsDataDao().findItemsByitemtype("軍車");
                    carsList.clear();
                    for(Items_entity a:itemsdata){
                        carsList.add(a.getName());
                    }
                    adapteraa = (ArrayAdapter)spin_forwork_showdriveplan_childitem.get(4).getAdapter();
                    adapteraa.notifyDataSetChanged();*/
                }).start();
                break;
            case "Cars":
                new Thread(() -> {
                    List<Items_entity> itemsdata = DataBase.getInstance(main_context).getItemsDataDao().findItemsByitemtype("軍車");
                    carsList.clear();
                    for(Items_entity a:itemsdata){
                        carsList.add(a.getName());
                    }
                    ArrayAdapter<String> adapteraa = (ArrayAdapter)spin_forwork_showdriveplan_childitem.get(4).getAdapter();
                    //adapteraa.notifyDataSetChanged();
                }).start();
                break;
            case "Dictionary":
                new Thread(() -> {//任務更新-------------------------------------------------------------------------------------------------------------------------------
                    List<SelfDictionary_entity> dictionarydata = DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryByTags("軍車任務");
                    eventList.clear();
                    for(SelfDictionary_entity a:dictionarydata){
                        eventList.add(a.getWord());
                    }
                    ArrayAdapter<String> adapteraa = (ArrayAdapter)spin_forwork_showdriveplan_childitem.get(0).getAdapter();
                    //adapteraa.notifyDataSetChanged();
                    //地點更新-------------------------------------------------------------------------------------------------------------------------------
                    dictionarydata = DataBase.getInstance(main_context).getSelfDictionaryDataDao().findDictionaryByTags("軍車地區");
                    areaList.clear();
                    for(SelfDictionary_entity a:dictionarydata){
                        areaList.add(a.getWord());
                    }
                    adapteraa = (ArrayAdapter)spin_forwork_showdriveplan_childitem.get(1).getAdapter();
                    //adapteraa.notifyDataSetChanged();
                }).start();
                break;
        }
    }
/**
 * 改失敗
    public void spinnersLiveupdate(String target){
        new Thread(() -> {
            switch (target) {
                case "Conductor":
                    List<Members_entity> membersdata = DataBase.getInstance(main_context).getMembersDataDao().findMembersByPersonalTags("車長");
                    conductorList.clear();
                    for(Members_entity a:membersdata){
                        conductorList.add(a.getRank() + " " + a.getName());
                    }
                    ArrayAdapter<String> adapteraa = (ArrayAdapter)spin_forwork_showdriveplan_childitem.get(2).getAdapter();
                    adapteraa.notifyDataSetChanged();
                    break;
                case "Driver":
                    membersdata = DataBase.getInstance(main_context).getMembersDataDao().findMembersByPersonalTags("駕駛");
                    driverList.clear();
                    for(Members_entity a:membersdata){
                        driverList.add(a.getRank() + " " + a.getName());
                    }
                    adapteraa = (ArrayAdapter)spin_forwork_showdriveplan_childitem.get(3).getAdapter();
                    adapteraa.notifyDataSetChanged();
                    break;
                case "Cars":
                    List<Items_entity> itemsdata = DataBase.getInstance(main_context).getItemsDataDao().findItemsByitemtype("軍車");
                    carsList.clear();
                    for(Items_entity a:itemsdata){
                        carsList.add(a.getName());
                    }
                    adapteraa = (ArrayAdapter)spin_forwork_showdriveplan_childitem.get(4).getAdapter();
                    adapteraa.notifyDataSetChanged();
                    break;
                case "Members":
                    //車長更新-------------------------------------------------------------------------------------------------------------------------------
                    List<Members_entity> mambersdata = DataBase.getInstance(main_context).getMembersDataDao().findMembersByPersonalTags("車長");
                    conductorList.clear();
                    for(Members_entity a:mambersdata){
                        conductorList.add(a.getRank() + " " + a.getName());
                    }
                    adapteraa = (ArrayAdapter)spin_forwork_showdriveplan_childitem.get(2).getAdapter();
                    adapteraa.notifyDataSetChanged();
                    //駕駛更新-------------------------------------------------------------------------------------------------------------------------------
                    mambersdata = DataBase.getInstance(main_context).getMembersDataDao().findMembersByPersonalTags("駕駛");
                    driverList.clear();
                    for(Members_entity a:mambersdata){
                        driverList.add(a.getRank() + " " + a.getName());
                    }
                    adapteraa = (ArrayAdapter)spin_forwork_showdriveplan_childitem.get(3).getAdapter();
                    adapteraa.notifyDataSetChanged();
                    break;
            }
        }).start();
    }
 **/

    //設置對外端口
    public static Fragment_T2_Forwork_DriveplanOutput getInstance() {
        return instance;
    }
}
