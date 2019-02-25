package com.cloud.marom.safetytours;

import android.app.Application;
import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cloud.marom.safetytours.objects.User;

public class Safetyapp extends Application {

    public String mUserName;
    public String mPassword;
    public User mUser;
    public static boolean IsGetSMS=false;

    private static Context context;
    private static Safetyapp self;

    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
        self.context = getApplicationContext();
    }

    public static Safetyapp getInstance() {
        return self;
    }
    public static Context getAppContext() {
        return self.context;
    }

    public RequestQueue mRequestQueue;
    public RequestQueue getQueue() {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(this);

        return mRequestQueue;
    }


}
