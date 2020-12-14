package com.something.oyundostum.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.something.oyundostum.R;
import com.something.oyundostum.SessionManager;
import com.something.oyundostum.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login2 extends AppCompatActivity {
    TextView registerUser;
    EditText username, password;
    Button loginButton;
    String user, pass;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        registerUser = findViewById(R.id.register2);
        username = findViewById(R.id.username2);
        password = findViewById(R.id.password2);
        loginButton = findViewById(R.id.loginButton2);

        session = new SessionManager(getApplicationContext());

        //activity mizin başında hemen login durumunu kontrol ediyoruz, eğer session varsa giriş ekranını geçiyoruz.
        if (session.isLoggedIn() == true){
            HashMap<String, String> user=session.getUserDetails();

            UserDetails.getInstance().username=user.get("email");

            startActivity(new Intent(Login2.this, MainActivity.class));
            finish();
        }

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login2.this, Register2.class));
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();

                if(user.equals("")){
                    username.setError("can't be blank");
                }
                else if(pass.equals("")){
                    password.setError("can't be blank");
                }
                else{
                    String url = "https://oyundostum-4e7b3.firebaseio.com/users.json";
                    final ProgressDialog pd = new ProgressDialog(Login2.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            if(s.equals("null")){
                                Toast.makeText(Login2.this, "user not found", Toast.LENGTH_LONG).show();
                            }
                            else{
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if(!obj.has(user)){
                                        Toast.makeText(Login2.this, "user not found", Toast.LENGTH_LONG).show();
                                    }
                                    else if(obj.getJSONObject(user).getString("password").equals(pass)){
                                        UserDetails.getInstance().username = user;
                                        UserDetails.getInstance().password = pass;
                                        session.createLoginSession(user, pass);
                                        startActivity(new Intent(Login2.this, MainActivity.class));
                                    }
                                    else {
                                        Toast.makeText(Login2.this, "incorrect password", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }
                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(Login2.this);
                    rQueue.add(request);
                }
            }
        });
    }
}