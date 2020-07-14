package com.example.globalrestorationchurch.ui.sermon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.globalrestorationchurch.R;


public class VideoFragment extends Fragment {

    SermonDetails details;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);


//        details = SermonDetails
//        int amount = savedInstanceState.fromBundle(getArguments()).getAmount();

        Log.e("tag", String.valueOf(details));

        return view;
    }
}