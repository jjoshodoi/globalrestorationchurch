package com.example.globalrestorationchurch;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class UserDetailsActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener{

    public static final String GOOGLE_ACCOUNT = "google_account";
    public static final String SIGNOUT_RESULTS = "SIGNOUTRESULTS";
    private TextView profileName, profileEmail;
    private ImageView profileImage;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        user = getIntent().getParcelableExtra(LoginActivity.FIREBASE_USER_KEY);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        profileName = findViewById(R.id.profile_text);
        profileName.setText(user.getDisplayName());
        profileEmail = findViewById(R.id.profile_email);
        profileEmail.setText(user.getEmail());
        profileImage = findViewById(R.id.profile_image);
        Picasso.get().load(user.getPhotoUrl()).centerCrop().fit().into(profileImage);
        Button signOut = findViewById(R.id.sign_out);



        signOut.setOnClickListener(view -> {
            DialogFragment newFragment = new NoticeDialogFragment();
            newFragment.show(getSupportFragmentManager(), "signout?");

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


