package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";//当前问题编号
    private static final String KEY_NOW_CHEAT="now_cheat";//当前问题是否作弊
    private static final int REQUEST_CHEAT = 0;

    private static final String KEY_ANSWER = "answer"; //问题的是否作答boolean数组
    private static final String KEY_CHEAT="cheat";  //问题的是否作弊boolean数组
    private static final String KEY_POINT = "point"; //得分数据
    private static final String KEY_ANSWERED = "answered"; //已作答的问题数量


    private Button mTureButton;
    private Button mFalseButton;

    private ImageButton mNextButton;
    private Button mCheatButton;
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
    private boolean mIsCheater;
    private int mPoint = 0;
    private int mAnswered = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");//在日志中显示
        setContentView(R.layout.activity_main);

        //如果此活动曾经被创建，接收问题编号数据，已作答数据，得分数据
        if (savedInstanceState != null) {
            //接收问题编号
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

            //接收问题的是否作答boolean数组
            boolean[] answerIsAnswer = savedInstanceState.getBooleanArray(KEY_ANSWER);
            for (int i = 0; i < mQuestions.length; i++) {
                if (answerIsAnswer[i])
                    mQuestions[i].Answered();
            }

            //接收问题的是否作弊boolean数组
            boolean[] answerIsCheat=savedInstanceState.getBooleanArray(KEY_CHEAT);
            for (int i=0;i<mQuestions.length;i++){
                if (answerIsCheat[i])
                    mQuestions[i].Cheated();
            }

            //接收已作答数据
            mAnswered=savedInstanceState.getInt(KEY_ANSWERED,0);

            //接收得分数据
            mPoint = savedInstanceState.getInt(KEY_POINT, 0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        //正确错误按钮
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

        //前进后退按钮
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
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

        //问题文本
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                updateQuestion();
            }
        });

        //Cheat按钮
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //启动CheatActivity
                boolean answerIsTrue = mQuestions[mCurrentIndex].isAnswerTure();//获取问题答案
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);//存储问题答案
                startActivityForResult(intent, REQUEST_CHEAT);//携带问题答案启动Cheat界面
            }
        });
        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        mIsCheater = CheatActivity.wasAnswerShown(data);//接收在CheatActivity中是否作弊
        //立即将作弊数据注入Boolean数组，并将mIsCheater置为false;
        if(mIsCheater){
            mQuestions[mCurrentIndex].Cheated();
            mIsCheater=false;
        }
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

        //保存当前问题编号
        saveInstanceState.putInt(KEY_INDEX, mCurrentIndex);

        //保存问题是否已作答的boolean数组
        boolean[] answerIfAnswer = new boolean[mQuestions.length];
        for (int i = 0; i < mQuestions.length; i++) {
            answerIfAnswer[i] = mQuestions[i].isIfAnswer();
        }
        saveInstanceState.putBooleanArray(KEY_ANSWER, answerIfAnswer);

        //保存问题是否作弊的boolean数组
        boolean[] answerIfCheat =new boolean[mQuestions.length];
        for (int i =0;i<mQuestions.length;i++){
            answerIfCheat[i]=mQuestions[i].isIfCheat();
        }
        saveInstanceState.putBooleanArray(KEY_CHEAT,answerIfCheat);

        //保存答题得分
        saveInstanceState.putInt(KEY_POINT, mPoint);

        //保存已作答问题数量
        saveInstanceState.putInt(KEY_ANSWERED,mAnswered);
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

    //更新问题
    private void updateQuestion() {
        int question = mQuestions[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }


    //检查答案
    private void checkAnswer(boolean userPressedTure) {
        boolean answerIsTure = mQuestions[mCurrentIndex].isAnswerTure();

        int messageResId;

        //检查答案是否作答
        if (!mQuestions[mCurrentIndex].isIfAnswer()) {
            //答案未作答，检查答案是否作弊
            if (mQuestions[mCurrentIndex].isIfCheat()) {
                messageResId = R.string.judgment_toast;
            } else {
                //检查输入的答案与正确答案是否相同
                if (userPressedTure == answerIsTure) {
                    messageResId = R.string.correct_toast;
                    mPoint++;
                } else {
                    messageResId = R.string.incorrect_toast;
                }
            }
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();//显示答题是否正确
            mQuestions[mCurrentIndex].Answered();//将当前问题标记为已作答
            mAnswered++;//已作答的题数加1

            //检查问题是否已经全部作答
            if (mAnswered == mQuestions.length) {
                String pointString = (double) mPoint / mAnswered * 100 + "%";//计算得分（百分比形式）
                Toast.makeText(this, pointString, Toast.LENGTH_SHORT).show();
            }
        } else {//问题已作答，显示Answered
            Toast.makeText(this, "Answered!", Toast.LENGTH_SHORT).show();
        }

    }

}