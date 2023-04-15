package com.example.moloassignment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    String [][] loginDetails = {
            {"Mololuwa", "BleakMolo10"},
            {"Zel", "bleakZel"},
            {"Kolade", "bleakKolade"},
            {"Izu", "batman"},
            {"Michael", "opoku"},
            {"Ese", "addo"},
            {"King", "adebanjo"},
            {"Esther", "adegoke"},
            {"Kenneth", "odion"},
            {"Deborah", "ibijola"}
    };
    EditText edtUserName, edtPassword;
    CheckBox showPasswordCheckbox;
    Button btnLogin;
    private static final String TAG = "MainActivity";

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
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Activity at OnCreateState");

        edtUserName = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        showPasswordCheckbox = findViewById(R.id.showPasswordCheckbox);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUsername = edtUserName.getText().toString().trim();
                String strPassword = edtPassword.getText().toString().trim();
                boolean found;

                for (int i = 0; i < loginDetails.length; i++) {
                    String[] details = loginDetails[i];
                    String userName = details[0];
                    String password = details[1];

                    if(strUsername.isEmpty() || strPassword.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                    } else {
                        if(strUsername.equals(userName) && strPassword.equals(password)) {
                            found = true;
                            Toast.makeText(MainActivity.this, "Welcome " + loginDetails[i][0], Toast.LENGTH_SHORT).show();

                            Intent userDashboardIntent = new Intent(MainActivity.this, TempUserDashboard.class);
                            userDashboardIntent.putExtra("loginDetails", loginDetails);
                            userDashboardIntent.putExtra("username", userName);
                            startActivity(userDashboardIntent);
                            break;
                        } else if(i == loginDetails.length - 1) {
                            found = false;
                            Toast.makeText(MainActivity.this, "Wrong Username/Password combination", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        showPasswordCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }
}