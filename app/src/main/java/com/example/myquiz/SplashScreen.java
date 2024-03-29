package com.example.myquiz;

import static java.lang.Thread.sleep;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView txtSplashText;
    ImageView imgViewLogo;

    private final static int EXIT_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        txtSplashText = findViewById(R.id.textviewLogoText);
        imgViewLogo = findViewById(R.id.imgviewLogo);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transition);
        imgViewLogo.setAnimation(animation);
        txtSplashText.setAnimation(animation);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    sleep(3000);

                } catch (Exception e){
                    e.printStackTrace();
                }finally {
                    GoToQuizActivity();
                }
            }
        });
        thread.start();
    }

    private void GoToQuizActivity() {

        startActivityForResult(new Intent(SplashScreen.this, QuizActivity.class), EXIT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == EXIT_CODE){
            if(resultCode == RESULT_OK){
                if(data.getBooleanExtra("EXIT", true)){
                    finish();
                };

            }
        }
    }
}