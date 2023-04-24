package com.yxproject.lyxsupport;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.stetho.Stetho;
import com.yxproject.RoomDataBase.DataBase;
import com.yxproject.RoomDataBase.Items_entity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_T3_Resources_Items extends Fragment {
    private Context main_context;
    RoomRecyclerViewAdapter item_roomRecyclerViewAdapter;
    Items_entity nowSelectedData;//取得在畫面上顯示中的資料內容

    private ArrayList<TextView> tv_database_items_childitem = new ArrayList<TextView>();
    private ArrayList<Button> btn_database_items_childitem = new ArrayList<Button>();
    private ArrayList<RecyclerView> rcy_database_items_childitem = new ArrayList<RecyclerView>();

    public Fragment_T3_Resources_Items(Context context) {
        main_context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.adapter_page_t3_databaseitem, container, false);
        // set view elements
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(main_context);
        }

        btn_database_items_childitem.add((Button) root.findViewById(R.id.bt_modify_actdatabaseitems));
        btn_database_items_childitem.add((Button) root.findViewById(R.id.bt_clear_actdatabaseitems));
        btn_database_items_childitem.add((Button) root.findViewById(R.id.bt_create_actdatabaseitems));

        tv_database_items_childitem.add((TextView) root.findViewById(R.id.edtv_name_actdatabaseitems));
        tv_database_items_childitem.add((TextView) root.findViewById(R.id.edtv_itemid_actdatabaseitems));
        tv_database_items_childitem.add((TextView) root.findViewById(R.id.edtv_quantity_actdatabaseitems));
        tv_database_items_childitem.add((TextView) root.findViewById(R.id.edtv_indecrease_actdatabaseitems));
        tv_database_items_childitem.add((TextView) root.findViewById(R.id.edtv_storageplace_actdatabaseitems));
        tv_database_items_childitem.add((TextView) root.findViewById(R.id.edtv_itemtype_actdatabaseitems));
        tv_database_items_childitem.add((TextView) root.findViewById(R.id.edtv_source_actdatabaseitems));
        tv_database_items_childitem.add((TextView) root.findViewById(R.id.edtv_elseinfo_actdatabaseitems));

        rcy_database_items_childitem.add((RecyclerView) root.findViewById(R.id.rcy_main_actdatabaseitems));
        rcy_database_items_childitem.get(0).setLayoutManager(new LinearLayoutManager(main_context));
        rcy_database_items_childitem.get(0).addItemDecoration(new DividerItemDecoration(main_context, DividerItemDecoration.VERTICAL));//設置分隔線
        setRecyclerFunction(rcy_database_items_childitem.get(0));//設置RecyclerView左滑刪除

        /**=======================================================================================*/
        /**初始化*/
        new Thread(() -> {
            List<Items_entity> itemsdata = DataBase.getInstance(main_context).getItemsDataDao().displayAllfromItems();
            item_roomRecyclerViewAdapter = new RoomRecyclerViewAdapter(main_context, itemsdata, "Items", 4);
            rcy_database_items_childitem.get(0).setAdapter(item_roomRecyclerViewAdapter);
            getActivity().runOnUiThread(() -> {
                //取得被選中的資料，並顯示於畫面
                //.setOnItemClickListener((myData)-> {//匿名函式(原貌在上方)
                item_roomRecyclerViewAdapter.setOnItemClickListener((myData)-> {//匿名函式(原貌在上方)
                    nowSelectedData = (Items_entity) myData;
                    tv_database_items_childitem.get(0).setText(((Items_entity) myData).getName());
                    tv_database_items_childitem.get(1).setText(((Items_entity) myData).getItemid());
                    tv_database_items_childitem.get(2).setText("" + ((Items_entity) myData).getQuantity());
                    tv_database_items_childitem.get(3).setText("" + ((Items_entity) myData).getIndecrease());
                    tv_database_items_childitem.get(4).setText(((Items_entity) myData).getStorageplace());
                    tv_database_items_childitem.get(5).setText(((Items_entity) myData).getItemtype());
                    tv_database_items_childitem.get(6).setText(((Items_entity) myData).getSource());
                    tv_database_items_childitem.get(7).setText(((Items_entity) myData).getElseInfo());
                });
            });
        }).start();
        /**=======================================================================================*/
        /**設置修改資料的事件*/
        btn_database_items_childitem.get(0).setOnClickListener((v) -> {
            new Thread(() -> {
                if(nowSelectedData ==null) return;//如果目前沒前台沒有資料，則以下程序不執行
                String name = tv_database_items_childitem.get(0).getText().toString();
                String itemid = tv_database_items_childitem.get(1).getText().toString();
                int quantity = Integer.parseInt(tv_database_items_childitem.get(2).getText().toString());
                int indecrease = Integer.parseInt(tv_database_items_childitem.get(3).getText().toString());
                String storageplace = tv_database_items_childitem.get(4).getText().toString();
                String itemtype = tv_database_items_childitem.get(5).getText().toString();
                String source = tv_database_items_childitem.get(6).getText().toString();
                String elseinfo = tv_database_items_childitem.get(7).getText().toString();
                Items_entity data = new Items_entity(nowSelectedData.getId(), name, itemid, quantity, indecrease, storageplace, itemtype, source, elseinfo);
                DataBase.getInstance(main_context).getItemsDataDao().updateData(data);
                getActivity().runOnUiThread(() -> {
                    tv_database_items_childitem.get(0).setText("");
                    tv_database_items_childitem.get(1).setText("");
                    tv_database_items_childitem.get(2).setText("");
                    tv_database_items_childitem.get(3).setText("");
                    tv_database_items_childitem.get(4).setText("");
                    tv_database_items_childitem.get(5).setText("");
                    tv_database_items_childitem.get(6).setText("");
                    tv_database_items_childitem.get(7).setText("");
                    nowSelectedData = null;
                    item_roomRecyclerViewAdapter.refreshView("Items");
                    Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Cars");
                    Fragment_T3_Resources_Dispatch.getInstance().spinnersLiveupdate("Cars");
                    //Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Members");
                    Toast.makeText(main_context, "已更新資訊！", Toast.LENGTH_LONG).show();
                });
            }).start();
        });
        /**=======================================================================================*/
        /**清空資料*/
        btn_database_items_childitem.get(1).setOnClickListener((v -> {
            tv_database_items_childitem.get(0).setText("");
            tv_database_items_childitem.get(1).setText("");
            tv_database_items_childitem.get(2).setText("");
            tv_database_items_childitem.get(3).setText("");
            tv_database_items_childitem.get(4).setText("");
            tv_database_items_childitem.get(5).setText("");
            tv_database_items_childitem.get(6).setText("");
            tv_database_items_childitem.get(7).setText("");
            nowSelectedData = null;
        }));
        /**=======================================================================================*/
        /**新增資料*/
        btn_database_items_childitem.get(2).setOnClickListener((v -> {
            new Thread(() -> {
                String name = tv_database_items_childitem.get(0).getText().toString();
                String itemid = tv_database_items_childitem.get(1).getText().toString();
                int quantity = Integer.parseInt(tv_database_items_childitem.get(2).getText().toString());
                int indecrease = Integer.parseInt(tv_database_items_childitem.get(3).getText().toString());
                String storageplace = tv_database_items_childitem.get(4).getText().toString();
                String itemtype = tv_database_items_childitem.get(5).getText().toString();
                String source = tv_database_items_childitem.get(6).getText().toString();
                String elseinfo = tv_database_items_childitem.get(7).getText().toString();
                if (name.length() == 0) return;//如果名字欄沒填入任何東西，則不執行下面的程序
                Items_entity data = new Items_entity(name, itemid, quantity, indecrease, storageplace, itemtype, source, elseinfo);
                DataBase.getInstance(main_context).getItemsDataDao().insertItemsData(data);
                getActivity().runOnUiThread(() -> {
                    tv_database_items_childitem.get(0).setText("");
                    tv_database_items_childitem.get(1).setText("");
                    tv_database_items_childitem.get(2).setText("");
                    tv_database_items_childitem.get(3).setText("");
                    tv_database_items_childitem.get(4).setText("");
                    tv_database_items_childitem.get(5).setText("");
                    tv_database_items_childitem.get(6).setText("");
                    tv_database_items_childitem.get(7).setText("");
                    nowSelectedData = null;
                    item_roomRecyclerViewAdapter.refreshView("Items");
                    Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Cars");
                    Fragment_T3_Resources_Dispatch.getInstance().spinnersLiveupdate("Cars");
                    //Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Members");
                    Toast.makeText(main_context, "已新增資訊！", Toast.LENGTH_LONG).show();
                });
            }).start();
        }));
        /**=======================================================================================*/

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
                        item_roomRecyclerViewAdapter.deleteDBData("Items",position);
                        new Thread(() -> {
                            getActivity().runOnUiThread(() -> {
                                Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Cars");
                                Fragment_T3_Resources_Dispatch.getInstance().spinnersLiveupdate("Cars");
                                //Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Members");
                            });
                        }).start();

                        break;
                }
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }
}
