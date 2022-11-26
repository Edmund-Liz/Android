package com.example.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTure;
    private  boolean mIfAnswer;

    public Question(int textResId, boolean answerTure, boolean ifAnswer) {
        mTextResId = textResId;
        mAnswerTure = answerTure;
        mIfAnswer = ifAnswer;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTure() {
        return mAnswerTure;
    }

    public void setAnswerTure(boolean answerTure) {
        mAnswerTure = answerTure;
    }

    public boolean isIfAnswer() {
        return mIfAnswer;
    }
    public void Answered(){
        mIfAnswer=true;
    }

}
