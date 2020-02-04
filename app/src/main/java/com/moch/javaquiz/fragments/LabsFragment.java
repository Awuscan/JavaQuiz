package com.moch.javaquiz.fragments;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.barteksc.pdfviewer.PDFView;
import com.moch.javaquiz.R;

import java.io.IOException;
import java.io.InputStream;

public class LabsFragment extends Fragment {

    private Spinner spinnerLabs;
    private InputStream is = null;
    private String files[];
    private String path = "labs/";
    ;
    private AssetManager as;
    private PDFView viewPDF;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_labs, container, false);

        viewPDF = root.findViewById(R.id.pdfView);
        as = getActivity().getAssets();

        try {
            files = getActivity().getAssets().list(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        spinnerLabs = root.findViewById(R.id.labsSpinner);
        spinnerLabs.setAdapter(
                new ArrayAdapter<>(getActivity().getBaseContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        files));
        spinnerLabs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                changePDF(spinnerLabs.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        return root;
    }

    private void changePDF(String file) {
        try {
            is = as.open(path + file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        viewPDF.fromStream(is).load();
    }
}