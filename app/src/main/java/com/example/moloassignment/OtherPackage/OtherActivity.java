package com.example.moloassignment.OtherPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.moloassignment.AccountActivity;
import com.example.moloassignment.QuizPackage.MainQuizActivity;
import com.example.moloassignment.R;
import com.example.moloassignment.TempUserDashboard;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import NotePackage.MainNotesActivity;

public class OtherActivity extends AppCompatActivity {
    ImageButton Calculator_button, dice_application, background_application;
    ImageView backButtonOther;
    BottomNavigationView temp_bottom_navigation_other;
    private static final String TAG = "OtherActivity";

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
        setContentView(R.layout.activity_other);
        Log.d(TAG, "Activity at OnCreateState");

        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String username = getIntent().getStringExtra("username");

        Calculator_button = findViewById(R.id.Calculator_button);
        dice_application = findViewById(R.id.dice_application);
        backButtonOther = findViewById(R.id.backButtonOther);
        background_application = findViewById(R.id.background_application);
        temp_bottom_navigation_other = findViewById(R.id.temp_bottom_navigation_other);

        Calculator_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculator_button.setBackgroundResource(R.drawable.white_border);
                Intent calculatorIntent = new Intent(OtherActivity.this, CalculatorActivity.class);
                calculatorIntent.putExtra("username", username);
                calculatorIntent.putExtra("loginDetails", loginDetails);
                startActivity(calculatorIntent);

                finish();
            }
        });

        dice_application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dice_application.setBackgroundResource(R.drawable.white_border);
                Intent diceIntent = new Intent(OtherActivity.this, DiceActivity.class);
                diceIntent.putExtra("username", username);
                diceIntent.putExtra("loginDetails", loginDetails);
                startActivity(diceIntent);

                finish();
            }
        });

        backButtonOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userDashboardIntent = new Intent(OtherActivity.this, TempUserDashboard.class);
                userDashboardIntent.putExtra("username", username);
                userDashboardIntent.putExtra("loginDetails", loginDetails);
                startActivity(userDashboardIntent);
                finish();
            }
        });

        background_application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background_application.setBackgroundResource(R.drawable.white_border);
                Intent backgroundIntent = new Intent(OtherActivity.this, BackgroundActivity.class);
                backgroundIntent.putExtra("username", username);
                backgroundIntent.putExtra("loginDetails", loginDetails);
                startActivity(backgroundIntent);

                finish();
            }
        });
        setupBottomNavigationView();
    }
    private void setupBottomNavigationView() {
        temp_bottom_navigation_other.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
            String username = getIntent().getStringExtra("username");
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_nav:
                        Intent homeIntent = new Intent(OtherActivity.this, TempUserDashboard.class);
                        homeIntent.putExtra("username", username);
                        homeIntent.putExtra("loginDetails", loginDetails);
                        startActivity(homeIntent);
                        return true;
                    case R.id.quiz_nav:
                        Intent quizIntent = new Intent(OtherActivity.this, MainQuizActivity.class);
                        quizIntent.putExtra("username", username);
                        quizIntent.putExtra("loginDetails", loginDetails);
                        startActivity(quizIntent);
                        return true;
                    case R.id.file_nav:
                        Intent fileIntent = new Intent(OtherActivity.this, MainNotesActivity.class);
                        fileIntent.putExtra("username", username);
                        fileIntent.putExtra("loginDetails", loginDetails);
                        startActivity(fileIntent);
                        return true;
                    case R.id.account_nav:
                        Intent accountIntent = new Intent(OtherActivity.this, AccountActivity.class);
                        accountIntent.putExtra("username", username);
                        accountIntent.putExtra("loginDetails", loginDetails);
                        startActivity(accountIntent);
                        return true;
                    case R.id.other_nav:
                        Intent otherIntent = new Intent(OtherActivity.this, OtherActivity.class);
                        otherIntent.putExtra("username", username);
                        otherIntent.putExtra("loginDetails", loginDetails);
                        startActivity(otherIntent);
                        return true;
                }
                return false;
            }
        });
    }
}