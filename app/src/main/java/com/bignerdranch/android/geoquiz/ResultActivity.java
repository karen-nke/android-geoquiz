package com.bignerdranch.android.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class ResultActivity extends AppCompatActivity {
    private static final int RIGHT_ANSWER_COUNT = 0;
    private static final int TOTAL_SCORE = 0;

    private TextView mResultLabel;
    private TextView mTotalScoreLabel;
    private Button mBackButton;

    private TextView mCongrats;



    public static final Intent newIntent1(Context packageContext, int answeredCorrect, int score) {
        Intent intent = new Intent(packageContext, ResultActivity.class);
        intent.putExtra("RIGHT_ANSWER_COUNT", answeredCorrect);
        intent.putExtra("TOTAL_SCORE", score);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String Result = intent.getStringExtra("percentage");
        String Score = intent.getStringExtra("score");



        mResultLabel = findViewById(R.id.resultLabel);
        mTotalScoreLabel = findViewById(R.id.totalScoreLabel);

        mTotalScoreLabel.setText("Total Score: " + Result);
        mResultLabel.setText("Correct Answer: " + Score + "/6");

        mBackButton =(Button)findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                finish();
            }
        });

        mCongrats=findViewById(R.id.congrats);
        mCongrats.setText("Congrats");


    }

}