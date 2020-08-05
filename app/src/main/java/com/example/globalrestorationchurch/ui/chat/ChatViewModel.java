package com.example.globalrestorationchurch.ui.chat;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatViewModel extends ViewModel{

    public ChatViewModel() {
        MutableLiveData<String> mText = new MutableLiveData<>();
        mText.setValue("This is chat fragment");
    }

}