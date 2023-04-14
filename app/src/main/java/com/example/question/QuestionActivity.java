package com.example.question;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.question.databinding.ActivityQuestionBinding;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    private List<Question> questionList = new ArrayList<>();
    private int current = 1;
    private int score = 0;
    private String currentAnswer = "";
    private ActivityQuestionBinding binding;
    private List<TextView> answerViewList = new ArrayList<>();
    String name;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        name = getIntent().getStringExtra("name");
        binding.tvName.append(name + "!");
        answerViewList.add(binding.tvAnswer1);
        answerViewList.add(binding.tvAnswer2);
        answerViewList.add(binding.tvAnswer3);
        questionList.add(new Question(
                "Question1 Title",
                "Question1 Content Question1 Content Question1 Content",
                "Answer1",
                "Answer2",
                "Answer3",
                "Answer1"
        ));
        questionList.add(new Question(
                "Question2 Title",
                "Question2 Content Question2 Content Question2 Content",
                "Answer1",
                "Answer2",
                "Answer3",
                "Answer2"
        ));
        questionList.add(new Question(
                "Question3 Title",
                "Question3 Content Question3 Content Question3 Content",
                "Answer1",
                "Answer2",
                "Answer3",
                "Answer3"
        ));
        nextQuestion();
    }

    @SuppressLint("SetTextI18n")
    private void nextQuestion() {
        for (TextView textView : answerViewList) {
            textView.setEnabled(true);
        }
        binding.tvStep.setText(current + "/" + questionList.size());
        binding.progress.setMax(questionList.size());
        binding.progress.setProgress(current);
        Question question = questionList.get(current - 1);
        binding.tvAnswer1.setText(question.getAnswer1());
        binding.tvAnswer2.setText(question.getAnswer2());
        binding.tvAnswer3.setText(question.getAnswer3());
        binding.tvSubmit.setText("Submit");
        View.OnClickListener answerClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                currentAnswer = tv.getText().toString();
            }
        };
        for (TextView textView : answerViewList) {
            textView.setOnClickListener(answerClick);
        }
        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.tvSubmit.getText().equals("Next")) {
                    if (current - 1 == questionList.size()) {
                        Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
                        intent.putExtra("score", score + "/" + questionList.size());
                        intent.putExtra("name", name);
                        startActivity(intent);
                        finish();
                        return;
                    }
                    nextQuestion();
                    for (TextView textView : answerViewList) {
                        textView.setEnabled(true);
                        textView.setBackgroundResource(R.drawable.shape_answer);
                    }
                    return;
                }
                current++;
                if (question.isRight(currentAnswer)) {
                    for (TextView textView : answerViewList) {
                        if (textView.getText().toString().equals(currentAnswer)) {
                            textView.setBackgroundResource(R.drawable.shape_btn_green);
                            score++;
                        }
                    }
                } else {
                    for (TextView textView : answerViewList) {
                        if (textView.getText().toString().equals(currentAnswer)) {
                            textView.setBackgroundResource(R.drawable.shape_btn_red);
                        }
                    }
                    for (TextView textView : answerViewList) {
                        if (question.isRight(textView.getText().toString())) {
                            textView.setBackgroundResource(R.drawable.shape_btn_green);
                        }
                    }
                }
                for (TextView textView : answerViewList) {
                    textView.setEnabled(false);
                }
                binding.tvSubmit.setText("Next");

            }
        });
    }
}