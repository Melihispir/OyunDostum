package com.something.oyundostum.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.something.oyundostum.R;
import com.something.oyundostum.activities.Chat2;
import com.something.oyundostum.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class MessagesFragment extends Fragment {
    Button btn ;
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview  = inflater.inflate(R.layout.fragment_mesajlar,null);
        usersList = rootview.findViewById(R.id.usersList1);
        noUsersText = rootview.findViewById(R.id.noUsersText1);
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.show();
        String url = "https://oyundostum-4e7b3.firebaseio.com/messages.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);


        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.getInstance().chatWith = al.get(position);
                startActivity(new Intent(getActivity(), Chat2.class));
            }
        });

                return rootview;

    }
    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";
            String key2 = "";

            while(i.hasNext()){
                key = i.next().toString();
                int y =key.indexOf("_");
                key2= key.substring(0,y); //melih  yazan
                key=key.substring(y+1,key.length());   //ahmet  yazılan
                if(!key.equals(UserDetails.getInstance().username)) {
                    if(key2.equals(UserDetails.getInstance().username))
                    al.add(key);
                }
                totalUsers++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, al));
        }
        pd.dismiss();
    }



}
