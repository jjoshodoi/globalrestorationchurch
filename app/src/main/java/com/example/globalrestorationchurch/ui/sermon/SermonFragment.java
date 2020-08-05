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

public class SermonFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SermonViewModel sermonViewModel = new
                ViewModelProvider(this).get(SermonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sermon, container, false);

        final RecyclerView recyclerView;
        recyclerView = root.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // set the recycler views adapter from the view model
        sermonViewModel.getUsers().observe(getViewLifecycleOwner(), users -> recyclerView.setAdapter(new MyAdapter(users, this)));

        return root;
    }
}
