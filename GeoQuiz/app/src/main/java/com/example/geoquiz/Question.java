package com.example.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTure;
    private boolean mIfAnswer;
    private boolean mIfCheat;

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

    public boolean isIfCheat() {
        return mIfCheat;
    }

    public void Answered() {
        mIfAnswer = true;
    }

    public void Cheated() {
        mIfCheat = true;
    }
}
