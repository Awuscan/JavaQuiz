package com.moch.javaquiz.ui.lectures;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class LecturesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LecturesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is lectures fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}