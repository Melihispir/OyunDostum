package com.something.oyundostum.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.something.oyundostum.CustomItemClickListener;
import com.something.oyundostum.R;
import com.something.oyundostum.SessionManager;
import com.something.oyundostum.UserDetails;
import com.something.oyundostum.adapters.AdvertAdapter;
import com.something.oyundostum.adapters.myAdvertAdapter;
import com.something.oyundostum.models.ilanlar_veriler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdvertActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ilanlar_veriler> list;
    ilanlar_veriler ilanlar_veri;
    ProgressDialog progressDialog;
    SessionManager sm ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sm = new SessionManager(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myadvert);
        recyclerView= findViewById(R.id.recyclerview3);
        sm.getUserDetails();
        HashMap<String, String> user = sm.getUserDetails();

        UserDetails.getInstance().username=user.get("email");
        new ServisVerisi().execute();
    }

    private class ServisVerisi extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(MyAdvertActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Lütfen bekleyiniz..");
            progressDialog.show();
            final int totalTime = 100;
            final Thread t = new Thread() {
                @Override
                public void run() {
                    int jumpTime = 0;

                    while(jumpTime < totalTime) {
                        try {
                            sleep(200);
                            jumpTime += 1;
                            progressDialog.setProgress(jumpTime);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            };
            t.start();
        }

        @Override
        protected String doInBackground(String... voids) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            list= new ArrayList<>();
            myRef.child("adverts").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if(postSnapshot.child("kullaniciadi").getValue().toString().equals(UserDetails.getInstance().username)){
                            ilanlar_veri = new ilanlar_veriler(postSnapshot.child("oyunismi").getValue().toString(),
                                    postSnapshot.child("aciklama").getValue().toString(),
                                    postSnapshot.child("kullaniciadi").getValue().toString()
                                    ,"dad");
                            list.add(ilanlar_veri);


                        }

                        Log.e("asddsf",postSnapshot.child("kullaniciadi").getValue().toString());

                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}

            });




            return null;
        }

        @SuppressLint("WrongConstant")
        @Override
        protected void onPostExecute(String result) {
            CustomItemClickListener listener = new CustomItemClickListener() {
                @Override
                public void onRowClicked(int position) {

                }

                @Override
                public void onViewClicked(View v, int position) throws JSONException {
                    if(v.getId()==R.id.iletisimbutton){

                        final DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference().child("adverts");
                        myRef2.orderByChild("aciklama").equalTo(list.get(position).getAciklama()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot child: dataSnapshot.getChildren()) {
                                    myRef2.child(child.getKey()).removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        Intent i = new Intent(MyAdvertActivity.this, MainActivity.class);

                        startActivity(i);
                    }

                }


            };


            myAdvertAdapter productAdapter = new myAdvertAdapter(MyAdvertActivity.this, list, listener);
            recyclerView.setAdapter(productAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyAdvertActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}
