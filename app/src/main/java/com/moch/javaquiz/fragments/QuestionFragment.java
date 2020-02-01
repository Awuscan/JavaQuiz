package com.moch.javaquiz.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
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
        rb1 = root.findViewById(R.id.radio_button1);
        rb2 = root.findViewById(R.id.radio_button2);
        rb3 = root.findViewById(R.id.radio_button3);
        rb4 = root.findViewById(R.id.radio_button4);
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
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
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
        rbGroup.clearCheck();

        rb1.setEnabled(true);
        rb2.setEnabled(true);
        rb3.setEnabled(true);
        rb4.setEnabled(true);

        rb1.setTextColor(getResources().getColor(R.color.colorText));
        rb2.setTextColor(getResources().getColor(R.color.colorText));
        rb3.setTextColor(getResources().getColor(R.color.colorText));
        rb4.setTextColor(getResources().getColor(R.color.colorText));

        if (questionCounter < questionTotal) {
            currentQuestion = questionList.get(questionCounter);

            textQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

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

        rb1.setEnabled(false);
        rb2.setEnabled(false);
        rb3.setEnabled(false);
        rb4.setEnabled(false);

        RadioButton rbChecked= root.findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbChecked) + 1;

        if (answerNr == currentQuestion.getAnswer()) {
            result++;
            String string = getResources().getText(R.string.result).toString() + result + "/" + questionTotal;
            textResult.setText(string);
        }

        rb1.setTextColor(getResources().getColor(R.color.colorTextWrong));
        rb2.setTextColor(getResources().getColor(R.color.colorTextWrong));
        rb3.setTextColor(getResources().getColor(R.color.colorTextWrong));
        rb4.setTextColor(getResources().getColor(R.color.colorTextWrong));

        switch (currentQuestion.getAnswer()) {
            case 1:
                rb1.setTextColor(getResources().getColor(R.color.colorTextRight));
                break;
            case 2:
                rb2.setTextColor(getResources().getColor(R.color.colorTextRight));
                break;
            case 3:
                rb3.setTextColor(getResources().getColor(R.color.colorTextRight));
                break;
            case 4:
                rb4.setTextColor(getResources().getColor(R.color.colorTextRight));
                break;
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
