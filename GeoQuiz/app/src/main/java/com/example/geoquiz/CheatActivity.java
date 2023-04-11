package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CheatActivity extends AppCompatActivity {

    private static final String KEY_CHEAT="cheat";

    private static final String EXTRA_ANSWER_IS_TURE =
            "com.bignerdranch.android.geoquiz.answer_is_ture";
    private static final String EXTRA_ANSWER_SHOW=
            "com.bignerdranch.android.geoquiz.answer_show";

    private boolean mAnswerIsTrue;
    private boolean mIfCheat;

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    public static Intent newIntent(Context packageContext,boolean answerIsTure) {
        Intent intent = new Intent(packageContext, CheatActivity.class);

        intent.putExtra(EXTRA_ANSWER_IS_TURE, answerIsTure);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOW,false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState!=null)
        {
            mIfCheat=savedInstanceState.getBoolean(KEY_CHEAT);//接收当前问题是否作弊
        }

        mAnswerIsTrue=getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TURE,false);//接收问题的答案

        mAnswerTextView =(TextView) findViewById(R.id.answer_text_view);

        mShowAnswerButton=(Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }
                else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                mIfCheat=true;
                setAnswerShownResult(true); //使返回是否作弊数据为true
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        super.onSaveInstanceState(saveInstanceState);

        //保存当前问题是否作弊
        saveInstanceState.putBoolean(KEY_CHEAT,mIfCheat);
    }
    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data=new Intent();
        data.putExtra(EXTRA_ANSWER_SHOW,isAnswerShown);
        setResult(RESULT_OK,data);
    }
}