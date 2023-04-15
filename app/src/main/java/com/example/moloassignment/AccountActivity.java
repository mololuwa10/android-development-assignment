package com.example.moloassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.moloassignment.OtherPackage.OtherActivity;
import com.example.moloassignment.QuizPackage.MainQuizActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import NotePackage.MainNotesActivity;

public class AccountActivity extends AppCompatActivity {
    TextView nameOfUser;
    Button edit_profile_button, logout_button;
    BottomNavigationView account_bottom_navigation;
    private static final String TAG = "AccountActivity";

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
        setContentView(R.layout.activity_account);
        Log.d(TAG, "Activity at OnCreateState");

        nameOfUser = findViewById(R.id.nameOfUser);
        edit_profile_button = findViewById(R.id.edit_profile_button);
        logout_button = findViewById(R.id.logout_button);
        account_bottom_navigation = findViewById(R.id.account_bottom_navigation);

        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String username = getIntent().getStringExtra("username");

        String[] userDetails = null;

        for (String[] details : loginDetails) {
            if (details[0].equals(username)) {
                userDetails = details;
                break;
            }
        }
        if (userDetails != null && !userDetails[0].isEmpty()) {
            String userName = userDetails[0];
            nameOfUser.setText(userName);
        }

        edit_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logOutIntent = new Intent(AccountActivity.this, MainActivity.class);
//                logOutIntent.putExtra("loginDetails", loginDetails);
//                logOutIntent.putExtra("username", username);
                startActivity(logOutIntent);
            }
        });
        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {
        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String username = getIntent().getStringExtra("username");
        account_bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_nav:
                        Intent homeIntent = new Intent(AccountActivity.this, TempUserDashboard.class);
                        homeIntent.putExtra("username", username);
                        homeIntent.putExtra("loginDetails", loginDetails);
                        startActivity(homeIntent);
                        return true;
                    case R.id.quiz_nav:
                        Intent quizIntent = new Intent(AccountActivity.this, MainQuizActivity.class);
                        quizIntent.putExtra("username", username);
                        quizIntent.putExtra("loginDetails", loginDetails);
                        startActivity(quizIntent);
                        return true;
                    case R.id.file_nav:
                        Intent fileIntent = new Intent(AccountActivity.this, MainNotesActivity.class);
                        fileIntent.putExtra("username", username);
                        fileIntent.putExtra("loginDetails", loginDetails);
                        startActivity(fileIntent);
                        return true;
                    case R.id.account_nav:
                        Intent accountIntent = new Intent(AccountActivity.this, AccountActivity.class);
                        accountIntent.putExtra("loginDetails", loginDetails);
                        accountIntent.putExtra("username", username);
                        startActivity(accountIntent);
                        return true;
                    case R.id.other_nav:
                        Intent otherIntent = new Intent(AccountActivity.this, OtherActivity.class);
                        otherIntent.putExtra("loginDetails", loginDetails);
                        otherIntent.putExtra("username", username);
                        startActivity(otherIntent);
                        return true;
                }
                return false;
            }
        });
    }
}