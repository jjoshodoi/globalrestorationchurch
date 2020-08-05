package com.example.globalrestorationchurch.ui.connect;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConnectViewModel extends ViewModel {

    public ConnectViewModel() {
        MutableLiveData<String> mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

}