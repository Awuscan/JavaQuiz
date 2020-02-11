package com.moch.javaquiz.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.moch.javaquiz.MainActivity;
import com.moch.javaquiz.R;
import com.moch.javaquiz.value_objects.Task;
import com.moch.javaquiz.value_objects.TaskAdapter;

import java.io.IOException;
import java.util.List;

public class LabsFragment extends Fragment {

    private Spinner spinnerLabs;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private RecyclerView recycleView;
    private List<Integer> labs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_labs, container, false);

        labs = MainActivity.dbHelper.getAllLabs();
        taskList = MainActivity.dbHelper.getLabTasks(1);

        recycleView = root.findViewById(R.id.recycleViewTask);

        adapter = new TaskAdapter(taskList);
        recycleView.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(llm);

        spinnerLabs = root.findViewById(R.id.labsSpinner);
        spinnerLabs.setAdapter(
                new ArrayAdapter<>(getActivity().getBaseContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        labs));
        spinnerLabs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                changeLab(spinnerLabs.getSelectedItemPosition() + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        return root;
    }

    private void changeLab(int labNumber) {
        taskList.clear();
        List<Task> tasks = MainActivity.dbHelper.getLabTasks(labNumber);
        for(Task task : tasks){
            task.setDrawable(  ((MainActivity) getActivity()).getDrawable(task.getImage()));
        }
        taskList.addAll(tasks);
        adapter.notifyDataSetChanged();
    }
}