package com.something.oyundostum.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.something.oyundostum.activities.Login2;
import com.something.oyundostum.R;
import com.something.oyundostum.SessionManager;
import com.something.oyundostum.activities.MyAdvertActivity;


public class ProfileFragment extends Fragment {
        Button btn ,btn2;
        SessionManager sm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sm= new SessionManager(getActivity().getApplicationContext());
        View rootview= inflater.inflate(R.layout.fragment_profilim,null);
        btn = rootview.findViewById(R.id.buttonprofil);
        btn2 = rootview.findViewById(R.id.buttonilanlarım);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.logoutUser();
                Intent i = new Intent(getActivity(), Login2.class);
                startActivity(i);
                getActivity().finish();

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i = new Intent (getActivity(), MyAdvertActivity.class);
                startActivity(i);
            }
        });

        return  rootview ;


    }


}
