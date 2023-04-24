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
import com.yxproject.RoomDataBase.Members_entity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_T3_Resources_Members extends Fragment {
    private Context main_context;
    RoomRecyclerViewAdapter roomRecyclerViewAdapter;
    Members_entity nowSelectedData;//取得在畫面上顯示中的資料內容

    RecyclerViewItemAdapter recyclerViewItemAdapter;

    private ArrayList<TextView> tv_database_members_childitem = new ArrayList<TextView>();
    private ArrayList<Button> btn_database_members_childitem = new ArrayList<Button>();
    private ArrayList<RecyclerView> rcy_database_members_childitem = new ArrayList<RecyclerView>();
    private DataBase dataBase;

    public Fragment_T3_Resources_Members(Context context) {
        main_context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.adapter_page_t3_databasemembers, container, false);

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(main_context);
        }

        btn_database_members_childitem.add((Button) root.findViewById(R.id.bt_modify_actdatabasemembers));
        btn_database_members_childitem.add((Button) root.findViewById(R.id.bt_clear_actdatabasemembers));
        btn_database_members_childitem.add((Button) root.findViewById(R.id.bt_create_actdatabasemembers));

        tv_database_members_childitem.add((TextView) root.findViewById(R.id.edtv_name_actdatabasemembers));
        tv_database_members_childitem.add((TextView) root.findViewById(R.id.edtv_phone_actdatabasemembers));
        tv_database_members_childitem.add((TextView) root.findViewById(R.id.edtv_rank_actdatabasemembers));
        tv_database_members_childitem.add((TextView) root.findViewById(R.id.edtv_job_actdatabasemembers));
        tv_database_members_childitem.add((TextView) root.findViewById(R.id.edtv_relation_actdatabasemembers));
        tv_database_members_childitem.add((TextView) root.findViewById(R.id.edtv_tags_actdatabasemembers));
        tv_database_members_childitem.add((TextView) root.findViewById(R.id.edtv_else_actdatabasemembers));

        rcy_database_members_childitem.add((RecyclerView) root.findViewById(R.id.rcy_main_actdatabasemembers));
        rcy_database_members_childitem.get(0).setLayoutManager(new LinearLayoutManager(main_context));
        rcy_database_members_childitem.get(0).addItemDecoration(new DividerItemDecoration(main_context, DividerItemDecoration.VERTICAL));//設置分隔線
        setRecyclerFunction(rcy_database_members_childitem.get(0));//設置RecyclerView左滑刪除

        /**=======================================================================================*/
        /**初始化*/
        new Thread(() -> {
            List<Members_entity> data = DataBase.getInstance(main_context).getMembersDataDao().displayAllfromMembers();
            roomRecyclerViewAdapter = new RoomRecyclerViewAdapter(main_context, data, "Members", 4);
            rcy_database_members_childitem.get(0).setAdapter(roomRecyclerViewAdapter);
            getActivity().runOnUiThread(() -> {
                //取得被選中的資料，並顯示於畫面
                //.setOnItemClickListener((myData)-> {//匿名函式(原貌在上方)
                roomRecyclerViewAdapter.setOnItemClickListener((myData)-> {//匿名函式(原貌在上方)
                    nowSelectedData = (Members_entity) myData;
                    tv_database_members_childitem.get(0).setText(((Members_entity) myData).getName());
                    tv_database_members_childitem.get(1).setText(((Members_entity) myData).getPhone());
                    tv_database_members_childitem.get(2).setText(((Members_entity) myData).getRank());
                    tv_database_members_childitem.get(3).setText(((Members_entity) myData).getJobfunctions());
                    tv_database_members_childitem.get(4).setText(((Members_entity) myData).getRelation());
                    tv_database_members_childitem.get(5).setText(((Members_entity) myData).getPersonaltags());
                    tv_database_members_childitem.get(6).setText(((Members_entity) myData).getElseInfo());
                });
            });
        }).start();
        /**=======================================================================================*/
        /**設置修改資料的事件*/
        btn_database_members_childitem.get(0).setOnClickListener((v) -> {
            new Thread(() -> {
                if(nowSelectedData ==null) return;//如果目前沒前台沒有資料，則以下程序不執行
                String name = tv_database_members_childitem.get(0).getText().toString();
                String phone = tv_database_members_childitem.get(1).getText().toString();
                String rank = tv_database_members_childitem.get(2).getText().toString();
                String job = tv_database_members_childitem.get(3).getText().toString();
                String relation = tv_database_members_childitem.get(4).getText().toString();
                String tags = tv_database_members_childitem.get(5).getText().toString();
                String elseinfo = tv_database_members_childitem.get(6).getText().toString();
                Members_entity data = new Members_entity(nowSelectedData.getId(), name, phone, rank, job, relation, tags, elseinfo);
                DataBase.getInstance(main_context).getMembersDataDao().updateData(data);
                getActivity().runOnUiThread(() -> {
                    tv_database_members_childitem.get(0).setText("");
                    tv_database_members_childitem.get(1).setText("");
                    tv_database_members_childitem.get(2).setText("");
                    tv_database_members_childitem.get(3).setText("");
                    tv_database_members_childitem.get(4).setText("");
                    tv_database_members_childitem.get(5).setText("");
                    tv_database_members_childitem.get(6).setText("");
                    nowSelectedData = null;
                    roomRecyclerViewAdapter.refreshView("Members");
                    Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Members");
                    Fragment_T3_Resources_Dispatch.getInstance().spinnersLiveupdate("Members");
                    Toast.makeText(main_context, "已更新資訊！", Toast.LENGTH_LONG).show();
                });
            }).start();
        });
        /**=======================================================================================*/
        /**清空資料*/
        btn_database_members_childitem.get(1).setOnClickListener((v -> {
            tv_database_members_childitem.get(0).setText("");
            tv_database_members_childitem.get(1).setText("");
            tv_database_members_childitem.get(2).setText("");
            tv_database_members_childitem.get(3).setText("");
            tv_database_members_childitem.get(4).setText("");
            tv_database_members_childitem.get(5).setText("");
            tv_database_members_childitem.get(6).setText("");
            nowSelectedData = null;
        }));
        /**=======================================================================================*/
        /**新增資料*/
        btn_database_members_childitem.get(2).setOnClickListener((v -> {
            new Thread(() -> {
                String name = tv_database_members_childitem.get(0).getText().toString();
                String phone = tv_database_members_childitem.get(1).getText().toString();
                String rank = tv_database_members_childitem.get(2).getText().toString();
                String job = tv_database_members_childitem.get(3).getText().toString();
                String relation = tv_database_members_childitem.get(4).getText().toString();
                String tags = tv_database_members_childitem.get(5).getText().toString();
                String elseinfo = tv_database_members_childitem.get(6).getText().toString();
                if (name.length() == 0) return;//如果名字欄沒填入任何東西，則不執行下面的程序
                Members_entity data = new Members_entity(name, phone, rank, job, relation, tags, elseinfo);
                DataBase.getInstance(main_context).getMembersDataDao().insertMembersData(data);
                getActivity().runOnUiThread(() -> {
                    tv_database_members_childitem.get(0).setText("");
                    tv_database_members_childitem.get(1).setText("");
                    tv_database_members_childitem.get(2).setText("");
                    tv_database_members_childitem.get(3).setText("");
                    tv_database_members_childitem.get(4).setText("");
                    tv_database_members_childitem.get(5).setText("");
                    tv_database_members_childitem.get(6).setText("");
                    nowSelectedData = null;
                    //myAdapter.refreshView();
                    roomRecyclerViewAdapter.refreshView("Members");
                    Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Members");
                    Fragment_T3_Resources_Dispatch.getInstance().spinnersLiveupdate("Members");
                    Toast.makeText(main_context, "已新增資訊！", Toast.LENGTH_LONG).show();
                });
            }).start();

            //((Fragment_T2_Forwork_Driveplan)getActivity()).spinnersLiveupdate("aa");
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
                        roomRecyclerViewAdapter.deleteDBData("Members",position);
                        new Thread(() -> {
                            getActivity().runOnUiThread(() -> {
                                Fragment_T2_Forwork_DriveplanOutput.getInstance().spinnersLiveupdate("Members");
                                Fragment_T3_Resources_Dispatch.getInstance().spinnersLiveupdate("Members");
                            });
                        }).start();
                        //Fragment_T2_Forwork_Driveplan.getInstance().spinnersLiveupdate("Members");
                        break;
                }
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }
}
