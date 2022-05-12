package com.bignerdranch.android.geoquiz;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAnswered;



    public Question (int textResId, boolean answerTrue , boolean answered ){
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mAnswered = answered;

    }


    public int getTextResId() {

        return mTextResId;
    }

    public void setTextResId(int textResId) {

        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {

        mAnswerTrue = answerTrue;
    }

    public boolean isAnswered() {

        return mAnswered;
    }

    public void setAnswered(boolean answered) {

        mAnswered = answered;
    }
}