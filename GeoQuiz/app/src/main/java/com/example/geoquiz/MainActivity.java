package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_POINT = "point";
    private static final String KEY_ANSWERED = "answered";

    private Button mTureButton;
    private Button mFalseButton;

    private ImageButton mNextButton;
    private ImageButton mPrevButton;

    private TextView mQuestionTextView;

    private Question[] mQuestions = new Question[]{
            new Question(R.string.question_australia, true, false),
            new Question(R.string.question_oceans, true, false),
            new Question(R.string.question_mideast, false, false),
            new Question(R.string.question_africa, false, false),
            new Question(R.string.question_americas, true, false),
            new Question(R.string.question_asia, true, false)
    };

    private int mCurrentIndex = 0;
    private int mPoint = 0;
    private int mAnswered = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            boolean[] answerIsAnswer = savedInstanceState.getBooleanArray(KEY_ANSWER);
            for (int i = 0; i < mQuestions.length; i++) {
                if (answerIsAnswer[i])
                    mQuestions[i].Answered();
            }
            mPoint = savedInstanceState.getInt(KEY_POINT, 0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTureButton = (Button) findViewById(R.id.Ture_button);
        mTureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.False_button);
        mFalseButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        }));

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                updateQuestion();
            }
        });
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex - 1) % mQuestions.length;
                updateQuestion();
            }
        });
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                updateQuestion();
            }
        });
        updateQuestion();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        saveInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        //保存当前问题编号

        //保存问题是否已作答
        boolean[] answerIfAnswer = new boolean[mQuestions.length];
        for (int i = 0; i < mQuestions.length; i++) {
            answerIfAnswer[i] = mQuestions[i].isIfAnswer();
        }
        saveInstanceState.putBooleanArray(KEY_ANSWER, answerIfAnswer);

        //保存答题得分
        saveInstanceState.putInt(KEY_POINT, mPoint);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestions[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTure) {
        boolean answerIsTure = mQuestions[mCurrentIndex].isAnswerTure();

        int messageResId;


        if (!mQuestions[mCurrentIndex].isIfAnswer()) {
            if (userPressedTure == answerIsTure) {
                messageResId = R.string.correct_toast;
                mPoint++;
            } else {
                messageResId = R.string.incorrect_toast;
            }
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
            mQuestions[mCurrentIndex].Answered();
            mAnswered++;
            if (mAnswered == mQuestions.length) {
                String pointString = String.valueOf((double) mPoint / mAnswered * 100) + "%";
                Toast.makeText(this, pointString, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Answered!", Toast.LENGTH_SHORT).show();
        }

    }

}