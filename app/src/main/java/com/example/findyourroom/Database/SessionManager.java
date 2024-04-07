package com.example.findyourroom.Database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    SessionManager(Context _context){
        context = _context;
        userSession = _context.getSharedPreferences("userLoginSession",0);
        editor = userSession.edit();
    }

    public void createLoginSession(String username,String password){
        editor.putBoolean(IS_LOGIN,true);

        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_PASSWORD,password);

        editor.commit();
    }
    public HashMap<String ,String> getUsersDataFromSession(){
        HashMap<String ,String> userData = new HashMap<>();
        userData.put(KEY_USERNAME,userSession.getString(KEY_USERNAME,null));
        userData.put(KEY_PASSWORD,userSession.getString(KEY_PASSWORD,null));

        return userData;
    }

    public boolean checkLogin(){
        if (userSession.getBoolean(IS_LOGIN,true)){
            return true;
        }
        else
            return false;
    }
    public void logout(){
        editor.clear();
        editor.commit();
    }

}
