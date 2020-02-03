package com.moch.javaquiz.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.moch.javaquiz.MainActivity;
import com.moch.javaquiz.Notice;
import com.moch.javaquiz.NoticeAdapter;
import com.moch.javaquiz.R;

import java.util.List;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView rv = root.findViewById(R.id.recycleViewNotice);

        List<Notice> noticeList = ((MainActivity)getActivity()).dbHelper.getAllNotices();

        NoticeAdapter na = new NoticeAdapter(noticeList);
        rv.setAdapter(na);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        return root;
    }
}