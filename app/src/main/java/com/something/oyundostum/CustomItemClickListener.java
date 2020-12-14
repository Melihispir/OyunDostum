package com.something.oyundostum;

import android.view.View;

import org.json.JSONException;

public interface CustomItemClickListener {





    void onRowClicked(int position);
    void onViewClicked(View v, int position) throws JSONException;
}


