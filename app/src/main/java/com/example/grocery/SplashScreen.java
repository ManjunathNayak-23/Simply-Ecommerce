package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    TextView title,subtitle,Welcome;
SharedPreferences onBoardingScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        title=findViewById(R.id.title);
        subtitle=findViewById(R.id.subtitle);
        Welcome=findViewById(R.id.welcome);


        title.animate().setDuration(800).alpha(1);
        subtitle.animate().setDuration(800).alpha(1);
        Welcome.animate().setDuration(800).alpha(1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBoardingScreen=getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
                boolean isFirstTime=onBoardingScreen.getBoolean("firstTime",true);
                if (isFirstTime){
                    SharedPreferences.Editor editor=onBoardingScreen.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();

                    startActivity(new Intent(getApplicationContext(),OnBoardingScreen.class));
                    finish();

                }else{
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }


            }
        },800);
    }

}