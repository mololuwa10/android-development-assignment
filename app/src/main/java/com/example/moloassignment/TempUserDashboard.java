package com.example.moloassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moloassignment.OtherPackage.OtherActivity;
import com.example.moloassignment.QuizPackage.MainQuizActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import NotePackage.MainNotesActivity;

public class TempUserDashboard extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private ArrayList<Integer> integerArrayList;
    TextView dateTextView, usernameTextView, timeLineTextView;
    BottomNavigationView bottomNavigationView;

    private static final String TAG = "TempUserDashboard";

    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Activity at onStartState");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity at onDestroyState");
    }

    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Activity at onStopSate");
    }

    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Activity at onPauseState");
    }

    // https://developer.android.com/reference/java/text/DateFormat
    Date currentDate = Calendar.getInstance().getTime();
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.UK);
    String dateString = dateFormat.format(currentDate);
    // -----------------------------------------------------------------------

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_user_dashboard);

        Log.d(TAG, "Activity at OnCreateState");

        dateTextView = findViewById(R.id.tempDateTextView);
        usernameTextView = findViewById(R.id.tempUsernameTextView);
        bottomNavigationView = findViewById(R.id.temp_bottom_navigation);
        drawerLayout = findViewById(R.id.drawer_layout);
        timeLineTextView = findViewById(R.id.timeline_text);
        recyclerView = findViewById(R.id.image_recycler_view);

        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String userName = getIntent().getStringExtra("username");

        dateTextView.setText(dateString);
        timeLineTextView.setText("TODAY'S TIMELINE");

        // I am getting the login details from the intent extra
        String[] userDetails = new String[2];

        for (String[] details : loginDetails) {
            if (details[0].equals(userName)) {
                userDetails = details;
                break;
            }
        }
        if (!userDetails[0].isEmpty()) {
            String username = userDetails[0];
            usernameTextView.setText("Hi, " + username);
        } else {
            Intent loginActivity = new Intent(TempUserDashboard.this, MainActivity.class);
            Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show();
            startActivity(loginActivity);
        }
        setupBottomNavigationView();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Activity at OnResumeState");
        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {
        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String username = getIntent().getStringExtra("username");
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_nav:
                        Intent homeIntent = new Intent(TempUserDashboard.this, TempUserDashboard.class);
                        homeIntent.putExtra("username", username);
                        homeIntent.putExtra("loginDetails", loginDetails);
                        startActivity(homeIntent);
                        return true;
                    case R.id.quiz_nav:
                        Intent quizIntent = new Intent(TempUserDashboard.this, MainQuizActivity.class);
                        quizIntent.putExtra("username", username);
                        quizIntent.putExtra("loginDetails", loginDetails);
                        startActivity(quizIntent);
                        return true;
                    case R.id.file_nav:
                        Intent fileIntent = new Intent(TempUserDashboard.this, MainNotesActivity.class);
                        fileIntent.putExtra("username", username);
                        fileIntent.putExtra("loginDetails", loginDetails);
                        startActivity(fileIntent);
                        return true;
                    case R.id.account_nav:
                        Intent accountIntent = new Intent(TempUserDashboard.this, AccountActivity.class);
                        accountIntent.putExtra("loginDetails", loginDetails);
                        accountIntent.putExtra("username", username);
                        startActivity(accountIntent);
                        return true;
                    case R.id.other_nav:
                        Intent otherIntent = new Intent(TempUserDashboard.this, OtherActivity.class);
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
