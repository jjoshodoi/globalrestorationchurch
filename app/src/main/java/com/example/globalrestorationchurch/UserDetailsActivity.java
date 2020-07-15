package com.example.globalrestorationchurch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class UserDetailsActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener {

    public static final String SIGNOUT_RESULTS = "SIGNOUTRESULTS";
    public static final String SIGNOUT = "signout?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        FirebaseUser user = getIntent().getParcelableExtra(LoginActivity.FIREBASE_USER_KEY);

        TextView profileName = findViewById(R.id.profile_text);
        profileName.setText(user.getDisplayName());
        TextView profileEmail = findViewById(R.id.profile_email);
        profileEmail.setText(user.getEmail());
        ImageView profileImage = findViewById(R.id.profile_image);
        Picasso.get().load(user.getPhotoUrl()).centerCrop().fit().into(profileImage);
        Button signOut = findViewById(R.id.sign_out);

        signOut.setOnClickListener(view -> {
            DialogFragment newFragment = new NoticeDialogFragment();
            newFragment.show(getSupportFragmentManager(), SIGNOUT);
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(SIGNOUT_RESULTS, true);
        setResult(MainActivity.RESULT_OK);
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}


