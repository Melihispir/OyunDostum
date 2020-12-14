package com.something.oyundostum.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.something.oyundostum.CustomItemClickListener;
import com.something.oyundostum.R;
import com.something.oyundostum.models.ilanlar_veriler;

import org.json.JSONException;

import java.util.ArrayList;

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.MyViewHolder> implements Filterable {

    ArrayList<ilanlar_veriler> mProductList;
    ArrayList<ilanlar_veriler> FilteredmProductList;
    LayoutInflater inflater;
    CustomItemClickListener mListener;


    public AdvertAdapter(Context context, ArrayList<ilanlar_veriler> products, CustomItemClickListener Listener) {

        inflater = LayoutInflater.from(context);
        this.mProductList = products;
        this.FilteredmProductList=products;
        mListener=Listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =   LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        MyViewHolder holder = new MyViewHolder(view,mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ilanlar_veriler veri = mProductList.get(position);
        holder.oyun_adi.setText(veri.getOyun_adi());
        holder.aciklama.setText(veri.getAciklama());
        holder.ad_soyad.setText(veri.getIsim_());

    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    mProductList = FilteredmProductList;

                } else {

                    ArrayList<ilanlar_veriler> tempFilteredList = new ArrayList<>();

                    for (ilanlar_veriler user : FilteredmProductList) {

                        // search for user name
                        if (user.getOyun_adi().contains(searchString)) {

                            tempFilteredList.add(user);
                        }
                    }

                    mProductList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mProductList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mProductList = (ArrayList<ilanlar_veriler>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ad_soyad,aciklama,oyun_adi;

       CustomItemClickListener mListener;
       Button iletisimegec;

        public MyViewHolder(View itemView, final CustomItemClickListener Listener) {
            super(itemView);
            mListener=Listener;
            ad_soyad= itemView.findViewById(R.id.adsoyad);
            aciklama = itemView.findViewById(R.id.Aciklama);
            oyun_adi = itemView.findViewById(R.id.Oyun_adi);
            iletisimegec= itemView.findViewById(R.id.iletisimbutton);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Listener != null)
                        Listener.onRowClicked(getAdapterPosition());
                }
            });
           iletisimegec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Listener != null) {
                        try {
                            Listener.onViewClicked(v, getAdapterPosition());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


        }




        @Override
        public void onClick(View v) {


        }


    }
}

