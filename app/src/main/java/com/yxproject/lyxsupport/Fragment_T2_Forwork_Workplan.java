package com.yxproject.lyxsupport;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Fragment_T2_Forwork_Workplan extends Fragment {
    private Context main_context;

    private static Fragment_T2_Forwork_DriveplanOutput instance = null;
    RoomSpinnerAdapter roomSpinnerAdapter;

    private Boolean dateChanged = TRUE;
    private Boolean todayIsFriday = FALSE;

    ArrayList<TextView> tv_forwork_workplan_childitem = new ArrayList<TextView>();

    public Fragment_T2_Forwork_Workplan(Context context) {
        main_context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.adapter_page_t2_workplan, container, false);
        // set view elements

        /*tv_forwork_workplan_childitem.add((TextView) root.findViewById(R.id.tv_dateday_actshowdriveplan));

        Calendar cld = Calendar.getInstance();
        cld.add(cld.DATE,+1);
        tv_forwork_workplan_childitem.get(0).setText((cld.get(Calendar.YEAR)-1911) + "/" + (cld.get(Calendar.MONTH) + 1) + "/" + cld.get(Calendar.DAY_OF_MONTH) + " " + dateFormatOutput("dayofweek",cld));
        tv_forwork_workplan_childitem.get(0).setInputType(InputType.TYPE_NULL);
        if(dateFormatOutput("dayofweek",cld) == "(一)"){todayIsFriday = TRUE;
        }else{todayIsFriday = FALSE;}
        tv_forwork_workplan_childitem.get(0).setOnClickListener(new View.OnClickListener() {
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
                                tv_forwork_workplan_childitem.get(0).setText((year-1911) + "/" + (monthOfYear + 1) + "/" + dayOfMonth + " " + dateFormatOutput("dayofweek",dateToCalendar(target_date)));
                                if(dateFormatOutput("dayofweek",dateToCalendar(target_date)) == "(一)"){todayIsFriday = TRUE;
                                }else{todayIsFriday = FALSE;}
                            }
                        }, year, month, day);
                picker.show();
            }
        });*/
        
        
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // view do any works

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

    public static Calendar dateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;

    }
}
