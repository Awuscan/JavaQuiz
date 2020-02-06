package com.moch.javaquiz.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Spinner;

import com.moch.javaquiz.MainActivity;
import com.moch.javaquiz.R;

public class QuizFragment extends Fragment {

    private Spinner spinnerCategory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_quiz, container, false);

        final Button buttonStartQuiz = root.findViewById(R.id.button_start_quiz);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        spinnerCategory = root.findViewById(R.id.spinnerCategory);

        getCategories();

        Button buttonUpdate = root.findViewById(R.id.buttonUpdate);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }

    private void startQuiz() {
        Fragment newFragment = new QuestionFragment();

        Bundle args = new Bundle();
        args.putString("category", spinnerCategory.getSelectedItem().toString());
        newFragment.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.remove(this);
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.commit();
    }

    private void getCategories(){
        spinnerCategory.setAdapter(
                new ArrayAdapter<>(getActivity().getBaseContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        ((MainActivity) getActivity()).dbHelper.getAllCategories()));

        spinnerCategory.setSelection(0);
    }
}