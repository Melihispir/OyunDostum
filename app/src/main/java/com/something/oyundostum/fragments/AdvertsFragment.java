package com.something.oyundostum.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.something.oyundostum.CustomItemClickListener;
import com.something.oyundostum.R;
import com.something.oyundostum.UserDetails;
import com.something.oyundostum.activities.AdvertPostScreen;
import com.something.oyundostum.activities.Chat2;
import com.something.oyundostum.adapters.AdvertAdapter;
import com.something.oyundostum.models.ilanlar_veriler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.util.ArrayList;

public class AdvertsFragment extends Fragment {

    private AdvertAdapter productAdapter;
    RecyclerView recyclerView;
    ArrayList<ilanlar_veriler> ilanlar_verilerlist;
    ilanlar_veriler ilanlar_veri;
    ImageView img;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_ilanlar, container,false);

        recyclerView = rootview.findViewById(R.id.recyclerview);
        img = rootview.findViewById(R.id.ilanekle);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AdvertPostScreen.class);
                startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Lütfen bekleyiniz..");

        recyclerView.setLayoutManager(linearLayoutManager);

        CustomItemClickListener listener = new CustomItemClickListener() {
            @Override
            public void onRowClicked(int position) {

            }

            @Override
            public void onViewClicked(View v, int position) throws JSONException {
                if (v.getId() == R.id.iletisimbutton) {
                    UserDetails.getInstance().chatWith = ilanlar_verilerlist.get(position).getIsim_();
                    Intent i = new Intent(getActivity(), Chat2.class);
                    startActivity(i);
                }
            }
        };

        ilanlar_verilerlist = new ArrayList<>();
        productAdapter = new AdvertAdapter(getActivity(), ilanlar_verilerlist, listener);

        recyclerView.setAdapter(productAdapter);

        updateUI();

        return rootview;
    }

    public void updateUI(){
        new ServisVerisi().execute();
    }

    public class ServisVerisi extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            Log.d("entered","update");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... voids) {

            myRef = database.getReference();
            myRef.child("adverts").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        ilanlar_veri = new ilanlar_veriler(postSnapshot.child("oyunismi").getValue().toString(),
                                postSnapshot.child("aciklama").getValue().toString(),
                                postSnapshot.child("kullaniciadi").getValue().toString()
                                , "dad");
                        ilanlar_verilerlist.add(ilanlar_veri);
                    }
                    if(getActivity() == null)
                        return;

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            productAdapter.notifyDataSetChanged();
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("db","error");
                }

            });

            return null;
        }

        @SuppressLint("WrongConstant")
        @Override
        protected void onPostExecute(String result) {
            Log.d("finished","update");
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

        menuInflater.inflate(R.menu.menu_item, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.getFilter().filter(newText);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu,menuInflater);
    }
}