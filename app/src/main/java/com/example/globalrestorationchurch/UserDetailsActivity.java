package com.example.globalrestorationchurch;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class UserDetailsActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener {

    public static final String SIGNOUT = "signout?";
    public static final String DELETE = "delete?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        FirebaseUser user = getIntent().getParcelableExtra(LoginActivity.FIREBASE_USER_KEY);

        TextView profileName = findViewById(R.id.profile_text);
        TextView profileEmail = findViewById(R.id.profile_email);
        ImageView profileImage = findViewById(R.id.profile_image);

        Button signOut = findViewById(R.id.sign_out);
        Button deleteAccount = findViewById(R.id.delete_account);

        assert user != null;
        profileName.setText(user.getDisplayName());

        profileEmail.setText(user.getEmail());

        Picasso.get().load(user.getPhotoUrl()).centerCrop().config(Bitmap.Config.RGB_565).fit().into(profileImage);

        signOut.setOnClickListener(view -> {
            DialogFragment newFragment = new NoticeDialogFragment(R.string.are_you_sure_signout);
            newFragment.show(getSupportFragmentManager(), SIGNOUT);
        });

        deleteAccount.setOnClickListener(view -> {
            DialogFragment newFragment = new NoticeDialogFragment(R.string.are_you_sure_delete);
            newFragment.show(getSupportFragmentManager(), DELETE);
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        switch (((NoticeDialogFragment) dialog).displayText) {
            case R.string.are_you_sure_signout:
                setResult(R.string.are_you_sure_signout);
                finish();
                break;
            case R.string.are_you_sure_delete:
                setResult(R.string.are_you_sure_delete);
                finish();
                break;
            default:
        }
    }

}


