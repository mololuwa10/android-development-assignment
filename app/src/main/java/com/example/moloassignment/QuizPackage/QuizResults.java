package com.example.moloassignment.QuizPackage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.example.moloassignment.R;

import java.util.List;

public class QuizResults extends AppCompatActivity {
    AppCompatButton startNewQuiz, save_score_button;
    TextView correct_answers, incorrect_answers, total_score;
    private List<QuestionList> questionList;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        startNewQuiz = findViewById(R.id.start_new_quiz_button);
        save_score_button = findViewById(R.id.save_score_button);
        correct_answers = findViewById(R.id.correct_answers);
        incorrect_answers = findViewById(R.id.incorrect_answers);
        total_score = findViewById(R.id.total_score);

        int getCorrectAnswers = getIntent().getIntExtra("correct", 0);
        int getInCorrectAnswers = getIntent().getIntExtra("incorrect", 0);
        int getNoOfQuestions = getIntent().getIntExtra("questionList", 0);

        correct_answers.setText("Correct Answers: " + String.valueOf(getCorrectAnswers));
        incorrect_answers.setText("Incorrect Answers: " + String.valueOf(getInCorrectAnswers));

        total_score.setText("Total Scores: " + String.valueOf(getCorrectAnswers) + "/" + String.valueOf(getNoOfQuestions));
        
        startNewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizResults.this, MainQuizActivity.class));
                finish();
            }
        });
        save_score_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(QuizResults.this, MainQuizActivity.class));
        finish();
    }
}