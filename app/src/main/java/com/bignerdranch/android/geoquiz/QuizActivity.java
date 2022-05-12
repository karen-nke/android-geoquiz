package com.bignerdranch.android.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.app.Activity;



public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private static final String KEY_SCORE = "score";
    private static final String KEY_CHEATER = "cheater";
    private static final String KEY_TOKENS = "tokens";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final String KEY_ANSWERED = "answered";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private Button mResetButton;
    private TextView mQuestionTextView ;
    private TextView mRemainingTokensTextView ;
    private String mStringScore;
    private int mPercentage;
    private int mIsAnswered =0;
    private TextView mAnsweredTextView;
    private TextView mNameDisplay;
    private String mName;

    private Question[]mQuestionBank = new Question[]{
            new Question(R.string.question_australia,true,false),
            new Question(R.string.question_oceans,true,false),
            new Question(R.string.question_mideast,false,false),
            new Question(R.string.question_africa,false,false),
            new Question(R.string.question_americas,true,false),
            new Question(R.string.question_asia,true,false)
    };


    private int mCurrentIndex = 0;
    private int mCurrentScore = 0;
    private int mRemainingCheatTokens = 3;
    private boolean[] mIsCheater = new boolean[mQuestionBank.length];


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null){
            mCurrentIndex =savedInstanceState.getInt(KEY_INDEX,0);
            mCurrentScore = savedInstanceState.getInt(KEY_SCORE, 0);
            mRemainingCheatTokens = savedInstanceState.getInt(KEY_TOKENS, 3);
            mIsCheater = savedInstanceState.getBooleanArray(KEY_CHEATER);
            mIsAnswered = savedInstanceState.getInt(KEY_ANSWERED,0);
        }

        mNameDisplay=findViewById(R.id.namedisplay);
        mName = getIntent().getExtras().getString("userinput");
        mNameDisplay.setText("Hello ! " + mName);



        mAnsweredTextView = (TextView)findViewById(R.id.question_answered);
        mAnsweredTextView.setText("Questions answered : "+ mIsAnswered + "/" + mQuestionBank.length);


        mQuestionTextView= (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
                updateQuestion();
            }
        });

        mRemainingTokensTextView = findViewById(R.id.cheat_tokens_text_view);
        mRemainingTokensTextView.setText("Remaining Cheat Tokens: " + mRemainingCheatTokens);

        mTrueButton =(Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(true);
                toggleAnswerButtonTo(false);
                mQuestionBank[mCurrentIndex].setAnswered(true);



            }
        });

        mFalseButton =(Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(false);
                toggleAnswerButtonTo(false);
                mQuestionBank[mCurrentIndex].setAnswered(true);


            }
        });

        mNextButton =(ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                mIsCheater[mCurrentIndex] = false;
                nextQuestion();
                updateQuestion();
            }
        });

        mPrevButton =(ImageButton)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                previousQuestion();
                updateQuestion();
            }
        });

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start CheatActivity
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });



        mResetButton =(Button)findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                reset();
            }
        });

        updateQuestion();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater[mCurrentIndex] = CheatActivity.wasAnswerShown(data);
            //Challenge: Limited to 3 cheats
            mRemainingCheatTokens--;
            mRemainingTokensTextView.setText("Remaining Cheat Tokens: " + mRemainingCheatTokens);
            if (mRemainingCheatTokens == 0) {
                mCheatButton.setEnabled(false);
            }
        }

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState() called");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_SCORE, mCurrentScore);
        savedInstanceState.putInt(KEY_TOKENS, mRemainingCheatTokens);
        savedInstanceState.putBooleanArray(KEY_CHEATER, mIsCheater);
        savedInstanceState.putInt(KEY_ANSWERED,mIsAnswered);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Log.d(TAG,"onRestart() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }



    private void reset(){

        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);

    }



    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        if(mQuestionBank[mCurrentIndex].isAnswered()== true){
            toggleAnswerButtonTo(false);
        }else {
            toggleAnswerButtonTo(true);
        }

    }

    private void previousQuestion() {

        if(mCurrentIndex != 0) {
            mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;

        } else {
            mCurrentIndex = mQuestionBank.length - 1;
        }

    }

    private void nextQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        if (mCurrentIndex == 0) {
            mCurrentScore = 0;
        }
    }

    public void showScore() {
        mPercentage = (int) (((double)mCurrentScore/mQuestionBank.length)*100);
        mStringScore = "You got " + mPercentage + "% correct answers";
        Toast.makeText(this, mStringScore, Toast.LENGTH_SHORT).show();

        Intent intent2 = new Intent(getApplicationContext(),ResultActivity.class);
        String result = Integer.toString(mPercentage);
        intent2.putExtra("percentage", result);
        String score = Integer.toString(mCurrentScore);
        intent2.putExtra("score", score);
        startActivity(intent2);


    }

    private void answered (){
        mQuestionBank[mCurrentIndex].setAnswered(true);
        toggleAnswerButtonTo(false);

    }



    private void toggleAnswerButtonTo(boolean b) {
        if (b == false) {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        } else if (b == true) {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
    }



    private void checkAnswer(boolean userPressedTrue) {
        answered();

        toggleAnswerButtonTo(false);

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (mIsCheater[mCurrentIndex]) {
            messageResId = R.string.judgement_toast;
            //Cheaters doesn't score
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mCurrentScore += 1;
                mIsAnswered += 1;
                mAnsweredTextView.setText("Questions answered : "+ mIsAnswered + "/" + mQuestionBank.length);
                updateQuestion();


                Log.d(TAG, mCurrentScore + "");
            } else {
                messageResId = R.string.incorrect_toast;
                mIsAnswered += 1;
                mAnsweredTextView.setText("Questions answered : "+ mIsAnswered + "/" + mQuestionBank.length);

                updateQuestion();

            }

        }
        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 20);
        toast.show();


        if (mCurrentIndex == mQuestionBank.length -1){
            showScore();

        }

    }

}









