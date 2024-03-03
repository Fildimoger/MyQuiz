package com.example.myquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;

    private TextView textViewQuestions;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;
    private TextView textViewCorrect, textViewWrong;

    private ArrayList<Questions> questionList;
    private int questionCounter;
    private int questionTotalCount;

    private int correctAns = 0, wrongAns = 0; // Variables to be used in correct and Wrong answers text Views.

    private  FinalScoreDialog finalScoreDialog;

    private int totalSizeofQuiz = 0;
    private Questions currentQuestions;
    private boolean answerd;

    private Handler handler = new Handler(); // Handler instance to manage message queues and perform actions on the UI thread.

    private ColorStateList buttonLabelColor; // Stores a ColorStateList for managing the color of button labels.

    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private CountDownTimer countDownTimer;

    private long timeleftinMillis;

    int score =0;

    int FLAG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        
        setUpUI(); //Method that sets up the UI by calling all the views by their IDs
        fetchDB(); //Imports the DB with all of the questions

        buttonLabelColor = rb1.getTextColors();

        finalScoreDialog = new FinalScoreDialog(this);
    }

    private void setUpUI() {

        //TextViews Setup
        textViewCorrect = findViewById(R.id.txtCorrect);
        textViewWrong = findViewById(R.id.txtWrong);
        textViewCountDown = findViewById(R.id.txtViewTimer);
        textViewQuestionCount = findViewById(R.id.txtTotalQuestion);
        textViewScore = findViewById(R.id.txtScore);
        textViewQuestions = findViewById(R.id.textView3);

        //Buttons Setup
        buttonConfirmNext = findViewById(R.id.button);
        rbGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.radiobutton1);
        rb2 = findViewById(R.id.radiobutton2);
        rb3 = findViewById(R.id.radiobutton3);
        rb4 = findViewById(R.id.radiobutton4);

    }

    private void fetchDB(){
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();

        startQuiz();

    }

    private void startQuiz() {
        questionTotalCount = questionList.size();
        Collections.shuffle(questionList);

        showQuestions();

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radiobutton1) {
                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_options_selected));
                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                } else if (checkedId == R.id.radiobutton2) {
                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_options_selected));
                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                } else if (checkedId == R.id.radiobutton3) {
                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_options_selected));
                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                } else if (checkedId == R.id.radiobutton4) {
                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_options_selected));
                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
                }
            }
        });


        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!answerd){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked() ){

                        quizOperations();

                    } else {
                        Toast.makeText(QuizActivity.this, "Please select an option", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    private void quizOperations() {

        answerd = true;

        countDownTimer.cancel();

        RadioButton rbselected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNR = rbGroup.indexOfChild(rbselected) +1;

        checkSolution(answerNR, rbselected);

    }

    private void checkSolution(int answerNR, RadioButton rbselected) {

        switch (currentQuestions.getAnswerNr()){
            case 1:
                if (currentQuestions.getAnswerNr() == answerNR) {
                    rb1.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));
                    correctAns ++;
                    score = score +10;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));
                    textViewScore.setText("Score" + String.valueOf(score));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            showQuestions();

                        }
                    },500);


                } else {
                    changetoIncorrectColor(rbselected);
                    wrongAns ++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            showQuestions();

                        }
                    },500);
                }
                break;
            case 2:

                if (currentQuestions.getAnswerNr() == answerNR) {
                    rb2.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));
                    correctAns ++;
                    score = score +10;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));
                    textViewScore.setText("Score" + String.valueOf(score));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            showQuestions();

                        }
                    },500);
                } else {
                    changetoIncorrectColor(rbselected);
                    wrongAns ++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            showQuestions();

                        }
                    },500);
                }

                break;

            case 3:

                if (currentQuestions.getAnswerNr() == answerNR) {
                    rb3.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));
                    correctAns ++;
                    score = score +10;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));
                    textViewScore.setText("Score" + String.valueOf(score));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            showQuestions();

                        }
                    },500);
                } else {
                    changetoIncorrectColor(rbselected);
                    wrongAns ++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            showQuestions();

                        }
                    },500);
                }

                break;

            case 4:

                if (currentQuestions.getAnswerNr() == answerNR) {
                    rb4.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));
                    correctAns ++;
                    score = score +10;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));
                    textViewScore.setText("Score" + String.valueOf(score));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            showQuestions();

                        }
                    },500);
                } else {
                    changetoIncorrectColor(rbselected);
                    wrongAns ++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            showQuestions();

                        }
                    },500);
                }

                break;

        }
        if (questionCounter == questionTotalCount){
            buttonConfirmNext.setText("Confirm and Finish");
        }
    }

    private void changetoIncorrectColor(RadioButton rbselected) {

        rbselected.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_wrong));
    }

    private void showQuestions(){
        rbGroup.clearCheck();

        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));

        // If new question exists, it proceeds
        if (questionCounter<questionTotalCount){
            currentQuestions = questionList.get(questionCounter);
            textViewQuestions.setText(currentQuestions.getQuestion());

            // Makes the Options for the current question appear in the buttons
            rb1.setText(currentQuestions.getOption1());
            rb2.setText(currentQuestions.getOption2());
            rb3.setText(currentQuestions.getOption3());
            rb4.setText(currentQuestions.getOption4());

            questionCounter++; //Increments the Question Counter by 1
            answerd = false;

            buttonConfirmNext.setText("Confirm");
            textViewQuestionCount.setText("Questions:" + questionCounter + "/" + questionTotalCount);

            timeleftinMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();;
        }
        else {

            totalSizeofQuiz = questionList.size();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

            finalScoreDialog.finalScoreDialog(correctAns, wrongAns, totalSizeofQuiz);

                }
            }, 1000);

        }
    }

    private void startCountDown() {

        countDownTimer = new CountDownTimer(timeleftinMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeleftinMillis = millisUntilFinished;

                updateCountDownText();
            }

            @Override
            public void onFinish() {

                timeleftinMillis = 0;
                updateCountDownText();

            }
        }.start();
    }

    //the timer code
    private void updateCountDownText(){
        int minutes = (int) (timeleftinMillis/1000) / 60;
        int seconds = (int) (timeleftinMillis/1000) % 60;
        
        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        textViewCountDown.setText(timeFormatted);

        if(timeleftinMillis < 10000){
            textViewCountDown.setTextColor(Color.RED);
        } else{
            textViewCountDown.setTextColor(buttonLabelColor);
        }

        if(timeleftinMillis == 0 ){
            Toast.makeText(this, "Time is up!", Toast.LENGTH_SHORT).show();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(getApplicationContext(),QuizActivity.class);
                    startActivity(intent);

                }
            }, 2000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
    }
}