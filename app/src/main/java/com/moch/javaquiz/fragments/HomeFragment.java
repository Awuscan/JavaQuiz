package com.moch.javaquiz.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moch.javaquiz.MainActivity;
import com.moch.javaquiz.R;
import com.moch.javaquiz.value_objects.Notice;
import com.moch.javaquiz.value_objects.NoticeAdapter;

import java.util.List;

public class HomeFragment extends Fragment {

    private NoticeAdapter adapter;
    private List<Notice> noticeList;
    private RecyclerView recycleView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final SwipeRefreshLayout swipe = root.findViewById(R.id.swipeContainer);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipe.setRefreshing(false);
            }
        });

        recycleView = root.findViewById(R.id.recycleViewNotice);

        noticeList = MainActivity.dbHelper.getAllNotices();

        adapter = new NoticeAdapter(noticeList);
        recycleView.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(llm);

        return root;
    }

    private void refresh() {
        ((MainActivity) getActivity()).getNewData();
        noticeList.clear();
        noticeList.addAll(((MainActivity) getActivity()).dbHelper.getAllNotices());
        adapter.notifyDataSetChanged();
    }


}