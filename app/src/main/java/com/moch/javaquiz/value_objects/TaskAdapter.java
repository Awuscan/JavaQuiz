package com.moch.javaquiz.value_objects;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moch.javaquiz.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private List<Task> taskList;

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Task c = taskList.get(position);
        holder.textTask.setText(c.getTask());
        //holder.imageView.setImageResource(c.getImage());
        holder.textCode.setText(c.getCode());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task, parent, false);
        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textTask;
        public ImageView imageView;
        public TextView textCode;


        public MyViewHolder(View view) {
            super(view);
            textTask = view.findViewById(R.id.task);
            imageView = view.findViewById(R.id.image);
            textCode = view.findViewById(R.id.code);
        }
    }
}