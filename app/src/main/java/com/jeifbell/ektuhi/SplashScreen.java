package com.jeifbell.ektuhi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.ImageView;

public class SplashScreen extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy); 

   		final ImageView logo = (ImageView) findViewById(R.id.imgLogo);
        final int[] thumbnail = {R.drawable.logo1, R.drawable.logo2,
        		R.drawable.logo3, R.drawable.logo4,R.drawable.logo5,
        		R.drawable.logo6,R.drawable.logo7,R.drawable.logo6, R.drawable.logo5,
        		R.drawable.logo4, R.drawable.logo3,R.drawable.logo2};
        
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
           int i=0;
           public void run() {
        	   logo.setImageResource(thumbnail[i]);
               i++;
               if(i>thumbnail.length-1)
               {
            	   i=0;
            	   handler.postDelayed(this, 100);
               }
               else{
            	   handler.postDelayed(this, 100);
               }
           }
       };
       handler.postDelayed(runnable, 1000);

        Thread logoTimer = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally{
                    finish();
                }
            }

        };
        logoTimer.start();
    }

}
