package com.moch.javaquiz.fragments;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.moch.javaquiz.R;

import java.io.IOException;
import java.io.InputStream;

public class LecturesFragment extends Fragment {

    private Spinner spinnerLectures;
    private Button buttonNext;
    private Button buttonPrevious;

    private String[] lectures;
    private String[] slides;
    private String path = "lectures/";
    private ImageView imageView;
    private AssetManager as;
    private InputStream is;

    private int slideNr = 0;
    private String lecture;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lectures, container, false);

        imageView = root.findViewById(R.id.slideView);
        buttonNext = root.findViewById(R.id.buttonNextSlide);
        buttonPrevious = root.findViewById(R.id.buttonPreviousSlide);
        spinnerLectures = root.findViewById(R.id.lecturesSpinner);
        as = getActivity().getAssets();

        try {
            lectures = getActivity().getAssets().list(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        spinnerLectures.setAdapter(
                new ArrayAdapter<>(getActivity().getBaseContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        lectures));
        spinnerLectures.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                loadLectureSlides(spinnerLectures.getSelectedItem().toString());
                changeLecture(spinnerLectures.getSelectedItem().toString());
                checkButtonNext();
                checkButtonPrevious();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextSlide();
                checkButtonNext();
                checkButtonPrevious();
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousSlide();
                checkButtonNext();
                checkButtonPrevious();
            }
        });

        return root;
    }

    private void changeLecture(String lecture) {
        try {
            slideNr = 0;
            this.lecture = lecture;
            is = as.open(path + lecture + "/" + slides[0]);
            Drawable d = Drawable.createFromStream(is, null);
            imageView.setImageDrawable(d);
        } catch (IOException ex) {
            return;
        }
    }

    private void loadLectureSlides(String lecture) {
        try {
            slides = getActivity().getAssets().list(path + lecture);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void nextSlide() {
        if (slideNr < slides.length - 1) {
            slideNr++;
            try {
                is = as.open(path + lecture + "/" + slides[slideNr]);
                Drawable d = Drawable.createFromStream(is, null);
                imageView.setImageDrawable(d);
            } catch (IOException ignored) {
            }
        }

    }

    private void previousSlide() {
        if (slideNr > 0) {
            slideNr--;
            try {
                is = as.open(path + lecture + "/" + slides[slideNr]);
                Drawable d = Drawable.createFromStream(is, null);
                imageView.setImageDrawable(d);
            } catch (IOException ignored) {
            }
        }

    }

    private void checkButtonNext() {
        if (slideNr == slides.length - 1) {
            buttonNext.setEnabled(false);
        } else {
            buttonNext.setEnabled(true);
        }
    }

    private void checkButtonPrevious() {
        if (slideNr == 0) {
            buttonPrevious.setEnabled(false);
        } else {
            buttonPrevious.setEnabled(true);
        }
    }
}