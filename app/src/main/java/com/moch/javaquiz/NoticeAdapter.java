package com.moch.javaquiz;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolder> {

    private List<Notice> noticeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textDate;
        public TextView textTitle;
        public TextView textMessage;


        public MyViewHolder(View view) {
            super(view);
            textDate = view.findViewById(R.id.date);
            textTitle = view.findViewById(R.id.title);
            textMessage = view.findViewById(R.id.message);
        }
    }

    public NoticeAdapter(List<Notice> noticeList) {
        this.noticeList = noticeList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notice c = noticeList.get(position);
        holder.textDate.setText(c.getDate());
        holder.textTitle.setText(String.valueOf(c.getTitle()));
        holder.textMessage.setText(String.valueOf(c.getMessage()));
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notice, parent, false);
        return new MyViewHolder(v);
    }
}