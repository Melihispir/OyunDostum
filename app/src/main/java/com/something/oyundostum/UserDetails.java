package com.something.oyundostum;


public class UserDetails {
    private static UserDetails mInstance = null;

    public static synchronized UserDetails getInstance() {
        if(null == mInstance){
            mInstance = new UserDetails();
        }
        return mInstance;
    }

    public String username = "";
    public String password = "";
    public String chatWith = "";
}