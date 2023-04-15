package com.example.moloassignment.OtherPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moloassignment.R;

import java.util.Stack;

public class CalculatorActivity extends AppCompatActivity {

    private EditText edtText;
    private Stack<Double> numberStack;
    private String operator;
    private static final String TAG = "CalculatorActivity";

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

    ImageView backButtonCalculator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Log.d(TAG, "Activity at onCreateState");

        Button[] buttons = new Button[17];
        TextView edtText;
        int[] buttonIds = {R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5,
                R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9, R.id.button_add, R.id.button_clear,
                R.id.button_divide, R.id.button_dot, R.id.button_equal, R.id.button_multiply, R.id.button_subtract};
        numberStack = new Stack<>();

        edtText = findViewById(R.id.textView);
        backButtonCalculator = findViewById(R.id.backButtonCalculator);

        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String username = getIntent().getStringExtra("username");

        backButtonCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(CalculatorActivity.this, OtherActivity.class);
                otherActivity.putExtra("username", username);
                otherActivity.putExtra("loginDetails", loginDetails);
                startActivity(otherActivity);
                finish();
            }
        });
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = findViewById(buttonIds[i]);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    String buttonText = ((Button) v).getText().toString();

                    if (buttonText.matches("[0-9]")) {
                        edtText.append(buttonText);
                    } else if (buttonText.equals(".")) {
                        if (!edtText.getText().toString().contains(".")) {
                            edtText.append(".");
                        }
                    } else if (buttonText.equals("+/-")) {
                        if (edtText.getText().toString().startsWith("-")) {
                            edtText.setText(edtText.getText().toString().substring(1));
                        } else {
                            edtText.setText("-" + edtText.getText().toString());
                        }
                    } else if (buttonText.equals("C")) {
                        edtText.setText("");
                    } else if (buttonText.equals("=")) {
                        if (!edtText.getText().toString().isEmpty()) {
                            double number = Double.parseDouble(edtText.getText().toString());
                            numberStack.push(number);
                        }

                        if (!numberStack.isEmpty()) {
                            double result = 0;
                            switch (operator) {
                                case "+":
                                    for (double num : numberStack) {
                                        result += num;
                                    }
                                    break;
                                case "-":
                                    result = numberStack.pop();
                                    for (double num : numberStack) {
                                        result -= num;
                                    }
                                    break;
                                case "*":
                                    result = 1;
                                    for (double num : numberStack) {
                                        result *= num;
                                    }
                                    break;
                                case "/":
                                    if (numberStack.size() < 2) {
                                        Toast.makeText(CalculatorActivity.this, "Not enough operands for division", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                    double divisor = numberStack.pop();
                                    double dividend = numberStack.pop();
                                    if (divisor != 0) {
                                        result = dividend / divisor;
                                        numberStack.push(result);
                                    } else {
                                        Toast.makeText(CalculatorActivity.this, "Can't Divide By Zero", Toast.LENGTH_SHORT).show();
                                        numberStack.push(dividend);
                                        numberStack.push(divisor);
                                        result = 0;
                                    }
                                    break;
                                default:
                                    result = 0;
                                    break;
                            }
                            edtText.setText(Double.toString(result));
                            numberStack.clear();
                        }
                    } else if (buttonText.matches("[+\\-*/]")) {
                        if (!edtText.getText().toString().isEmpty()) {
                            double number = Double.parseDouble(edtText.getText().toString());
                            numberStack.push(number);
                        }
                        operator = buttonText;
                        edtText.setText("");
                    }
                }
            });
        }
    }
}