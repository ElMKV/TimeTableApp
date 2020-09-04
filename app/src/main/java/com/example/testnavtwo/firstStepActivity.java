package com.example.testnavtwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class firstStepActivity extends AppCompatActivity {
    int count = 1;
    ImageView imageView;
    TextView textView, textViewDis;
    View viewDotOne,viewDotTwo,viewDotThree,viewDotFour,viewDotFive,viewDotSix, viewDotSeven;
    Button buttonNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_step);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);


        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.hiTextView);
        textViewDis = findViewById(R.id.textViewDis);
        buttonNext = findViewById(R.id.nextButton);

        viewDotOne = findViewById(R.id.viewDot1);
        viewDotTwo = findViewById(R.id.viewDot2);
        viewDotThree = findViewById(R.id.viewDot3);
        viewDotFour = findViewById(R.id.viewDot4);
        viewDotFive = findViewById(R.id.viewDot5);
        viewDotSix = findViewById(R.id.viewDot6);
        viewDotSeven = findViewById(R.id.viewDot7);

        imageView.setImageResource(R.mipmap.plane);
        viewDotOne.setBackgroundResource(R.drawable.dot_color);




    }


    public void nextImage(View view) {

        count++;

        switch (count)
        {


            case 2:
                imageView.setImageResource(R.drawable.for_you);
                viewDotTwo.setBackgroundResource(R.drawable.dot_color);

                textView.setText("Это приложение созданно специально для вас");
                textViewDis.setText("Мы добавили щепотку волшебства в это приложения");

                break;
            case 3:
                imageView.setImageResource(R.mipmap.search);
                viewDotThree.setBackgroundResource(R.drawable.dot_color);

                textView.setText("Нажмите на сайт");
                textViewDis.setText("Ведь нам важен ваш выбор");

                break;

            case 4:
                imageView.setImageResource(R.mipmap.to_do);
                viewDotFour.setBackgroundResource(R.drawable.dot_color);

                textView.setText("Выберите интересующие замены занятий");
                textViewDis.setText("А дальше сделаем все мы");

                break;


            case 5:
                imageView.setImageResource(R.mipmap.home);
                viewDotFive.setBackgroundResource(R.drawable.dot_color);

                textView.setText("После сохранения нажмите два раза на домик");
                textViewDis.setText("И замены появятся у вас на экране");


                break;


            case 6:
                imageView.setImageResource(R.mipmap.file);
                viewDotSix.setBackgroundResource(R.drawable.dot_color);
                textViewDis.setText("");

                textView.setText("Мы не заполняем вашу памянть излишними файлами");
                textViewDis.setText("Магическим образом лишние файлы изчезнут");

                buttonNext.setText("Начать");



                break;

            case 7:
                imageView.setImageResource(R.mipmap.simple);
                viewDotSeven.setBackgroundResource(R.drawable.dot_color);
                textViewDis.setText("");

                textView.setText("Это так просто и быстро!");
                textViewDis.setText("Ваше удобство - наша задача");

                buttonNext.setText("Начать");


                break;

            case 8:




                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;

        }



    }


}
