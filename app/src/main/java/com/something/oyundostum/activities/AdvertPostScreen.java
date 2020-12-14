package com.something.oyundostum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.something.oyundostum.R;
import com.something.oyundostum.UserDetails;
import com.something.oyundostum.models.Advert2;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AdvertPostScreen extends AppCompatActivity {
    EditText gamenameField;
    EditText descriptionField ;
    Button btn ;
    Firebase reference1;
    DatabaseReference df;
    Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_post_screen);
        gamenameField=findViewById(R.id.gamenameField);
        descriptionField= findViewById(R.id.descriptionField);
        btn = findViewById(R.id.butongonder);
        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://oyundostum-4e7b3.firebaseio.com/advert/");
        map = new HashMap<>();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* map.put("oyunismi", gamenameField.getText().toString());
                map.put("aciklama", descriptionField.getText().toString());
                map.put("kullaniciadi", UserDetails.getInstance().username);*/
                String gamingname= gamenameField.getText().toString();
                String aciklima=descriptionField.getText().toString();
                if(gamingname.equals("")){
                    gamenameField.setError("boş bırakılamaz");
                }
                else if (aciklima.equals("")){
                    descriptionField.setError("boş geçilemez");

                }
                else {
                    Advert2 adv = new Advert2(descriptionField.getText().toString(), UserDetails.getInstance().username, gamenameField.getText().toString());
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("adverts");
                    dbRef.push().setValue(adv);
                    //PostAdvert();
                    Intent i = new Intent(AdvertPostScreen.this, MainActivity.class);

                    startActivity(i);
                }
            }
        });
      }
}

