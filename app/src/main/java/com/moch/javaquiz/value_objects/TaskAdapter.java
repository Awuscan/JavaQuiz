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

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Task c = taskList.get(position);
        holder.textTask.setText(c.getTask());
        holder.textTask.setTextIsSelectable(false);
        holder.textTask.measure(-1, -1);//you can specific other values.
        holder.textTask.setTextIsSelectable(true);
        holder.imageView.setImageDrawable(c.getDrawable());
        holder.textCode.setText(c.getCode());
        holder.textCode.setTextIsSelectable(false);
        holder.textCode.measure(-1, -1);//you can specific other values.
        holder.textCode.setTextIsSelectable(true);
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


}