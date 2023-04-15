package com.example.moloassignment.FilePackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moloassignment.AccountActivity;
import com.example.moloassignment.OtherPackage.OtherActivity;
import com.example.moloassignment.QuizPackage.MainQuizActivity;
import com.example.moloassignment.R;
import com.example.moloassignment.TempUserDashboard;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TempFileManagement extends AppCompatActivity {
    EditText edtTextFileName, edtTextFileContent;
    AppCompatButton tempNewFileButton;
    FileViewAdapter fileViewAdapter;
    RecyclerView recyclerView;
    TextView createFileText;
    ArrayList<MyFile> fileList;
    private static final int REQUEST_CODE = 1;
    BottomNavigationView file_bottom_navigation;
    private static final String TAG = "TempFileManagement";

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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_file_management);

        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String username = getIntent().getStringExtra("username");

        edtTextFileContent = findViewById(R.id.edtTextFileContent);
        edtTextFileName = findViewById(R.id.edtTextFileName);
        createFileText = findViewById(R.id.createFileText);
        tempNewFileButton = findViewById(R.id.tempNewFileButton);
        recyclerView = findViewById(R.id.fileRecyclerView);
        file_bottom_navigation = findViewById(R.id.file_bottom_navigation);

        fileList = new ArrayList<>();
        fileViewAdapter = new FileViewAdapter(fileList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(fileViewAdapter);

        tempNewFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempNewFileButton.setBackgroundResource(R.drawable.yellow_border);
                if (checkStoragePermission()) {
                    createNewFile();
                }
            }
        });
        loadFiles();
        setupBottomNavigationView();
    }

    private boolean checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(TempFileManagement.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TempFileManagement.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            return false;
        }
        return true;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewFile() {
        String userFileNameInput = edtTextFileName.getText().toString();
        String userFileContentInput = edtTextFileContent.getText().toString();

        if (TextUtils.isEmpty(userFileNameInput) || TextUtils.isEmpty(userFileContentInput)) {
            Snackbar.make(tempNewFileButton, "Please fill in both file name and content", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // https://developer.android.com/reference/java/text/DateFormat
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.UK);
        String dateString = dateFormat.format(currentDate);

        MyFile file;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            file = new MyFile(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), userFileNameInput + " " + dateString + ".txt", userFileContentInput);
        } else {
            file = new MyFile(getExternalFilesDir(null), userFileNameInput + " " + dateString + ".txt", userFileContentInput);
        }
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file.getFile());
            fileOutputStream.write(userFileContentInput.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();

            fileList.add(file);
            fileViewAdapter.notifyDataSetChanged();

            edtTextFileName.setText("");
            edtTextFileContent.setText("");

            Toast.makeText(this, "File created successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to create file", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadFiles() {
        File[] files = getExternalFilesDir(null).listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                String fileContent = "";
                try {
                    byte[] bytes = new byte[(int) file.length()];
                    FileInputStream fileInputStream = new FileInputStream(file);
                    fileInputStream.read(bytes);
                    fileInputStream.close();
                    fileContent = new String(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MyFile myFile = new MyFile(file, fileName, fileContent);
                fileList.add(myFile);
            }
        }
        fileViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                createNewFile();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Permission is required to access the files",
                                Snackbar.LENGTH_INDEFINITE).setAction("Allow", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat.requestPermissions(TempFileManagement.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                            }
                        })
                        .show();
            }
        }
    }

    private void setupBottomNavigationView() {
        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String username = getIntent().getStringExtra("username");

        file_bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_nav:
                        Intent homeIntent = new Intent(TempFileManagement.this, TempUserDashboard.class);
                        homeIntent.putExtra("username", username);
                        homeIntent.putExtra("loginDetails", loginDetails);
                        startActivity(homeIntent);
                        return true;
                    case R.id.quiz_nav:
                        Intent quizIntent = new Intent(TempFileManagement.this, MainQuizActivity.class);
                        quizIntent.putExtra("username", username);
                        quizIntent.putExtra("loginDetails", loginDetails);
                        startActivity(quizIntent);
                        return true;
                    case R.id.file_nav:
                        Intent fileIntent = new Intent(TempFileManagement.this, TempFileManagement.class);
                        fileIntent.putExtra("username", username);
                        fileIntent.putExtra("loginDetails", loginDetails);
                        startActivity(fileIntent);
                        return true;
                    case R.id.account_nav:
                        Intent accountIntent = new Intent(TempFileManagement.this, AccountActivity.class);
                        accountIntent.putExtra("loginDetails", loginDetails);
                        accountIntent.putExtra("username", username);
                        startActivity(accountIntent);
                        return true;
                    case R.id.other_nav:
                        Intent otherIntent = new Intent(TempFileManagement.this, OtherActivity.class);
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