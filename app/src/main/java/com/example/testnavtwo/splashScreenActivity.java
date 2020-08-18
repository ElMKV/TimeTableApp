package com.example.testnavtwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class splashScreenActivity extends AppCompatActivity {

    private ImageView image;
    private AnimatedVectorDrawable animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        image = (ImageView) findViewById(R.id.object);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Drawable d = image.getDrawable();
        if (d instanceof AnimatedVectorDrawable) {

            animation = (AnimatedVectorDrawable) d;
            animation.start();

            Thread logoTimer = new Thread()
            {
                public void run()
                {
                    try
                    {
                        int logoTimer = 0;


                        while(logoTimer < 1400)
                        {
                            sleep(100);
                            logoTimer = logoTimer +100;
                        };
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);








                    }
                    catch (InterruptedException e)
                    {
                        // TODO: автоматически сгенерированный блок catch.
                        e.printStackTrace();
                    }
                    finally
                    {
                        finish();




                    }
                }
            };
            logoTimer.start();
        }
    }


}
