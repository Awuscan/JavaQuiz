package com.moch.javaquiz.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.moch.javaquiz.MainActivity;
import com.moch.javaquiz.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class QuizFragment extends Fragment {

    private Spinner spinnerCategory;
    private Spinner spinnerCount;
    private List<String> categoryList;

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

        categoryList = MainActivity.dbHelper.getAllCategories();

        spinnerCategory = root.findViewById(R.id.spinnerCategory);

        spinnerCount = root.findViewById(R.id.spinnerCount);

        spinnerCategory.setAdapter(
                new ArrayAdapter<>(getActivity().getBaseContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        //MainActivity.dbHelper.getAllCategoriesAndCount()));
                        categoryList));



        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                String category = categoryList.get(spinnerCategory.getSelectedItemPosition());
                List<Integer> countList = new ArrayList<>();
                for(int i = 1; i <= MainActivity.dbHelper.getCategorySize(category); i++){
                    countList.add(i);
                }
                spinnerCount.setAdapter(
                        new ArrayAdapter<>(getActivity().getBaseContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                countList));
                spinnerCount.setSelection(countList.size()/2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                spinnerCategory.setSelection(0);
            }

        });

        spinnerCategory.setSelection(0);


        return root;
    }

    private void startQuiz() {
        Fragment newFragment = new QuestionFragment();

        Bundle args = new Bundle();
        args.putString("category", categoryList.get(spinnerCategory.getSelectedItemPosition()));
        args.putInt("count", spinnerCount.getSelectedItemPosition() + 1);
        newFragment.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.remove(this);
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.commit();
    }
}