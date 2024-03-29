package com.something.oyundostum.adapters;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.something.oyundostum.R;
import com.something.oyundostum.models.Message;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
public class CustomAdapter extends BaseAdapter{
    LayoutInflater layoutInflater;
    ArrayList<Message> messageList;
    FirebaseUser fUser;
    public CustomAdapter(Activity activity, ArrayList<Message> messageList, FirebaseUser fUser){
        this.messageList = messageList;
        layoutInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fUser=fUser;}
    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satir = null;
        Message message = messageList.get(position);
        if(message.getGonderen().equals(fUser.getEmail())){
            satir=layoutInflater.inflate(R.layout.custom_sag,null);
            TextView mailim= (TextView) satir.findViewById(R.id.textViewBen);
            mailim.setText(message.getGonderen());
            TextView mesajim= (TextView) satir.findViewById(R.id.textViewMesajim);
            mesajim.setText(message.getMesaj());
            TextView zamanim= (TextView) satir.findViewById(R.id.textViewZamanim);
            zamanim.setText(message.getZaman());
        }
        else{
            satir=layoutInflater.inflate(R.layout.custom_sol,null);
            TextView gonderenMail= (TextView) satir.findViewById(R.id.textViewGonderenKisi);
            gonderenMail.setText(message.getGonderen());
            TextView mesaji= (TextView) satir.findViewById(R.id.textViewMesaji);
            mesaji.setText(message.getMesaj());
            TextView zamani= (TextView) satir.findViewById(R.id.textViewZamani);
            zamani.setText(message.getZaman());

        }
        return satir;
    }
}
