package com.example.adminpanel.adapters;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.adminpanel.setters_getters.admin;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLogedIn";

    public static final String KEY_NAME = "admin_name";
    public static final String KEY_EMAIL = "admin_email";
    public static final String KEY_MOBILE = "admin_mobile";
    public static final String KEY_GENDER = "admin_gender";
    public static final String KEY_NIC = "admin_nic";
    public static final String KE_ADDRESS = "admin_address";
    public static final String KE_Keey = "admin_keey";
    public static final String KE_url = "admin_img";
    public static final String KEY_PASSWORD = "admin_password";

    public SessionManager(Context _context) {
        context = _context;
        userSession = context.getSharedPreferences("UserLoginSession", context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createLoginSession(String admin_name, String admin_email, String admin_mobile, String admin_gender, String admin_nic, String admin_address,String admin_keey, String admin_img, String admin_password) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, admin_name);
        editor.putString(KEY_EMAIL, admin_email);
        editor.putString(KEY_MOBILE, admin_mobile);
        editor.putString(KEY_GENDER, admin_gender);
        editor.putString(KEY_NIC, admin_nic);
        editor.putString(KE_ADDRESS, admin_address);
        editor.putString(KE_Keey, admin_keey);
        editor.putString(KE_url, admin_img);
        editor.putString(KEY_PASSWORD, admin_password);

        editor.commit();
    }

    public HashMap<String, String> getUserDetailsFromSession() {
        HashMap<String, String> UserData = new HashMap<String, String>();
        UserData.put(KEY_NAME, userSession.getString(KEY_NAME, null));
        UserData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        UserData.put(KEY_MOBILE, userSession.getString(KEY_MOBILE, null));
        UserData.put(KEY_GENDER, userSession.getString(KEY_GENDER, null));
        UserData.put(KEY_NIC, userSession.getString(KEY_NIC, null));
        UserData.put(KE_ADDRESS, userSession.getString(KE_ADDRESS, null));
        UserData.put(KE_Keey, userSession.getString(KE_Keey, null));
        UserData.put(KE_url, userSession.getString(KE_url, null));
        UserData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));

        return UserData;
    }

    public admin get_admin_details(){
        admin admn1 = new admin();
        admn1.setAdmin_name(userSession.getString(KEY_NAME, null));
        admn1.setAdmin_email(userSession.getString(KEY_EMAIL, null));
        admn1.setAdmin_mobile(userSession.getString(KEY_MOBILE, null));
        admn1.setAdmin_gender(userSession.getString(KEY_GENDER, null));
        admn1.setAdmin_nic(userSession.getString(KEY_NIC, null));
        admn1.setAdmin_address(userSession.getString(KE_ADDRESS, null));
        admn1.setKeey(userSession.getString(KE_Keey, null));
        admn1.setImageURL(userSession.getString(KE_url, null));
        admn1.setAdmin_password(userSession.getString(KEY_PASSWORD, null));

        return admn1;
    }

    public Boolean checkLogin() {
        if (userSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }



    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }
}
