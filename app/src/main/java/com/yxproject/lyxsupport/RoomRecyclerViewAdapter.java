package com.yxproject.lyxsupport;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yxproject.RoomDataBase.DataBase;
import com.yxproject.RoomDataBase.Dispatch_entity;
import com.yxproject.RoomDataBase.Items_entity;
import com.yxproject.RoomDataBase.Members_entity;
import com.yxproject.RoomDataBase.SelfDictionary_entity;

import java.util.List;

public class RoomRecyclerViewAdapter extends RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder> {

    //private List<Members_entity> members_data;
    //private List<Items_entity> items_data;
    private List myMembersData;
    private List myItemsData;
    private List myDispatchData;
    private List myDictionaryData;
    private Context main_context;
    private String target_table;
    private int typeNum;
    private RoomRecyclerViewAdapter.OnItemClickListener onItemClickListener;

    public RoomRecyclerViewAdapter(Context context, List myData, String target_table, int typeNum) {
        this.main_context = context;
        this.target_table = target_table;
        this.typeNum = typeNum;

        switch (target_table) {
            case "Members":
                this.myMembersData = myData;
                break;
            case "Items":
                this.myItemsData = myData;
                break;
            case "Dispatch":
                this.myDispatchData = myData;
                break;
            case "Dictionary":
                this.myDictionaryData = myData;
                break;
        }
        //this.myData = myData;
    }
    /**建立對外接口*/
    public void setOnItemClickListener(RoomRecyclerViewAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_database_id;
        TextView tv_database_adapteritem_name;
        TextView tv_database_adapteritem_a;
        TextView tv_database_adapteritem_b;
        TextView tv_database_adapteritem_c;
        TextView tv_database_adapteritem_d;
        TextView tv_database_adapteritem_e;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_database_id = (TextView) itemView.findViewById(R.id.tv_database_adapteritem_d_id);
            tv_database_adapteritem_name = (TextView) itemView.findViewById(R.id.tv_database_adapteritem_name);
            tv_database_adapteritem_a = (TextView) itemView.findViewById(R.id.tv_database_adapteritem_property_a);
            tv_database_adapteritem_b = (TextView) itemView.findViewById(R.id.tv_database_adapteritem_property_b);
            tv_database_adapteritem_c = (TextView) itemView.findViewById(R.id.tv_database_adapteritem_property_c);
            tv_database_adapteritem_d = (TextView) itemView.findViewById(R.id.tv_database_adapteritem_property_d);
            tv_database_adapteritem_e = (TextView) itemView.findViewById(R.id.tv_database_adapteritem_property_e);
            view = itemView;
        }
    }
    /**更新資料*/
    public void refreshView(String target_table) {
        switch (target_table) {
            case "Members":
                new Thread(() -> {
                    List<Members_entity> membersdata = DataBase.getInstance(main_context).getMembersDataDao().displayAllfromMembers();
                    this.myMembersData = membersdata;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                }).start();
            break;
            case "Items":
                new Thread(() -> {
                    List<Items_entity> itemsdata = DataBase.getInstance(main_context).getItemsDataDao().displayAllfromItems();
                    this.myItemsData = itemsdata;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                }).start();
            break;
            case "Dispatch":
                new Thread(() -> {
                    List<Dispatch_entity> diapatchdata = DataBase.getInstance(main_context).getDispatchDataDao().displayAllfromDispatch();
                    this.myDispatchData = diapatchdata;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                }).start();
            break;
            case "Dictionary":
                new Thread(() -> {
                    List<SelfDictionary_entity> dictionarydata = DataBase.getInstance(main_context).getSelfDictionaryDataDao().displayAllfromDictionary_exsetting();
                    this.myDictionaryData = dictionarydata;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                }).start();
                break;
        }
    }

    public void refreshSearchDatatoView(String target_table, String keyword_a, String keyword_b) {
        switch (target_table) {
            case "Dispatch_time":
                new Thread(() -> {
                    List<Dispatch_entity> diapatchdata = DataBase.getInstance(main_context).getDispatchDataDao().findDispatchDataByTime(keyword_a);
                    this.myDispatchData = diapatchdata;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                }).start();
                break;
        }
    }

    /**刪除資料*/
    public void deleteDBData(String target, int position){
        new Thread(()->{
            switch (target) {
                case "Members":
                    DataBase.getInstance(main_context).getMembersDataDao().deleteMembersData(((Members_entity)myMembersData.get(position)).getId());
                    break;
                case "Items":
                    DataBase.getInstance(main_context).getItemsDataDao().deleteItemsData(((Items_entity)myItemsData.get(position)).getId());
                    break;
                case "Dispatch":
                    DataBase.getInstance(main_context).getDispatchDataDao().deleteDispatchData(((Dispatch_entity)myDispatchData.get(position)).getId());
                    break;
                case "Dictionary":
                    DataBase.getInstance(main_context).getSelfDictionaryDataDao().deleteDictionaryData(((SelfDictionary_entity)myDictionaryData.get(position)).getId());
                    break;
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                    refreshView(target);
                }
            });
        }).start();
    }

    @NonNull
    @Override
    public RoomRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(main_context).inflate(R.layout.adapter_item_type_d, parent, false);;
        switch (target_table) {
            case "Members":
                view = LayoutInflater.from(main_context).inflate(R.layout.adapter_item_type_d, parent, false);
                break;
            case "Items":
                view = LayoutInflater.from(main_context).inflate(R.layout.adapter_item_type_d, parent, false);
                break;
            case "Dispatch":
                view = LayoutInflater.from(main_context).inflate(R.layout.adapter_item_type_e, parent, false);
                break;
            case "Dictionary":
                view = LayoutInflater.from(main_context).inflate(R.layout.adapter_item_type_d, parent, false);
                break;
            default:
                view = LayoutInflater.from(main_context).inflate(R.layout.adapter_item_type_d, parent, false);
                break;
        }
        return new RoomRecyclerViewAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RoomRecyclerViewAdapter.ViewHolder holder, int position) {
        switch (target_table){
            case "Members":
                //holder.tv_database_id.setText("" + ((Members_entity)myData.get(position)).getId());
                holder.tv_database_adapteritem_name.setText(((Members_entity)myMembersData.get(position)).getName());
                holder.tv_database_adapteritem_a.setText(((Members_entity)myMembersData.get(position)).getRank());
                holder.tv_database_adapteritem_b.setText(((Members_entity)myMembersData.get(position)).getPhone());
                holder.tv_database_adapteritem_c.setText(((Members_entity)myMembersData.get(position)).getJobfunctions());
                holder.view.setOnClickListener((v)->{
                    onItemClickListener.onItemClick(((Members_entity)myMembersData.get(position)));
                });
                break;
            case "Items":
                //holder.tv_database_id.setText("" + ((Items_entity)myData.get(position)).getId());
                holder.tv_database_adapteritem_name.setText(((Items_entity)myItemsData.get(position)).getName());
                holder.tv_database_adapteritem_a.setText("數量:" + ((Items_entity)myItemsData.get(position)).getQuantity());
                holder.tv_database_adapteritem_b.setText("位置:" + ((Items_entity)myItemsData.get(position)).getStorageplace());
                holder.tv_database_adapteritem_c.setText(((Items_entity)myItemsData.get(position)).getItemid());
                holder.view.setOnClickListener((v)->{
                    onItemClickListener.onItemClick(((Items_entity)myItemsData.get(position)));
                });
                break;
            case "Dispatch":
                //holder.tv_database_id.setText("" + ((Items_entity)myData.get(position)).getId());
                holder.tv_database_adapteritem_name.setText(((Dispatch_entity)myDispatchData.get(position)).getTime());
                holder.tv_database_adapteritem_a.setText("車輛: " + ((Dispatch_entity)myDispatchData.get(position)).getCar());
                holder.tv_database_adapteritem_b.setText("車長: " + ((Dispatch_entity)myDispatchData.get(position)).getConductor());
                holder.tv_database_adapteritem_c.setText("駕駛: " + ((Dispatch_entity)myDispatchData.get(position)).getDriver());
                holder.tv_database_adapteritem_d.setText("任務: " + ((Dispatch_entity)myDispatchData.get(position)).getEvent());
                holder.tv_database_adapteritem_e.setText("地點: " + ((Dispatch_entity)myDispatchData.get(position)).getArea());
                holder.view.setOnClickListener((v)->{
                    onItemClickListener.onItemClick(((Dispatch_entity)myDispatchData.get(position)));
                });
                break;
            case "Dictionary":
                holder.tv_database_adapteritem_name.setText(((SelfDictionary_entity)myDictionaryData.get(position)).getWord());
                holder.tv_database_adapteritem_a.setText(((SelfDictionary_entity)myDictionaryData.get(position)).getAnnotation());
                holder.tv_database_adapteritem_b.setText(((SelfDictionary_entity)myDictionaryData.get(position)).getTags());
                holder.tv_database_adapteritem_c.setText(((SelfDictionary_entity)myDictionaryData.get(position)).getElseInfo());
                holder.view.setOnClickListener((v)->{
                    onItemClickListener.onItemClick(((SelfDictionary_entity)myDictionaryData.get(position)));
                });
                break;
        }
    }
    @Override
    public int getItemCount() {
        //return myData.size();
        switch (target_table){
            case "Members":
                return myMembersData.size();
            case "Items":
                return myItemsData.size();
            case "Dispatch":
                return myDispatchData.size();
            case "Dictionary":
                return myDictionaryData.size();
            default:
                return myItemsData.size();
        }
    }
    /**建立對外接口*/
    public interface OnItemClickListener {
        //void onMembersItemClick(Members_entity membersentity);
        void onItemClick(Object sentity);
    }
}