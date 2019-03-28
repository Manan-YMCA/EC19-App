package com.elementsculmyca.ec19_app.UI.SplashScreen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.LoginScreen.LoginActivity;
import com.elementsculmyca.ec19_app.UI.MainScreen.MainScreenActivity;

public class SplashScreenActivity extends Activity {
    public static int SPLASH_TIME_OUT = 3000;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences=getSharedPreferences("login_details",0);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                String phone = sharedPreferences.getString("UserPhone","");
                if(phone.equals("")) {
                    Intent SplashScreen = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(SplashScreen);
                }
                else {
                    startActivity(new Intent(SplashScreenActivity.this,MainScreenActivity.class));
                }
                finish();
            }
        },SPLASH_TIME_OUT);

    }
}
