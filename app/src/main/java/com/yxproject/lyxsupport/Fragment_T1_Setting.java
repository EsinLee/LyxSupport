package com.yxproject.lyxsupport;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.facebook.stetho.Stetho;

public class Fragment_T1_Setting extends Fragment {
    private Context main_context;

    private Button btn_setting_bgimg;
    private Button btn_setting_appinfo;

    private Dialog dialog_colorpicker;
    private View dialog_colorpicker_viewdialog;
    private SeekBar seekBar_dialog_colorpicker_red;
    private TextView tv_dialog_colorpicker_red_value;
    private SeekBar seekBar_dialog_colorpicker_green;
    private TextView tv_dialog_colorpicker_green_value;
    private SeekBar seekBar_dialog_colorpicker_blue;
    private TextView tv_dialog_colorpicker_blue_value;
    private Button btn_dialog_colorpicker_submit;

    private MainActivity mActivity;


    public Fragment_T1_Setting(Context context) {
        this.main_context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.adapter_page_t1_setting, container, false);
        // set view elements
        mActivity = (MainActivity) main_context;
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(main_context);
        }
        btn_setting_bgimg = (Button) root.findViewById(R.id.btn_setting_bgimg);
        btn_setting_appinfo = (Button) root.findViewById(R.id.btn_setting_appinfo);

        seekBar_dialog_colorpicker_red = (SeekBar) root.findViewById(R.id.seekBar_dialog_colorpicker_red);
        tv_dialog_colorpicker_red_value = (TextView) root.findViewById(R.id.tv_dialog_colorpicker_red_value);
        seekBar_dialog_colorpicker_green = (SeekBar) root.findViewById(R.id.seekBar_dialog_colorpicker_green);
        tv_dialog_colorpicker_green_value = (TextView) root.findViewById(R.id.tv_dialog_colorpicker_green_value);
        seekBar_dialog_colorpicker_blue = (SeekBar) root.findViewById(R.id.seekBar_dialog_colorpicker_blue);
        tv_dialog_colorpicker_blue_value = (TextView) root.findViewById(R.id.tv_dialog_colorpicker_blue_value);
        btn_dialog_colorpicker_submit = (Button) root.findViewById(R.id.btn_dialog_colorpicker_submit);

        dialog_colorpicker = new Dialog(main_context);
        btn_setting_bgimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openColorPicker();
                new Dialog_colorpicker(main_context).show();
            }
        });

        btn_setting_appinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(main_context);
                alertDialog.setTitle("關於");
                alertDialog.setMessage("當前版本 : v1.0.3");
                alertDialog.setNeutralButton("關閉",((dialog, which) -> {}));
                AlertDialog dialog = alertDialog.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener((v -> {
                    dialog.dismiss();
                }));

                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // view do any works

    }
}
