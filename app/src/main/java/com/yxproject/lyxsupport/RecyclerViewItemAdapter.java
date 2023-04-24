package com.yxproject.lyxsupport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewItemAdapter extends RecyclerView.Adapter<RecyclerViewItemAdapter.MyViewItemHolder> {

    private Context context;
    private AdapterView.OnItemClickListener mItemClickListener;

    private final List<String> nameDatas;
    private final List<String> tipDatas;
    private final List<Integer> imgDatas;

    // 物件類型
    private int type_transform= 1;

    public RecyclerViewItemAdapter(Context context, String[] nametext, int[] imageId, String[] tips, int typeNum) {
        //將會傳入 MainActivity 實體
        this.context = context;

        // 傳入物件類型
        this.type_transform = typeNum;

        nameDatas = new ArrayList<String>();
        tipDatas = new ArrayList<String>();
        imgDatas = new ArrayList<Integer>();
        for (int i = 0; i < nametext.length; i++) {
            nameDatas.add("" + nametext[i]);
            tipDatas.add("" + tips[i]);
            imgDatas.add(imageId[i]);
        }
    }

    public void setItemClickListener(AdapterView.OnItemClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewItemHolder extends RecyclerView.ViewHolder {
        // 1 2 3
        TextView tv_title;
        TextView tv_describe;
        ImageView imgv_mainimg;
        Button bt_showmore;
        TextView tv_innertext;
        LinearLayout linlayout_textboxcontainer;
        boolean bool_tv_innertext_stat_closed;
        RecyclerView recyclerview_target;

        // 4
        TextView tv_database_id;
        TextView tv_database_name;
        TextView tv_database_phone;
        TextView tv_database_rank;
        TextView tv_database_job;

        ImageView iv_page_mainbg;

        public MyViewItemHolder(View view) {
            super(view);

            switch (type_transform){
                case 1:
                    tv_title = (TextView) view.findViewById(R.id.tv_title);
                    tv_describe = (TextView) view.findViewById(R.id.tv_describe);
                    imgv_mainimg = (ImageView) view.findViewById(R.id.iv_mainimg);
                    bt_showmore = (Button) view.findViewById(R.id.bt_showmore);
                    tv_innertext = (TextView) view.findViewById(R.id.tv_innertext);
                    linlayout_textboxcontainer = (LinearLayout) view.findViewById(R.id.linlayout_textboxcontainer);
                    bool_tv_innertext_stat_closed = true;
                    recyclerview_target = (RecyclerView) view.findViewById(R.id.rcv_main_acthome);

                    break;
                case  2:
                    tv_title = (TextView) view.findViewById(R.id.tv_title);
                    imgv_mainimg = (ImageView) view.findViewById(R.id.iv_mainimg);

                    break;
                case 3:
                    tv_title = (TextView) view.findViewById(R.id.tv_title);
                    imgv_mainimg = (ImageView) view.findViewById(R.id.iv_mainimg);

                    break;
                case 4:
                    tv_database_id = (TextView) view.findViewById(R.id.tv_database_adapteritem_d_id);;
                    tv_database_name = (TextView) view.findViewById(R.id.tv_database_adapteritem_name);;
                    tv_database_phone = (TextView) view.findViewById(R.id.tv_database_adapteritem_property_b);;
                    tv_database_rank = (TextView) view.findViewById(R.id.tv_database_adapteritem_property_a);;
                    tv_database_job = (TextView) view.findViewById(R.id.tv_database_adapteritem_property_c);;

                    break;
                default:
                    break;

            }


        }

    /*public void DoShowMore(TextView tv) {
        tv.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }*/
    }

    @Override
    public MyViewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (type_transform){
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.adapter_item_type_a, parent, false);
                break;
            case  2:
                view = LayoutInflater.from(context).inflate(R.layout.adapter_item_type_b, parent, false);
                break;
            case 3:
                view = LayoutInflater.from(context).inflate(R.layout.adapter_item_type_c, parent, false);
                break;
            case 4:
                view = LayoutInflater.from(context).inflate(R.layout.adapter_item_type_d, parent, false);
                break;
            default:
                view = LayoutInflater.from(context).inflate(R.layout.adapter_item_type_a, parent, false);
                break;

        }
        final MyViewItemHolder holder = new MyViewItemHolder(view);

    /*holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Recycle Click : " + nameDatas.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    });*/
        // 設定各類型物件使用的事件及作動(如按鈕事件
        switch (type_transform){
            // 物件類型A 按鈕操作展開或收起文字介面
            case 1:
                holder.tv_innertext.setText("This is context text. This is context text. This is context text. This is context text. This is context text. This is context text. This is context text.");
                // 取得物件佈局-文字框-tv_innertext
                LinearLayout.LayoutParams tvparams = (LinearLayout.LayoutParams) holder.linlayout_textboxcontainer.getLayoutParams();
                // 設定物件高度依據內文改變
                tvparams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                // 設定物件高度為1dp
                tvparams.height = 1;
                // 將設定好的參數傳給目標物件
                holder.linlayout_textboxcontainer.setLayoutParams(tvparams);
                // 設定按鈕按下事件-按鈕-bt_showmore
                holder.bt_showmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 取得物件佈局-文字框-tv_innertext
                        LinearLayout.LayoutParams tvparams = (LinearLayout.LayoutParams) holder.linlayout_textboxcontainer.getLayoutParams();
                        // 分辨文字框-tv_innertext-開啟狀態
                        if(holder.bool_tv_innertext_stat_closed){ // 收起狀態轉為展開
                            tvparams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            holder.tv_innertext.setLayoutParams(tvparams);
                            Toast.makeText(view.getContext(), "N, Recycle Click : " + nameDatas.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                        }else{ // 展開狀態轉為收起
                            tvparams.height = 1;
                            holder.tv_innertext.setLayoutParams(tvparams);
                            Toast.makeText(view.getContext(), "Y, Recycle Click : " + nameDatas.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                        }
                        holder.bool_tv_innertext_stat_closed = !holder.bool_tv_innertext_stat_closed;
                        //Toast.makeText(view.getContext(), "Recycle Click : " + nameDatas.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;
        }


        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewItemHolder holder, int position) {

        switch (type_transform){
            case 1:
                holder.tv_title.setText(nameDatas.get(position));
                holder.tv_describe.setText(tipDatas.get(position));
                holder.imgv_mainimg.setImageResource(imgDatas.get(position));

                break;
            case  2:
                holder.tv_title.setText(nameDatas.get(position));
                holder.imgv_mainimg.setImageResource(imgDatas.get(position));
                break;
            case 3:
                holder.tv_title.setText(nameDatas.get(position));
                holder.imgv_mainimg.setImageResource(imgDatas.get(position));
                break;
            case 4:
                holder.tv_database_id.setText("" + imgDatas.get(position));
                holder.tv_database_name.setText(nameDatas.get(position));
                holder.tv_database_phone.setText(tipDatas.get(position));
                //holder.tv_database_rank.setText(nameDatas.get(position));
                //holder.tv_database_job.setText(nameDatas.get(position));
                break;
            default:
                break;

        }


    }

    @Override
    public int getItemCount() {
        return nameDatas.size();
    }
}
