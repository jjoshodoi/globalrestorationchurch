package com.example.globalrestorationchurch.ui.sermon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SermonViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SermonViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}