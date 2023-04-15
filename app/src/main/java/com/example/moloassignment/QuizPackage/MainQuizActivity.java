package com.example.moloassignment.QuizPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.moloassignment.R;

public class MainQuizActivity extends AppCompatActivity {
    LinearLayout java, android;
    Button startQuizButton;
    String topicName = "";

    private static final String TAG = "MainQuizActivity";

    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Activity at onStartState");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity at onDestroyState");
    }

    protected  void onResume() {
        super.onResume();
        Log.d(TAG, "Activity at onResumeState");
    }

    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Activity at onStopSate");
    }

    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Activity at onPauseState");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);

        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String username = getIntent().getStringExtra("username");

        java = findViewById(R.id.javaTest);
        android = findViewById(R.id.androidTest);

        Log.d(TAG, "Activity at OnCreateStage");

        startQuizButton = findViewById(R.id.startQuizButton);

        java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicName = "java";
                java.setBackgroundResource(R.drawable.white_border);
                android.setBackgroundResource(0);
            }
        });

        android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicName = "android";
                java.setBackgroundResource(0);
                android.setBackgroundResource(R.drawable.white_border);
            }
        });

        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(topicName.isEmpty()) {
                    Toast.makeText(MainQuizActivity.this, "Please select the topic", Toast.LENGTH_SHORT).show();
                } else {
                    Intent quizIntent = new Intent(MainQuizActivity.this, QuizActivity.class);
                    quizIntent.putExtra("topicName", topicName);
                    startActivity(quizIntent);
                }
            }
        });
    }

}