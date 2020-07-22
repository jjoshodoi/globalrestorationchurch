package com.example.globalrestorationchurch.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalrestorationchurch.MainActivity;
import com.example.globalrestorationchurch.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

public class ChatFragment extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<ChatMessage, MyViewHolder> recyclerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        FloatingActionButton fab =
                view.findViewById(R.id.fab);

        recyclerView = view.findViewById(R.id.list_of_messages);

        fab.setOnClickListener(view1 -> {
            EditText input = view.findViewById(R.id.input);
            // Read the input field and push a new instance of ChatMessage to the Firebase database
            FirebaseDatabase.getInstance()
                    .getReference()
                    .push()
                    .setValue(new ChatMessage(input.getText().toString(),
                            Objects.requireNonNull(FirebaseAuth.getInstance()
                                    .getCurrentUser())
                                    .getDisplayName())
                    );

            // Clear the input
            input.setText("");
        });

        updateList();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }

    private void updateList() {
        Query query = FirebaseDatabase.getInstance().getReference();
        FirebaseRecyclerOptions<ChatMessage> optionsRecycler =
                new FirebaseRecyclerOptions.Builder<ChatMessage>()
                        .setQuery(query, ChatMessage.class)
                        .build();

//        FirebaseRecyclerAdapter<ChatMessage, MyViewHolder> finalRecyclerAdapter = recyclerAdapter;
        recyclerAdapter = new FirebaseRecyclerAdapter<ChatMessage, MyViewHolder>(optionsRecycler) {
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.text_bubble, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull ChatMessage model) {
                // Bind the Chat object to the ChatHolder
                // ...

                String user = model.getMessageUser();

                holder.user.setText(user);
                holder.text.setText(model.getMessageText());
                holder.time.setText(String.valueOf(model.getMessageTime()));

                if (user.equals(MainActivity.userName)) {

//                    ConstraintLayout mConstraintLayout  = (ConstraintLayout)holder.view;
//                    ConstraintSet set = new ConstraintSet();
//
//                    CardView cv = holder.view.findViewById(R.id.cardview);
//
//                    mConstraintLayout.removeView(cv);
//                    mConstraintLayout.addView(cv,0);
//                    set.clone(mConstraintLayout);
//                    set.connect(cv.getId(), ConstraintSet.END, mConstraintLayout.getId(), ConstraintSet.END, 0);
//                    set.connect(cv.getId(), ConstraintSet.BOTTOM, mConstraintLayout.getId(), ConstraintSet.BOTTOM, 0);
//                    set.connect(cv.getId(), ConstraintSet.TOP, mConstraintLayout.getId(), ConstraintSet.TOP, 0);
//                    set.applyTo(mConstraintLayout);
                }
            }

            @Override
            public void onDataChanged() {
                // Called each time there is a new data snapshot. You may want to use this method
                // to hide a loading spinner or check for the "no documents" state and update your UI.
                // ...

                recyclerView.scrollToPosition(recyclerAdapter.getItemCount() - 1);
            }

            @Override
            public void onError(DatabaseError e) {
                // Called when there is an error getting data. You may want to update
                // your UI to display an error message to the user.
                // ...
            }

        };
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        ChatViewModel mViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        // TODO: Use the ViewModel
    }

}

class MyViewHolder extends RecyclerView.ViewHolder {
    public final View view;
    public final TextView text;
    public final TextView user;
    public final TextView time;

    public MyViewHolder(View v) {
        super(v);
        this.view = v;
        this.user = v.findViewById(R.id.message_user);
        this.text = v.findViewById(R.id.message_text);
        this.time = v.findViewById(R.id.message_time);
    }
}