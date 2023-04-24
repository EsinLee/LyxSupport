package com.yxproject.lyxsupport;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_T2_Forwork_Calendar extends Fragment {
    MainActivity mMainactivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainactivity = (MainActivity)context;
    }

    public Fragment_T2_Forwork_Calendar() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.adapter_page_t2_calendar, container, false);
        // set view elements
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // view do any works

    }
}
