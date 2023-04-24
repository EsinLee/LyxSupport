package com.yxproject.lyxsupport;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.yxproject.application_data.AppData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static android.content.Context.NOTIFICATION_SERVICE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


/**
 * 第二層
 */
public class ViewPagerItemAdapter_t1 extends RecyclerView.Adapter<ViewPagerItemAdapter_t1.ViewHolder> {
    //Input MainActivity
    private Context context;
    //Initialize variable
    private ArrayList<String> viewtype; //View type

    private int type_transform = 0; //Position record

    public ViewPagerItemAdapter_t1(Context context, String[] viewtypeinput){
        this.context = context;
        viewtype = new ArrayList<String>();
        for (int i = 0; i < viewtypeinput.length; i++) {
            viewtype.add("" + viewtypeinput[i]);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Initial variable (start) (one page one group) ===========================================
        // FEATURE_MEMO
        ArrayList<RecyclerView> rcy_feature_memo_childitem = new ArrayList<RecyclerView>();
        ArrayList<CalendarView> calendar_feature_memo_childitem = new ArrayList<CalendarView>();

        // FEATURE_TEXT_EDITOR
        ArrayList<TextView> tv_feature_textedit_childitem = new ArrayList<TextView>();

        // FEATURE_ACCOUNT_BOOK
        ArrayList<Button> tv_feature_accountbook_childitem = new ArrayList<Button>();

        // FEATURE_RESOURCES_MANAGEMENT
        ArrayList<RecyclerView> rcy_feature_resource_childitem = new ArrayList<RecyclerView>();

        // FEATURE_DATABASE_MANAGEMENT
        ArrayList<TextView> tv_database_childitem = new ArrayList<TextView>();
        ArrayList<Button> btn_database_childitem = new ArrayList<Button>();
        ArrayList<RecyclerView> rcy_database_childitem = new ArrayList<RecyclerView>();

        //------------------------------------------------------------------------------------------

        // FORWORK_CALENDAR
        ArrayList<CalendarView> calendar_forwork_calendar_childitem = new ArrayList<CalendarView>();
        ArrayList<ViewPager2> vp2_dorwork_calendar_childitem = new ArrayList<ViewPager2>();

        // FORWORK_DRIVE_PLAN
        ArrayList<Spinner> spin_forwork_showdriveplan_childitem = new ArrayList<Spinner>();
        ArrayList<TextView> tv_forwork_showdriveplan_childitem = new ArrayList<TextView>();
        ArrayList<Button> btn_forwork_showdriveplan_childitem = new ArrayList<Button>();
        ArrayList<SeekBar> sekb_forwork_showdriveplan_childitem = new ArrayList<SeekBar>();

        Boolean dateChanged = TRUE;

        // FORWORK_RESOURCES
        ArrayList<ViewPager2> vp2_forwork_resources_childitem = new ArrayList<ViewPager2>();
        ArrayList<TabLayout> tabl_forwork_resources_childitem = new ArrayList<TabLayout>();

        // FORWORK_WORKPLAN
        ArrayList<TextView> tv_forwork_workplan_childitem = new ArrayList<TextView>();

        // Initial variable (end) ==================================================================

        RecyclerViewItemAdapter recyclerViewItemAdapter;

        //TextView tv_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 主recyclerview傳入的View裡面的子recyclerview
            switch (viewtype.get(type_transform)) {
                case "FEATURE_MEMO":

                    break;

                case "FEATURE_TEXT_EDITOR":

                    break;

                case "FEATURE_ACCOUNT_BOOK":
                    tv_feature_accountbook_childitem.add((Button) itemView.findViewById(R.id.bt_clear_actacbook));
                    break;

                case "FEATURE_RESOURCES_MANAGEMENT":
                    rcy_feature_resource_childitem.add((RecyclerView) itemView.findViewById(R.id.rcv_main_acthome));
                    break;
                case "FEATURE_DATABASE_MANAGEMENT":
                    tv_database_childitem.add((TextView) itemView.findViewById(R.id.edtv_name_actdatabase));
                    tv_database_childitem.add((TextView) itemView.findViewById(R.id.edtv_phone_actdatabase));
                    tv_database_childitem.add((TextView) itemView.findViewById(R.id.edtv_rank_actdatabase));
                    tv_database_childitem.add((TextView) itemView.findViewById(R.id.edtv_job_actdatabase));
                    tv_database_childitem.add((TextView) itemView.findViewById(R.id.edtv_else_actdatabase));

                    btn_database_childitem.add((Button) itemView.findViewById(R.id.bt_modify_actdatabase));
                    btn_database_childitem.add((Button) itemView.findViewById(R.id.bt_clear_actdatabase));
                    btn_database_childitem.add((Button) itemView.findViewById(R.id.bt_create_actdatabase));

                    rcy_database_childitem.add((RecyclerView) itemView.findViewById(R.id.rcy_main_actdatabase));
                    break;

               case "FORWORK_CALENDAR":
                    calendar_forwork_calendar_childitem.add((CalendarView) itemView.findViewById(R.id.calendar_main_actcalendar));
                    vp2_dorwork_calendar_childitem.add((ViewPager2) itemView.findViewById(R.id.vp_main_actcalendar));
                    break;

                case "FORWORK_DRIVE_PLAN":
                    spin_forwork_showdriveplan_childitem.add((Spinner) itemView.findViewById(R.id.spin_cont_event_actshowdriveplan));
                    spin_forwork_showdriveplan_childitem.add((Spinner) itemView.findViewById(R.id.spin_cont_area_actshowdriveplan));
                    spin_forwork_showdriveplan_childitem.add((Spinner) itemView.findViewById(R.id.spin_cont_conductor_actshowdriveplan));
                    spin_forwork_showdriveplan_childitem.add((Spinner) itemView.findViewById(R.id.spin_cont_driver_actshowdriveplan));
                    spin_forwork_showdriveplan_childitem.add((Spinner) itemView.findViewById(R.id.spin_cont_car_actshowdriveplan));
                    spin_forwork_showdriveplan_childitem.add((Spinner) itemView.findViewById(R.id.spin_cont_daywork_actshowdriveplan_a));
                    spin_forwork_showdriveplan_childitem.add((Spinner) itemView.findViewById(R.id.spin_cont_daywork_actshowdriveplan_b));
                    spin_forwork_showdriveplan_childitem.add((Spinner) itemView.findViewById(R.id.spin_cont_daywork_actshowdriveplan_c));

                    btn_forwork_showdriveplan_childitem.add((Button) itemView.findViewById(R.id.bt_sent_actshowdriveplan));
                    btn_forwork_showdriveplan_childitem.add((Button) itemView.findViewById(R.id.bt_clear_actshowdriveplan));
                    btn_forwork_showdriveplan_childitem.add((Button) itemView.findViewById(R.id.bt_addition_actshowdriveplan));

                    tv_forwork_showdriveplan_childitem.add((TextView) itemView.findViewById(R.id.tv_dateday_actshowdriveplan));
                    tv_forwork_showdriveplan_childitem.add((TextView) itemView.findViewById(R.id.tv_result_actshowdriveplan));
                    tv_forwork_showdriveplan_childitem.add((TextView) itemView.findViewById(R.id.tv_tommorrow_actshowdriveplan));
                    tv_forwork_showdriveplan_childitem.add((TextView) itemView.findViewById(R.id.tv_addition_actshowdriveplan));

                    break;

                case "FORWORK_RESOURCES":
                    vp2_forwork_resources_childitem.add((ViewPager2) itemView.findViewById(R.id.vp_main_actworkresourcesmanagement));
                    tabl_forwork_resources_childitem.add((TabLayout) itemView.findViewById(R.id.tabl_main_actworkresourcesmanagement));
                    break;

                case "FORWORK_WORKPLAN":
                    tv_forwork_workplan_childitem.add((TextView) itemView.findViewById(R.id.tv_dateday_actworkplan));
                    break;
            }
            //tv_title = itemView.findViewById(R.id.tv_title);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        final ViewPagerItemAdapter_t1.ViewHolder holder;

        ViewPagerItemAdapter_t2 viewpageritemadapter;

        switch (viewtype.get(type_transform)){
            case "FEATURE_MEMO":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_t2_workplan, parent, false);
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
                holder.rcy_feature_memo_childitem.get(0).setLayoutManager(childLayoutManager);
                holder.rcy_feature_memo_childitem.get(0).setAdapter(holder.recyclerViewItemAdapter = new RecyclerViewItemAdapter(context, AppData.TEST_NAME_CN, AppData.TEST_ICON, AppData.TEST_DESCRIPTION,1));


                type_transform++;
                break;
            case"FEATURE_TEXT_EDITOR":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_t2_texteditor, parent, false);
                holder = new ViewHolder(view);

                type_transform++;
                break;
            case "FEATURE_ACCOUNT_BOOK":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_t2_accountbook, parent, false);
                holder = new ViewHolder(view);

                holder.tv_feature_accountbook_childitem.get(0).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

                        Notification.Builder builder = new Notification.Builder(context);
                        builder.setSmallIcon(R.drawable.lyx_app_smallicon)
                                .setTicker("xx特價")
                                .setContentTitle("標題example")
                                .setContentText("內容example");

                        Notification notification = builder.build();
                        notificationManager.cancel(0); // 移除id值為0的通知
                        notificationManager.notify(0, notification);
                    }
                });

                type_transform++;
                break;
            case "FEATURE_RESOURCES_MANAGEMENT":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_t2_resourcesmanagement, parent, false);
                holder = new ViewHolder(view);

                type_transform++;
                break;
            case "FEATURE_DATABASE_MANAGEMENT":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_t2_databasemanagement, parent, false);
                holder = new ViewHolder(view);

                type_transform++;
                break;
            case "FORWORK_CALENDAR":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_t2_calendar, parent, false);
                holder = new ViewHolder(view);

                type_transform++;
                break;

            case "FORWORK_DRIVE_PLAN":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_t2_showdriveplan, parent, false);
                holder = new ViewHolder(view);

                Calendar cld = Calendar.getInstance();
                cld.add(cld.DATE,+1);
                holder.tv_forwork_showdriveplan_childitem.get(0).setText((cld.get(Calendar.YEAR)-1911) + "/" + (cld.get(Calendar.MONTH) + 1) + "/" + cld.get(Calendar.DAY_OF_MONTH) + " " + dateFormatOutput("dayofweek",cld));
                holder.tv_forwork_showdriveplan_childitem.get(0).setInputType(InputType.TYPE_NULL);
                holder.tv_forwork_showdriveplan_childitem.get(0).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar cldr = Calendar.getInstance();
                        int day = cldr.get(Calendar.DAY_OF_MONTH);
                        int month = cldr.get(Calendar.MONTH);
                        int year = cldr.get(Calendar.YEAR);
                        int dayodweek = cldr.get(Calendar.DAY_OF_WEEK);
                        holder.dateChanged = TRUE;
                        // date picker dialog
                        DatePickerDialog picker;
                        picker = new DatePickerDialog(context,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        Date target_date = new Date(year, monthOfYear, dayOfMonth-1);
                                        holder.tv_forwork_showdriveplan_childitem.get(0).setText((year-1911) + "/" + (monthOfYear + 1) + "/" + dayOfMonth + " " + dateFormatOutput("dayofweek",dateToCalendar(target_date)));
                                    }
                                }, year, month, day);
                        picker.show();
                    }
                });

                // set all spinners data and events
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,R.array.m_event_array,R.layout.spinner_style_a);
                holder.spin_forwork_showdriveplan_childitem.get(0).setAdapter(adapter);
                adapter.setDropDownViewResource(R.layout.spinner_style_a);
                holder.spin_forwork_showdriveplan_childitem.get(0).setOnItemSelectedListener(spnOnItemSelected);
                //----------------------------------------------------------------------------------------------------------------------------
                adapter = ArrayAdapter.createFromResource(context,R.array.m_area_array,R.layout.spinner_style_a);
                holder.spin_forwork_showdriveplan_childitem.get(1).setAdapter(adapter);
                adapter.setDropDownViewResource(R.layout.spinner_style_a);
                holder.spin_forwork_showdriveplan_childitem.get(1).setOnItemSelectedListener(spnOnItemSelected);
                //----------------------------------------------------------------------------------------------------------------------------
                adapter = ArrayAdapter.createFromResource(context,R.array.m_conductor_array,R.layout.spinner_style_a);
                holder.spin_forwork_showdriveplan_childitem.get(2).setAdapter(adapter);
                adapter.setDropDownViewResource(R.layout.spinner_style_a);
                holder.spin_forwork_showdriveplan_childitem.get(2).setOnItemSelectedListener(spnOnItemSelected);
                //----------------------------------------------------------------------------------------------------------------------------
                adapter = ArrayAdapter.createFromResource(context,R.array.m_driver_array,R.layout.spinner_style_a);
                holder.spin_forwork_showdriveplan_childitem.get(3).setAdapter(adapter);
                adapter.setDropDownViewResource(R.layout.spinner_style_a);
                holder.spin_forwork_showdriveplan_childitem.get(3).setOnItemSelectedListener(spnOnItemSelected);
                //----------------------------------------------------------------------------------------------------------------------------
                adapter = ArrayAdapter.createFromResource(context,R.array.m_car_array,R.layout.spinner_style_a);
                holder.spin_forwork_showdriveplan_childitem.get(4).setAdapter(adapter);
                adapter.setDropDownViewResource(R.layout.spinner_style_a);
                holder.spin_forwork_showdriveplan_childitem.get(4).setOnItemSelectedListener(spnOnItemSelected);
                //----------------------------------------------------------------------------------------------------------------------------
                adapter = ArrayAdapter.createFromResource(context,R.array.m_daywork_simple_array,R.layout.spinner_style_a);
                holder.spin_forwork_showdriveplan_childitem.get(5).setAdapter(adapter);
                adapter.setDropDownViewResource(R.layout.spinner_style_a);
                holder.spin_forwork_showdriveplan_childitem.get(5).setOnItemSelectedListener(spnOnItemSelected);
                holder.spin_forwork_showdriveplan_childitem.get(6).setAdapter(adapter);
                adapter.setDropDownViewResource(R.layout.spinner_style_a);
                holder.spin_forwork_showdriveplan_childitem.get(6).setOnItemSelectedListener(spnOnItemSelected);
                holder.spin_forwork_showdriveplan_childitem.get(7).setAdapter(adapter);
                adapter.setDropDownViewResource(R.layout.spinner_style_a);
                holder.spin_forwork_showdriveplan_childitem.get(7).setOnItemSelectedListener(spnOnItemSelected);

                // set '暫存' button event
                holder.btn_forwork_showdriveplan_childitem.get(0).setOnClickListener(new View.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        // set result text
                        if(holder.dateChanged){
                            holder.tv_forwork_showdriveplan_childitem.get(1).setText(
                                holder.tv_forwork_showdriveplan_childitem.get(1).getText().toString() +
                                holder.tv_forwork_showdriveplan_childitem.get(0).getText().toString() + "\n" +
                                "任務:" + holder.spin_forwork_showdriveplan_childitem.get(0).getSelectedItem().toString() + "\n" +
                                "地區:" + holder.spin_forwork_showdriveplan_childitem.get(1).getSelectedItem().toString() + "\n" +
                                "車長:" + holder.spin_forwork_showdriveplan_childitem.get(2).getSelectedItem().toString() + "\n" +
                                "駕駛:" + holder.spin_forwork_showdriveplan_childitem.get(3).getSelectedItem().toString() + "\n" +
                                "車輛:" + holder.spin_forwork_showdriveplan_childitem.get(4).getSelectedItem().toString() + "\n" + "\n"
                            );
                        }else{
                            holder.tv_forwork_showdriveplan_childitem.get(1).setText(
                                holder.tv_forwork_showdriveplan_childitem.get(1).getText().toString() +
                                "任務:" + holder.spin_forwork_showdriveplan_childitem.get(0).getSelectedItem().toString() + "\n" +
                                "地區:" + holder.spin_forwork_showdriveplan_childitem.get(1).getSelectedItem().toString() + "\n" +
                                "車長:" + holder.spin_forwork_showdriveplan_childitem.get(2).getSelectedItem().toString() + "\n" +
                                "駕駛:" + holder.spin_forwork_showdriveplan_childitem.get(3).getSelectedItem().toString() + "\n" +
                                "車輛:" + holder.spin_forwork_showdriveplan_childitem.get(4).getSelectedItem().toString() + "\n" + "\n"
                            );
                        }

                        holder.tv_forwork_showdriveplan_childitem.get(2).setText(
                                holder.tv_forwork_showdriveplan_childitem.get(2).getText().toString() +
                                    /*holder.spin_forwork_showdriveplan_childitem.get(1).getSelectedItem().toString() + */
                                    "()至" + holder.spin_forwork_showdriveplan_childitem.get(1).getSelectedItem().toString() + "徑路巡查，" +
                                    "由" + holder.spin_forwork_showdriveplan_childitem.get(2).getSelectedItem().toString() + "負責" +
                                    "(" +holder.spin_forwork_showdriveplan_childitem.get(4).getSelectedItem().toString() + ")\n" + "\n"
                        );
                        holder.dateChanged = FALSE;
                    }
                });

                // set '清除' button event
                holder.btn_forwork_showdriveplan_childitem.get(1).setOnClickListener(new View.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        // set result text
                        holder.tv_forwork_showdriveplan_childitem.get(1).setText("");
                        holder.tv_forwork_showdriveplan_childitem.get(2).setText("");
                        holder.tv_forwork_showdriveplan_childitem.get(3).setText("");

                        holder.dateChanged = TRUE;
                    }
                });

                // set '合成' button event
                holder.btn_forwork_showdriveplan_childitem.get(2).setOnClickListener(new View.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        ArrayList<Integer> worknum = new ArrayList<Integer>();
                        worknum.add(holder.spin_forwork_showdriveplan_childitem.get(5).getSelectedItemPosition());
                        worknum.add(holder.spin_forwork_showdriveplan_childitem.get(6).getSelectedItemPosition());
                        worknum.add(holder.spin_forwork_showdriveplan_childitem.get(7).getSelectedItemPosition());
                        int worknum_a = holder.spin_forwork_showdriveplan_childitem.get(5).getSelectedItemPosition();
                        int worknum_b = holder.spin_forwork_showdriveplan_childitem.get(6).getSelectedItemPosition();
                        int worknum_c = holder.spin_forwork_showdriveplan_childitem.get(7).getSelectedItemPosition();
                        String daywork_all = "";
                        int dayworknum = 1;
                        for(int i = 0; i < worknum.size(); i ++){
                            if(worknum.get(i) != 0){
                                daywork_all = daywork_all + "(" + dayworknum + ")" + AppData.M_EVENT_RESULTS[worknum.get(i)];
                                dayworknum++;
                            }
                        }
                        
                        // set result text
                        holder.tv_forwork_showdriveplan_childitem.get(3).setText(
                            //holder.tv_forwork_showdriveplan_childitem.get(1).getText().toString() +
                            "1.明日派車" + "\n" +
                            holder.tv_forwork_showdriveplan_childitem.get(1).getText().toString() +
                            //"今日完成工作:\n" + AppData.M_EVENT_RESULTS[worknum_a] + AppData.M_EVENT_RESULTS[worknum_b] + AppData.M_EVENT_RESULTS[worknum_c] + "\n" +
                            "2.今日1800後還在營外車輛：無" + "\n\n"+
                            "3.今日完成任務:\n" + daywork_all + "\n" +
                            "4.明日工作預劃:\n" +
                            holder.tv_forwork_showdriveplan_childitem.get(2).getText().toString()

                        );
                        //holder.tv_forwork_showdriveplan_childitem.get(3).setMovementMethod(ScrollingMovementMethod.getInstance());

                    }
                });

                type_transform++;
                break;

            case "FORWORK_RESOURCES":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_t2_workresourcesmanagement, parent, false);
                holder = new ViewHolder(view);

                viewpageritemadapter = new ViewPagerItemAdapter_t2(context, AppData.FORWORK_RESOURCES_VIEWTYPE);
                //Set clip padding
                holder.vp2_forwork_resources_childitem.get(0).setClipToPadding(false);
                //Set clip children
                holder.vp2_forwork_resources_childitem.get(0).setClipChildren(false);
                //Set page limit
                holder.vp2_forwork_resources_childitem.get(0).setOffscreenPageLimit(3);
                //Set default start position
                holder.vp2_forwork_resources_childitem.get(0).getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
                //Set adapter on vertical viewpaper
                holder.vp2_forwork_resources_childitem.get(0).setAdapter(viewpageritemadapter);
                //Set main ViewPagers can slide or not
                holder.vp2_forwork_resources_childitem.get(0).setUserInputEnabled(false); //true:allow，false：disallow
                //-----------------------------------------------------------------------------------------------
                // Need to add tablayout events
                //context.connectFeatureTablayoutWithViewpager2(holder.tabl_childitem(0));

                // tablayout event in feature page
                holder.tabl_forwork_resources_childitem.get(0).addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        switch(tab.getPosition()) {
                            case 0:
                                holder.vp2_forwork_resources_childitem.get(0).setCurrentItem(0, true);
                                break;
                            case 1:
                                holder.vp2_forwork_resources_childitem.get(0).setCurrentItem(1, true);
                                break;
                            case 2:
                                holder.vp2_forwork_resources_childitem.get(0).setCurrentItem(2, true);
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

                type_transform++;
                break;

            case "FORWORK_WORKPLAN":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_error, parent, false);
                holder = new ViewHolder(view);
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
    public void onBindViewHolder(@NonNull ViewPagerItemAdapter_t1.ViewHolder holder, int position) {
        //Set image on image view
        //holder.imageView.setBackgroundResource(images[position]);
        //holder.tv_title.setText(texts[position]);
    }

    @Override
    public int getItemCount() {
        //Return array length
        return viewtype.size();
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

    private String todayDateOutput(String type){
        //Date predate = new Date();
        Calendar now = Calendar.getInstance();
        switch (type){
            case"year_ad":
                return "" + now.get(Calendar.YEAR);
            case"year_tw":
                return "" + (now.get(Calendar.YEAR) - 1911);
            case"month":
                return "" + (now.get(Calendar.MONTH) + 1);
            case"day":
                return "" + now.get(Calendar.DATE);
            case"dayofweek":
                switch(now.get(Calendar.DAY_OF_WEEK)){
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

    private String dayInWeekOutput(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setTimeZone(TimeZone.getTimeZone("GMT"));

        switch(c.get(Calendar.DAY_OF_WEEK)){
            case 1:return "(日)";
            case 2:return "(一)";
            case 3:return "(二)";
            case 4:return "(三)";
            case 5:return "(四)";
            case 6:return "(五)";
            case 7:return "(六)";
            default:return "error";
        }
    }

    public static Calendar dateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

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

}