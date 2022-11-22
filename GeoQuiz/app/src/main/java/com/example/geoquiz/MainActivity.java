package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mTureButton;
    private Button mFalseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTureButton = (Button) findViewById(R.id.Ture_button);
        mTureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        R.string.correct_toast,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
            }
        });
        mFalseButton = (Button) findViewById(R.id.False_button);
        mFalseButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        R.string.incorrect_toast,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
            }
        }));
    }
}