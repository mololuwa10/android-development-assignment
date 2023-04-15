package com.example.moloassignment.FilePackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.moloassignment.QuizPackage.QuizActivity;
import com.example.moloassignment.R;
import com.example.moloassignment.TempUserDashboard;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressLint("MissingInflatedId")
public class FileManagement extends AppCompatActivity {
    Button newFileButton;
    BottomNavigationView bottomNavigationView;
    RecyclerView rvList;
    private static final String TAG = "FileActivity";

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_management);

        ArrayList<File> studentFiles = new ArrayList<File>();
        bottomNavigationView = findViewById(R.id.file_bottom_navigation);
        newFileButton = findViewById(R.id.newFileButton);

//        rvList = findViewById(R.id.rv_list);

        Intent dataFromUserBoard = getIntent();

        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String userName = dataFromUserBoard.getStringExtra("username");

        // https://developer.android.com/reference/java/text/DateFormat
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.UK);
        String dateString = dateFormat.format(currentDate);
        // -----------------------------------------------------------------------

//        newFileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String[]  userDetails = null;
//                String filename = null;
//
//                for (String[] details : loginDetails) {
//                    if (details[0].equals(userName)) {
//                        userDetails = details;
//                        break;
//                    }
//                }
//                if (userDetails != null) {
//                    String username = userDetails[0];
//                    filename = "gje" + dateString + ".pdf";
//                    File newFile = new File(getFilesDir(), filename);
//                if(newFile.exists()){
//                    Log.d("File", "File exists");
//                } else {
//                    Log.d("File", "File does not exist");
//                }
//                    try {
//                        boolean fileCreatedSuccessfully = newFile.createNewFile();
//                        if(fileCreatedSuccessfully) {
//                            studentFiles.add(newFile);
//                            Toast.makeText(FileManagement.this, "File Created Successfully", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(FileManagement.this, "File already exists", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        RecyclerViewClass recyclerViewClass = new RecyclerViewClass(studentFiles);
//        rvList.setLayoutManager(new LinearLayoutManager(this));
//        rvList.setAdapter(recyclerViewClass);

        newFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()) {
                    Intent fileListIntent = new Intent(FileManagement.this, FileActivity.class);
                    String filePath = Environment.getExternalStorageDirectory().getPath();
                    fileListIntent.putExtra("filePath", filePath);
                    startActivity(fileListIntent);
                } else {
                    requestPermission();
                }
            }
        });
        setupBottomNavigationView();
    }

//    public static class StudentFile {
//        private String filename, filePath;
//        public StudentFile(String fileName, String filePath) {
//            this.filename = fileName;
//            this.filePath = filePath;
//        }
//
//        public String getFileName() {
//            return filename;
//        }
//
//        public void setFileName(String fileName) {
//            this.filename = fileName;
//        }
//
//        public String getFilePath() {
//            return filePath;
//        }
//
//        public void setFilePath(String filePath) {
//            this.filePath = filePath;
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Activity at onResumeState");
        setupBottomNavigationView();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(FileManagement.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(FileManagement.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(FileManagement.this, "Requires Storage Permission, Allow from settings", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(FileManagement.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
        }
    }

    private void setupBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            Intent dataFromUserBoard = getIntent();

            String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
            String userName = dataFromUserBoard.getStringExtra("username");
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_nav:
                        Intent homeIntent = new Intent(FileManagement.this, TempUserDashboard.class);
                        homeIntent.putExtra("username", userName);
                        homeIntent.putExtra("loginDetails", loginDetails);
                        startActivity(homeIntent);
                        return true;
                    case R.id.quiz_nav:
                        Intent quizIntent = new Intent(FileManagement.this, QuizActivity.class);
                        startActivity(quizIntent);
                        return true;
                    case R.id.file_nav:
                        Intent fileIntent = new Intent(FileManagement.this, FileManagement.class);
                        startActivity(fileIntent);
                        return true;
                    case R.id.account_nav:
                        return true;
                }
                return false;
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(FileManagement.this, UserDashboard.class);
//        intent.putExtra("loginDetails", getIntent().getSerializableExtra("loginDetails"));
//        intent.putExtra("username", getIntent().getStringExtra("username"));
//        startActivity(intent);
//    }
}