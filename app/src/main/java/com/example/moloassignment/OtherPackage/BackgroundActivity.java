package com.example.moloassignment.OtherPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moloassignment.R;

import java.util.HashMap;
import java.util.Map;

public class BackgroundActivity extends AppCompatActivity {
    private static final String TAG = "BackgroundActivity";
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
    ImageView backButtonBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);

        Log.d(TAG, "Activity at onCreateState");

        Button submitButton = findViewById(R.id.button);
        EditText userColorChoice = findViewById(R.id.userTextColour);
        TextView textView = findViewById(R.id.textView);
        LinearLayout layout = findViewById(R.id.background_xml);
        backButtonBackground = findViewById(R.id.backButtonBackground);

        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String username = getIntent().getStringExtra("username");

        backButtonBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(BackgroundActivity.this, OtherActivity.class);
                otherActivity.putExtra("username", username);
                otherActivity.putExtra("loginDetails", loginDetails);
                startActivity(otherActivity);
                finish();
            }
        });

        Map<String, Integer> colorMap = new HashMap<String, Integer>() {{
            put("black", Color.parseColor("#000000"));
            put("white", Color.parseColor("#FFFFFF"));
            put("red", Color.parseColor("#FF0000"));
            put("green", Color.parseColor("#00FF00"));
            put("blue", Color.parseColor("#0000FF"));
            put("yellow", Color.parseColor("#FFFF00"));
            put("cyan", Color.parseColor("#00FFFF"));
            put("magenta", Color.parseColor("#FF00FF"));
            put("gray", Color.parseColor("#808080"));
            put("lightgray", Color.parseColor("#D3D3D3"));
            put("darkgray", Color.parseColor("#A9A9A9"));
            put("purple", Color.parseColor("#800080"));
            put("pink", Color.parseColor("#FFC0CB"));
        }};

        submitButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceType", "SetTextI18n"})
            @Override
            public void onClick(View v) {
                String userInput = userColorChoice.getText().toString().trim();
                textView.setText("Selected Colour: " + userInput);
                try{
                    int color = Color.parseColor(userInput);
                    layout.setBackgroundColor(color);
                } catch (IllegalArgumentException e) {
                    Integer color = colorMap.get(userInput.toLowerCase());

                    if(color != null) {
                        layout.setBackgroundColor(color);
                    } else {
                        Toast.makeText(BackgroundActivity.this, "Color is not set, Choose a different Color", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}