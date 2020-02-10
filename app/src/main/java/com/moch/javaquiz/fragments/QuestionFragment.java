package com.moch.javaquiz.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.moch.javaquiz.MainActivity;
import com.moch.javaquiz.value_objects.Question;
import com.moch.javaquiz.R;

import java.util.Arrays;
import java.util.List;


public class QuestionFragment extends Fragment {


    private TextView textResult;
    private TextView textCount;
    private TextView textQuestion;
    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private Button buttonNext;

    private List<Question> questionList;
    private int questionTotal;
    private int questionCounter;
    private Question currentQuestion;

    private int[][] answers;
    private int result;
    private boolean finished;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_question, container, false);

        Bundle bundle = this.getArguments();
        String category = null;
        if (bundle != null) {
            category = bundle.getString("category", null);
        } else {
            finishQuiz();
        }

        textResult = root.findViewById(R.id.text_score);
        textCount = root.findViewById(R.id.text_count);
        textQuestion = root.findViewById(R.id.text_question);
        cb1 = root.findViewById(R.id.checkBox1);
        cb2 = root.findViewById(R.id.checkBox2);
        cb3 = root.findViewById(R.id.checkBox3);
        cb4 = root.findViewById(R.id.checkBox4);
        buttonNext = root.findViewById(R.id.button_next);

        questionList = MainActivity.dbHelper.getCategoryQuestions(category);
        questionTotal = questionList.size();

        if (questionTotal == 0) {
            finishQuiz();
        }

        questionCounter = 0;

        answers = new int[questionTotal][4];

        for (int[] row: answers)
            Arrays.fill(row, 0);

        finished = false;

        showNextQuestion();


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!finished){
                    if (cb1.isChecked() || cb2.isChecked() || cb3.isChecked() || cb4.isChecked()) {
                        checkAnswer();
                        showNextQuestion();
                    }
                }else{
                    viewNextAnswer();
                }

            }
        });

        root.findViewById(R.id.button_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishQuiz();
            }
        });
        return root;
    }

    private void showNextQuestion() {
        cb1.setChecked(false);
        cb2.setChecked(false);
        cb3.setChecked(false);
        cb4.setChecked(false);

        if (questionCounter < questionTotal) {
            currentQuestion = questionList.get(questionCounter);

            textQuestion.setText(currentQuestion.getQuestion());
            cb1.setText(currentQuestion.getOption1());
            cb2.setText(currentQuestion.getOption2());
            cb3.setText(currentQuestion.getOption3());
            cb4.setText(currentQuestion.getOption4());

            questionCounter++;

            String string = getResources().getText(R.string.question).toString() + questionCounter + "/" + questionTotal;
            textCount.setText(string);
        } else {
            finished = true;
            cb1.setEnabled(false);
            cb2.setEnabled(false);
            cb3.setEnabled(false);
            cb4.setEnabled(false);
            questionCounter = 0;
            textResult.setVisibility(TextView.VISIBLE);
            textResult.setText(getResources().getText(R.string.result).toString() + result + "/" + questionTotal);
            buttonNext.setText(getResources().getText(R.string.next_answer).toString());
            viewNextAnswer();
        }
    }

    private void checkAnswer() {

        if (currentQuestion.isAnswer1() == cb1.isChecked() &&
                currentQuestion.isAnswer2() == cb2.isChecked() &&
                currentQuestion.isAnswer3() == cb3.isChecked() &&
                currentQuestion.isAnswer4() == cb4.isChecked()) {
            result++;

        }

        if (cb1.isChecked()) {
            answers[questionCounter-1][0] = 1;
        }
        if (cb2.isChecked()) {
            answers[questionCounter-1][1] = 1;
        }
        if (cb3.isChecked()) {
            answers[questionCounter-1][2] = 1;
        }
        if (cb4.isChecked()) {
            answers[questionCounter-1][3] = 1;
        }


        if (questionCounter < questionTotal-1) {
            buttonNext.setText(getResources().getText(R.string.next_question).toString());
        } else {
            buttonNext.setText(getResources().getText(R.string.finish).toString());
        }
    }

    private void viewNextAnswer(){

        if (questionCounter < questionTotal) {
            currentQuestion = questionList.get(questionCounter);

            textQuestion.setText(currentQuestion.getQuestion());
            cb1.setText(currentQuestion.getOption1());
            cb2.setText(currentQuestion.getOption2());
            cb3.setText(currentQuestion.getOption3());
            cb4.setText(currentQuestion.getOption4());

            if (currentQuestion.isAnswer1()) {
                cb1.setTextColor(getResources().getColor(R.color.colorTextRight));
            } else {
                cb1.setTextColor(getResources().getColor(R.color.colorTextWrong));
            }

            if (currentQuestion.isAnswer2()) {
                cb2.setTextColor(getResources().getColor(R.color.colorTextRight));
            } else {
                cb2.setTextColor(getResources().getColor(R.color.colorTextWrong));
            }

            if (currentQuestion.isAnswer3()) {
                cb3.setTextColor(getResources().getColor(R.color.colorTextRight));
            } else {
                cb3.setTextColor(getResources().getColor(R.color.colorTextWrong));
            }

            if (currentQuestion.isAnswer4()) {
                cb4.setTextColor(getResources().getColor(R.color.colorTextRight));
            } else {
                cb4.setTextColor(getResources().getColor(R.color.colorTextWrong));
            }

            if (answers[questionCounter][0] == 1){
                cb1.setChecked(true);
            }else{
                cb1.setChecked(false);
            }
            if (answers[questionCounter][1] == 1){
                cb2.setChecked(true);
            }else{
                cb2.setChecked(false);
            }
            if (answers[questionCounter][2] == 1){
                cb3.setChecked(true);
            }else{
                cb3.setChecked(false);
            }
            if (answers[questionCounter][3] == 1){
                cb4.setChecked(true);
            }else{
                cb4.setChecked(false);
            }

            questionCounter++;

            String string = getResources().getText(R.string.question).toString() + questionCounter + "/" + questionTotal;
            textCount.setText(string);
            if(questionCounter == questionTotal){
                buttonNext.setText(getResources().getText(R.string.finish).toString());
            }
        } else {
            finishQuiz();
        }
    }

    private void finishQuiz() {
        Fragment newFragment = new QuizFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.remove(this);
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.commit();
    }
}
