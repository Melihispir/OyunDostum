package com.something.oyundostum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.something.oyundostum.R;
import com.something.oyundostum.SessionManager;
import com.something.oyundostum.fragments.AdvertsFragment;
import com.something.oyundostum.fragments.MessagesFragment;
import com.something.oyundostum.fragments.ProfileFragment;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Button btn ;
    private SessionManager session;
    private String email, sifre;
    private AdvertsFragment advertsFragment;
    private MessagesFragment messagesFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        advertsFragment = new AdvertsFragment();
        messagesFragment = new MessagesFragment();
        profileFragment = new ProfileFragment();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        session = new SessionManager(getApplicationContext());

        // sessiondan kullanıcı verilerini almak için nesnemizi oluşturduk.
        HashMap<String, String> user = session.getUserDetails();

        //keylerine göre user nesnemizden verilerimizi çağırdık ve ekledik.
        email = user.get(SessionManager.KEY_EMAIL);
        sifre = user.get(SessionManager.KEY_SIFRE);


        //session'ın varlığını sorguluyoruz. eğer boş ise giriş sayfasına yönlendiriyoruz.
        if (email.isEmpty() || sifre.isEmpty()){

            Toast.makeText(MainActivity.this, "Lütfen Giriş Yapınız.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this, Register2.class));
        }

        else{
            //session boş değil ise TextViewlerimize session bilgilerini yazdırıyoruz.
        }

         loadFragment(new MessagesFragment());
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.navigation_ilanlar:

                return loadFragment(new AdvertsFragment());

            case R.id.navigation_mesajlar:
                return loadFragment(new MessagesFragment());

            case R.id.navigation_profilim:
                return loadFragment( new ProfileFragment());
        }

        return true;
    }
}