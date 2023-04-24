package com.yxproject.lyxsupport;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.yxproject.RoomDataBase.DataBase;
import com.yxproject.RoomDataBase.Items_entity;
import com.yxproject.RoomDataBase.Members_entity;

import java.util.List;

public class RoomSpinnerAdapter extends ArrayAdapter {

    private Context main_context;
    private List<Members_entity> members_data;
    private List<Items_entity> items_data;

    private LayoutInflater mInflater;

    private String target_table;
    private int typeNum;
    private RoomSpinnerAdapter.OnItemClickListener onItemClickListener;

    public RoomSpinnerAdapter(Context context, List myData, String target_table, int typeNum) {
        super(context, R.layout.spinner_style_a);
        this.main_context = context;
        this.target_table = target_table;
        this.typeNum = typeNum;

        switch (target_table) {
            case "Members":
                this.members_data = myData;
                break;
            case "Items":
                this.items_data = myData;
                break;
            default:
                this.members_data = myData;
                break;
        }
    }

    @Override
    public int getCount() {
        return members_data.size();
    }

    @Override
    public Object getItem(int position) {
        return members_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return members_data.get(position).getId();
    }

    // This is for the default ("idle") state of the spinner.
    // You can use a custom layout or use the default one.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(main_context);
        convertView=_LayoutInflater.inflate(R.layout.spinner_style_a, null);
        if(convertView!=null)
        {
            TextView _TextView1=(TextView)convertView.findViewById(R.id.tv_spnStyle_a);
            _TextView1.setText(members_data.get(position).getName());
        }
        return convertView;
    }

    // Drop down item view as stated in the method name.
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        /*convertView=mInflater.inflate(R.layout.spinner_style_a, parent, false);
        TextView textView = convertView.findViewById(R.id.tv_spnStyle_a);
        textView.setText(members_data.get(position).getName());
        return convertView;*/

        LayoutInflater _LayoutInflater=LayoutInflater.from(main_context);
        convertView=_LayoutInflater.inflate(R.layout.spinner_style_a, null);
        if(convertView!=null)
        {
            TextView _TextView1=(TextView)convertView.findViewById(R.id.tv_spnStyle_a);
            _TextView1.setText(members_data.get(position).getName());
        }
        return convertView;
    }

    /**更新資料*/
    public void refreshView() {
        switch (target_table) {
            case "Members":
                new Thread(()->{
                    List<Members_entity> data = DataBase.getInstance(main_context).getMembersDataDao().displayAllfromMembers();
                    this.members_data = data;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                    notifyDataSetChanged();
                }).start();
                break;
            case "Items":
                break;
        }
    }

    /**建立對外接口*/
    public interface OnItemClickListener {
        void onItemClick(List mtdata);
    }
}
