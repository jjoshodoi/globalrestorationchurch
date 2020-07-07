package com.example.globalrestorationchurch.ui.sermon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalrestorationchurch.R;

import java.util.ArrayList;

public class SermonFragment extends Fragment {

    //    private static final String API_KEY = "AIzaSyAkWwsGfbqMay48tO1xSWHEss9YQsW5f_o";
//    private static final String PLAYLISTVIDEOSTRING = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet%2C+id&playlistId=PL21rEW-r8cqeumA2WciZ77zUVEycwK-C7&key=AIzaSyAkWwsGfbqMay48tO1xSWHEss9YQsW5f_o&maxResults=20";
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SermonViewModel sermonViewModel = new
                ViewModelProvider(this).get(SermonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sermon, container, false);

        setUpRecycler(root);
        // set the recycler views adapter from the view model
        sermonViewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            setRecyclerAdapter(new ArrayList<>(users));
        });

        return root;
    }

    private void setUpRecycler(View root) {
        recyclerView = root.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setRecyclerAdapter(ArrayList<SermonDetails> details) {
        // specify an adapter (see also next example)
        recyclerView.setAdapter(new MyAdapter(details));
    }
}
