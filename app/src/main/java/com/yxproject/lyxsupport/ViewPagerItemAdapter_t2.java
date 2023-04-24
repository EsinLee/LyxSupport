package com.yxproject.lyxsupport;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 第三層
 */
public class ViewPagerItemAdapter_t2 extends RecyclerView.Adapter<ViewPagerItemAdapter_t2.ViewHolder> {
    //Input MainActivity
    private Context context;
    //Initialize variable
    private ArrayList<String> viewtype; //View type

    RoomRecyclerViewAdapter roomRecyclerViewAdapter;

    private int type_transform = 0; //Position record

    public ViewPagerItemAdapter_t2(Context context, String[] viewtypeinput){
        this.context = context;
        viewtype = new ArrayList<String>();
        for (int i = 0; i < viewtypeinput.length; i++) {
            viewtype.add("" + viewtypeinput[i]);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Initial variable (start) (one page one group) ===========================================

        // RESOURCES_MEMBERS
        ArrayList<TextView> tv_database_members_childitem = new ArrayList<TextView>();
        ArrayList<Button> btn_database_members_childitem = new ArrayList<Button>();
        ArrayList<RecyclerView> rcy_database_members_childitem = new ArrayList<RecyclerView>();

        // RESOURCES_ITEMS
        ArrayList<TextView> tv_database_childitem = new ArrayList<TextView>();
        ArrayList<Button> btn_database_childitem = new ArrayList<Button>();
        ArrayList<RecyclerView> rcy_database_childitem = new ArrayList<RecyclerView>();

        // RESOURCES_DRIVEPLAN
        ArrayList<TextView> tv_database_driverplan_childitem = new ArrayList<TextView>();
        ArrayList<Button> btn_database_driverplan_childitem = new ArrayList<Button>();
        ArrayList<Spinner> spin_database_driverplan_childitem = new ArrayList<Spinner>();
        ArrayList<RecyclerView> rcy_database_driverplan_childitem = new ArrayList<RecyclerView>();

        RecyclerViewItemAdapter recyclerViewItemAdapter;

        //TextView tv_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 主recyclerview傳入的View裡面的子recyclerview
            switch (viewtype.get(type_transform)) {
                case "RESOURCES_MEMBERS":
                    btn_database_members_childitem.add((Button) itemView.findViewById(R.id.bt_modify_actdatabasemembers));
                    btn_database_members_childitem.add((Button) itemView.findViewById(R.id.bt_clear_actdatabasemembers));
                    btn_database_members_childitem.add((Button) itemView.findViewById(R.id.bt_create_actdatabasemembers));

                    tv_database_members_childitem.add((TextView) itemView.findViewById(R.id.edtv_name_actdatabasemembers));
                    tv_database_members_childitem.add((TextView) itemView.findViewById(R.id.edtv_phone_actdatabasemembers));
                    tv_database_members_childitem.add((TextView) itemView.findViewById(R.id.edtv_rank_actdatabasemembers));
                    tv_database_members_childitem.add((TextView) itemView.findViewById(R.id.edtv_job_actdatabasemembers));
                    tv_database_members_childitem.add((TextView) itemView.findViewById(R.id.edtv_relation_actdatabasemembers));
                    tv_database_members_childitem.add((TextView) itemView.findViewById(R.id.edtv_else_actdatabasemembers));

                    rcy_database_members_childitem.add((RecyclerView) itemView.findViewById(R.id.rcy_main_actdatabasemembers));

                    break;
                case "RESOURCES_ITEMS":
                    /*tv_database_childitem.add((TextView) itemView.findViewById(R.id.edtv_name_actdatabase));
                    tv_database_childitem.add((TextView) itemView.findViewById(R.id.edtv_phone_actdatabase));
                    tv_database_childitem.add((TextView) itemView.findViewById(R.id.edtv_rank_actdatabase));
                    tv_database_childitem.add((TextView) itemView.findViewById(R.id.edtv_job_actdatabase));
                    tv_database_childitem.add((TextView) itemView.findViewById(R.id.edtv_else_actdatabase));

                    btn_database_childitem.add((Button) itemView.findViewById(R.id.bt_modify_actdatabase));
                    btn_database_childitem.add((Button) itemView.findViewById(R.id.bt_clear_actdatabase));
                    btn_database_childitem.add((Button) itemView.findViewById(R.id.bt_create_actdatabase));

                    rcy_database_childitem.add((RecyclerView) itemView.findViewById(R.id.rcy_main_actdatabase));*/
                    break;
                case "RESOURCES_DRIVEPLAN":
                    tv_database_driverplan_childitem.add((TextView) itemView.findViewById(R.id.tv_dateday_database_driverplan));

                    spin_database_driverplan_childitem.add((Spinner) itemView.findViewById(R.id.spin_event_database_driverplan));
                    spin_database_driverplan_childitem.add((Spinner) itemView.findViewById(R.id.spin_area_database_driverplan));
                    spin_database_driverplan_childitem.add((Spinner) itemView.findViewById(R.id.spin_conductor_database_driverplan));
                    spin_database_driverplan_childitem.add((Spinner) itemView.findViewById(R.id.spin_driver_database_driverplan));
                    spin_database_driverplan_childitem.add((Spinner) itemView.findViewById(R.id.spin_car_database_driverplan));

                    btn_database_driverplan_childitem.add((Button) itemView.findViewById(R.id.bt_modify_database_driverplan));
                    btn_database_driverplan_childitem.add((Button) itemView.findViewById(R.id.bt_clear_database_driverplan));
                    btn_database_driverplan_childitem.add((Button) itemView.findViewById(R.id.bt_create_database_driverplan));

                    rcy_database_driverplan_childitem.add((RecyclerView) itemView.findViewById(R.id.rcy_main_databasedriverplan));
                    break;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        final ViewHolder holder;

        switch (viewtype.get(type_transform)){
            case "RESOURCES_MEMBERS":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_t3_databasemembers, parent, false);
                holder = new ViewHolder(view);




                //會閃退!!!!!!!!!!!!!!!!
                /*holder.btn_database_members_childitem.get(2).setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = holder.tv_database_members_childitem.get(0).getText().toString();
                        String phone = holder.tv_database_members_childitem.get(1).getText().toString();
                        String rank = holder.tv_database_members_childitem.get(2).getText().toString();
                        String jobfunctions = holder.tv_database_members_childitem.get(3).getText().toString();
                        String relation = holder.tv_database_members_childitem.get(5).getText().toString();
                        String elseInfo = holder.tv_database_members_childitem.get(5).getText().toString();
                        if (name.length() == 0) return;//如果名字欄沒填入任何東西，則不執行下面的程序
                        Room_Object_Members data = new Room_Object_Members(name, phone, rank, jobfunctions, relation, elseInfo);
                        MemberDatabase.getInstance(context).getDataUao().insertData(data);
                        roomRecyclerViewAdapter.refreshView();
                        holder.tv_database_members_childitem.get(0).setText("");
                        holder.tv_database_members_childitem.get(1).setText("");
                        holder.tv_database_members_childitem.get(2).setText("");
                        holder.tv_database_members_childitem.get(3).setText("");
                        holder.tv_database_members_childitem.get(5).setText("");
                        holder.tv_database_members_childitem.get(5).setText("");
                    }
                }));*/

                type_transform++;
                break;
            case "RESOURCES_DRIVEPLAN":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_t3_databasedispatch, parent, false);
                holder = new ViewHolder(view);

                holder.tv_database_driverplan_childitem.get(0).setInputType(InputType.TYPE_NULL);
                holder.tv_database_driverplan_childitem.get(0).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar cldr = Calendar.getInstance();
                        int day = cldr.get(Calendar.DAY_OF_MONTH);
                        int month = cldr.get(Calendar.MONTH);
                        int year = cldr.get(Calendar.YEAR);
                        int dayodweek = cldr.get(Calendar.DAY_OF_WEEK);
                        //String dayodweek = dayInWeekOutput_intin(cldr.get(Calendar.DAY_OF_WEEK));
                        // date picker dialog
                        DatePickerDialog picker;
                        picker = new DatePickerDialog(context,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        Date target_date = new Date(year, monthOfYear, dayOfMonth-1);
                                        holder.tv_database_driverplan_childitem.get(0).setText((year-1911) + "/" + (monthOfYear + 1) + "/" + dayOfMonth + " " + dateFormatOutput("dayofweek",dateToCalendar(target_date)));
                                    }
                                }, year, month, day);
                        picker.show();
                    }
                });

                // set all spinners data and events
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,R.array.m_event_array,R.layout.spinner_style_a);
                holder.spin_database_driverplan_childitem.get(0).setAdapter(adapter);
                adapter.setDropDownViewResource(R.layout.spinner_style_a);
                holder.spin_database_driverplan_childitem.get(0).setOnItemSelectedListener(spnOnItemSelected);
                //----------------------------------------------------------------------------------------------------------------------------
                adapter = ArrayAdapter.createFromResource(context,R.array.m_area_array,R.layout.spinner_style_a);
                holder.spin_database_driverplan_childitem.get(1).setAdapter(adapter);
                adapter.setDropDownViewResource(R.layout.spinner_style_a);
                holder.spin_database_driverplan_childitem.get(1).setOnItemSelectedListener(spnOnItemSelected);
                //----------------------------------------------------------------------------------------------------------------------------
                adapter = ArrayAdapter.createFromResource(context,R.array.m_conductor_array,R.layout.spinner_style_a);
                holder.spin_database_driverplan_childitem.get(2).setAdapter(adapter);
                adapter.setDropDownViewResource(R.layout.spinner_style_a);
                holder.spin_database_driverplan_childitem.get(2).setOnItemSelectedListener(spnOnItemSelected);
                //----------------------------------------------------------------------------------------------------------------------------
                adapter = ArrayAdapter.createFromResource(context,R.array.m_driver_array,R.layout.spinner_style_a);
                holder.spin_database_driverplan_childitem.get(3).setAdapter(adapter);
                adapter.setDropDownViewResource(R.layout.spinner_style_a);
                holder.spin_database_driverplan_childitem.get(3).setOnItemSelectedListener(spnOnItemSelected);
                //----------------------------------------------------------------------------------------------------------------------------
                adapter = ArrayAdapter.createFromResource(context,R.array.m_car_array,R.layout.spinner_style_a);
                holder.spin_database_driverplan_childitem.get(4).setAdapter(adapter);
                adapter.setDropDownViewResource(R.layout.spinner_style_a);
                holder.spin_database_driverplan_childitem.get(4).setOnItemSelectedListener(spnOnItemSelected);

                //holder.rcy_database_driverplan_childitem.get(0).



                holder.btn_database_driverplan_childitem.get(2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    }
                });

                type_transform++;
                break;
            case "RESOURCES_ITEMS":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_t3_databaseitem, parent, false);
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
    public void onBindViewHolder(@NonNull ViewPagerItemAdapter_t2.ViewHolder holder, int position) {
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
                    case 1:return "(日)";
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

    private String dayInWeekOutput_intin(int a){
        switch(a){
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

}