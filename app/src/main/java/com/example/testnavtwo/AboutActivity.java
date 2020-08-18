package com.example.testnavtwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

    }



    public void ViewGide(View view) {

        Intent intent = new Intent(getApplicationContext(), firstStepActivity.class);
        startActivity(intent);

    }
}
