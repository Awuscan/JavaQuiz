package com.moch.javaquiz.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.moch.javaquiz.MainActivity;
import com.moch.javaquiz.Question;
import com.moch.javaquiz.R;

import java.util.List;


public class QuestionFragment extends Fragment {

    private View root;

    private TextView textResult;
    private TextView textCount;
    private TextView textQuestion;
    private RadioGroup rbGroup;
    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private Button buttonNext;

    private List<Question> questionList;
    private int questionTotal;
    private int questionCounter;
    private Question currentQuestion;

    private int result;
    private boolean answered;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_question, container, false);

        textResult = root.findViewById(R.id.text_score);
        textCount = root.findViewById(R.id.text_count);
        textQuestion = root.findViewById(R.id.text_question);
        rbGroup = root.findViewById(R.id.radio_group);
        cb1 = root.findViewById(R.id.checkBox1);
        cb2 = root.findViewById(R.id.checkBox2);
        cb3 = root.findViewById(R.id.checkBox3);
        cb4 = root.findViewById(R.id.checkBox4);
        buttonNext = root.findViewById(R.id.button_next);

        questionList = ((MainActivity)getActivity()).dbHelper.getAllQuestions();
        questionTotal = questionList.size();
        questionCounter = 0;

        showNextQuestion();
        String string = getResources().getText(R.string.result).toString() + result + "/" + questionTotal;
        textResult.setText(string);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (cb1.isChecked() || cb2.isChecked() || cb3.isChecked() || cb4.isChecked()) {
                        checkAnswer();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });

        this.root = root;
        return root;
    }

    private void showNextQuestion() {
        cb1.setChecked(false);
        cb2.setChecked(false);
        cb3.setChecked(false);
        cb4.setChecked(false);

        cb1.setEnabled(true);
        cb2.setEnabled(true);
        cb3.setEnabled(true);
        cb4.setEnabled(true);

        cb1.setTextColor(getResources().getColor(R.color.colorText));
        cb2.setTextColor(getResources().getColor(R.color.colorText));
        cb3.setTextColor(getResources().getColor(R.color.colorText));
        cb4.setTextColor(getResources().getColor(R.color.colorText));

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

            answered = false;

            buttonNext.setText(getResources().getText(R.string.check_answer).toString());
        } else {
            finishQuiz();
        }
    }

    private void checkAnswer() {
        answered = true;

        cb1.setEnabled(false);
        cb2.setEnabled(false);
        cb3.setEnabled(false);
        cb4.setEnabled(false);

        if (    currentQuestion.isAnswer1() == cb1.isChecked() &&
                currentQuestion.isAnswer2() == cb2.isChecked() &&
                currentQuestion.isAnswer3() == cb3.isChecked() &&
                currentQuestion.isAnswer4() == cb4.isChecked() ){
            result++;
            String string = getResources().getText(R.string.result).toString() + result + "/" + questionTotal;
            textResult.setText(string);
        }

        if (currentQuestion.isAnswer1()){
            cb1.setTextColor(getResources().getColor(R.color.colorTextRight));
        }else{
            cb1.setTextColor(getResources().getColor(R.color.colorTextWrong));
        }

        if (currentQuestion.isAnswer2()){
            cb2.setTextColor(getResources().getColor(R.color.colorTextRight));
        }else{
            cb2.setTextColor(getResources().getColor(R.color.colorTextWrong));
        }

        if (currentQuestion.isAnswer3()){
            cb3.setTextColor(getResources().getColor(R.color.colorTextRight));
        }else{
            cb3.setTextColor(getResources().getColor(R.color.colorTextWrong));
        }

        if (currentQuestion.isAnswer4()){
            cb4.setTextColor(getResources().getColor(R.color.colorTextRight));
        }else{
            cb4.setTextColor(getResources().getColor(R.color.colorTextWrong));
        }


        if (questionCounter < questionTotal) {
            buttonNext.setText(getResources().getText(R.string.next_question).toString());
        } else {
            buttonNext.setText(getResources().getText(R.string.finish).toString());
        }
    }

    private void finishQuiz() {
        Fragment newFragment = new QuizFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.commit();
    }
}
