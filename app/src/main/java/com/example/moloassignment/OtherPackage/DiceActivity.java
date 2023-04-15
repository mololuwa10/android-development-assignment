package com.example.moloassignment.OtherPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moloassignment.R;

public class DiceActivity extends AppCompatActivity {
    ImageView backButtonDice;
    private static final String TAG = "DiceActivity";
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
        setContentView(R.layout.activity_dice);

        backButtonDice = findViewById(R.id.backButtonDice);

        ImageView rolledDice1 = findViewById(R.id.rolledDice1);
        Button diceButtonRoll1 = findViewById(R.id.diceButtonRoll1);
        rollDice(diceButtonRoll1, rolledDice1, 1);

        ImageView rolledDice2 = findViewById(R.id.rolledDice2);
        Button diceButtonRoll2 = findViewById(R.id.diceButtonRoll2);
        rollDice(diceButtonRoll2, rolledDice2, 2);

        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String username = getIntent().getStringExtra("username");

        backButtonDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(DiceActivity.this, OtherActivity.class);
                otherActivity.putExtra("username", username);
                otherActivity.putExtra("loginDetails", loginDetails);
                startActivity(otherActivity);
                finish();
            }
        });
    }

    protected void rollDice(Button button, ImageView rolledDice, int index) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomNumber = (int) (Math.random() * 6) + 1;
                int randomNumber2 = (int) (Math.random() * 6) + 1;
                rolledDice.setVisibility(View.INVISIBLE);

                ImageView rolledDice1 = findViewById(R.id.rolledDice1);
                ImageView rolledDice2 = findViewById(R.id.rolledDice2);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        @SuppressLint("DiscouragedApi")
                        int diceImageResource = getResources().getIdentifier("dice" + randomNumber, "drawable", getPackageName());
                        int diceImageResource2 = getResources().getIdentifier("dice" + randomNumber2, "drawable", getPackageName());

                        if (index == 1) {
                            rolledDice1.setImageResource(diceImageResource);
                        } else {
                            rolledDice2.setImageResource(diceImageResource2);
                        }
                        rolledDice.setVisibility(View.VISIBLE);

                        if(randomNumber > randomNumber2) {
                            Toast.makeText(DiceActivity.this, "Dice 1 has the Highest Number", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DiceActivity.this, "Dice 2 has the Highest Number", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 500);
            }
        });
    }
}