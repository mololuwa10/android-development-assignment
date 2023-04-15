package com.example.moloassignment.QuizPackage;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moloassignment.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity {
    ImageView backButton;
    TextView quizTimer, topicName;
    ImageView imageView;
    TextView questionNumber, Questions;
    RadioButton firstOption, secondOption, thirdOption, fourthOption;
    RadioGroup optionsGroup;
    AppCompatButton nextQuestionButton;
    Timer quiz_Timer;
    int timeInMins = 10;
    int seconds = 0;
    private List<QuestionList> questionList;
    int currentQuestionPosition = 0;
    String selectedOption = "";
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        backButton = findViewById(R.id.backButton);
        quizTimer = findViewById(R.id.quiz_timer);
        topicName = findViewById(R.id.quiz_topic_name);
        questionNumber = findViewById(R.id.questionNumber);
        Questions = findViewById(R.id.Questions);
        firstOption = findViewById(R.id.firstOption);
        secondOption = findViewById(R.id.secondOption);
        thirdOption = findViewById(R.id.thirdOption);
        fourthOption = findViewById(R.id.fourthOption);
        nextQuestionButton = findViewById(R.id.nextQuestionButton);
        optionsGroup = findViewById(R.id.answer_options_group);
        imageView = findViewById(R.id.questionImageView);

        final String getTopicName = getIntent().getStringExtra("topicName");

        topicName.setText(getTopicName);

        questionList = com.example.moloassignment.QuizPackage.Questions.getQuestions(getTopicName);
        startTimer(quizTimer);

        questionNumber.setText((currentQuestionPosition+1) + "/" + questionList.size());
        Questions.setText(questionList.get(0).getQuestion());

        firstOption.setText(questionList.get(0).getFirstOption());
        secondOption.setText(questionList.get(0).getSecondOption());
        thirdOption.setText(questionList.get(0).getThirdOption());
        fourthOption.setText(questionList.get(0).getFourthOption());

        optionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedOption = findViewById(checkedId);
                String selectedOptionText = selectedOption.getText().toString();

                questionList.get(currentQuestionPosition).setSelectedAnswer(selectedOptionText);
            }
        });

        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedOptionId = optionsGroup.getCheckedRadioButtonId();
                if (selectedOptionId != -1) {
                    String selectedOptionText = ((RadioButton) findViewById(selectedOptionId)).getText().toString();
                    questionList.get(currentQuestionPosition).setSelectedAnswer(selectedOptionText);
                    nextQuestion();
                } else {
                    Toast.makeText(QuizActivity.this, "Please Select an Option", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quiz_Timer.purge();
                quiz_Timer.cancel();

                startActivity(new Intent(QuizActivity.this, MainQuizActivity.class));
                finish();
            }
        });
    }
    private void nextQuestion() {
        currentQuestionPosition++;
        if(currentQuestionPosition == questionList.size() - 1) {
            nextQuestionButton.setText("Submit Quiz");
        }

        if(currentQuestionPosition < questionList.size()) {
            selectedOption = "";

            if(questionList.get(currentQuestionPosition).getImage() != 0) {
                imageView.setImageResource(questionList.get(currentQuestionPosition).getImage());
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.GONE);
            }

            firstOption.setBackgroundResource(R.drawable.white_border);
            firstOption.setTextColor(Color.parseColor("#1F6BB8"));

            secondOption.setBackgroundResource(R.drawable.white_border);
            secondOption.setTextColor(Color.parseColor("#1F6BB8"));

            thirdOption.setBackgroundResource(R.drawable.white_border);
            thirdOption.setTextColor(Color.parseColor("#1F6BB8"));

            fourthOption.setBackgroundResource(R.drawable.white_border);
            fourthOption.setTextColor(Color.parseColor("#1F6BB8"));

            questionNumber.setText((currentQuestionPosition+1) + "/" + questionList.size());
            Questions.setText(questionList.get(currentQuestionPosition).getQuestion());

            firstOption.setText(questionList.get(currentQuestionPosition).getFirstOption());
            secondOption.setText(questionList.get(currentQuestionPosition).getSecondOption());
            thirdOption.setText(questionList.get(currentQuestionPosition).getThirdOption());
            fourthOption.setText(questionList.get(currentQuestionPosition).getFourthOption());

        } else {
            Intent intent = new Intent(QuizActivity.this, QuizResults.class);
            intent.putExtra("correct", getCorrectAnswer());
            intent.putExtra("incorrect", getInCorrectAnswer());
            intent.putExtra("questionList", questionList.size());

            startActivity(intent);
            finish();
        }
    }
    private void startTimer(TextView timerTextView) {
        quiz_Timer = new Timer();
        quiz_Timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                 if(seconds == 0 && timeInMins == 0) {
                    quiz_Timer.purge();
                    quiz_Timer.cancel();

                    Intent timeIntent = new Intent(QuizActivity.this, QuizResults.class);
                    timeIntent.putExtra("correct", getCorrectAnswer());
                    timeIntent.putExtra("incorrect", getInCorrectAnswer());
                    startActivity(timeIntent);
                    finish();
                } else if(seconds == 0) {
                    timeInMins--;
                    seconds = 59;
                } else {
                    seconds--;
                }

                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        String finalMinutes = String.valueOf(timeInMins);
                        String finalSeconds = String.valueOf(seconds);

                        if(finalMinutes.length() == 1) {
                            finalMinutes = "0" + finalMinutes;
                        }

                        if(finalSeconds.length() == 1) {
                            finalSeconds = "0" + finalSeconds;
                        }
                        timerTextView.setText(finalMinutes + ":" + finalSeconds);
                    }
                });
            }
        }, 1000, 1000);
    }

    private int getCorrectAnswer() {
        int correctAnswers = 0;
        for(int i = 0; i < questionList.size(); i++) {
            String getSelectedAnswer = questionList.get(i).getSelectedAnswer();
            String getAnswer = questionList.get(i).getAnswer();

            if(getSelectedAnswer.equals(getAnswer)) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    private int getInCorrectAnswer() {
        int inCorrectAnswers = 0;
        for(int i = 0; i < questionList.size(); i++) {
            String getSelectedAnswer = questionList.get(i).getSelectedAnswer();
            String getAnswer = questionList.get(i).getAnswer();

            if(!getSelectedAnswer.equals(getAnswer)) {
                inCorrectAnswers++;
            }
        }
        return inCorrectAnswers;
    }

    @Override
    public void onBackPressed() {
        quiz_Timer.purge();
        quiz_Timer.cancel();

        startActivity(new Intent(QuizActivity.this, MainQuizActivity.class));
        finish();
    }
}