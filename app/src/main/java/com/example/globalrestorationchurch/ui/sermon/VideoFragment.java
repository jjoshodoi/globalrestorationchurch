package com.example.globalrestorationchurch.ui.sermon;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.example.globalrestorationchurch.R;

import java.net.URISyntaxException;


public class VideoFragment extends Fragment {

    SermonDetails details;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            details = getArguments().getParcelable(SermonDetails.KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        VideoView video = view.findViewById(R.id.video_view);
        video.setVideoPath(details.getPath());

        return view;
    }
}