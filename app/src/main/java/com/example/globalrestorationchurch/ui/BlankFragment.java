package com.example.globalrestorationchurch.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.globalrestorationchurch.R;
import com.example.globalrestorationchurch.ui.connect.Connect;
import com.example.globalrestorationchurch.ui.connect.ConnectViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class BlankFragment extends Fragment {

    public static final String GOOGLE_ACCOUNT = "google_account";
    private TextView profileName, profileEmail;
    private ImageView profileImage;
    private Button signOut;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.connect_fragment, container, false);
        profileName = v.findViewById(R.id.profile_text);
        profileEmail = v.findViewById(R.id.profile_email);
        profileImage = v.findViewById(R.id.profile_image);
        signOut = v.findViewById(R.id.sign_out);

//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              /*
//              Sign-out is initiated by simply calling the googleSignInClient.signOut API. We add a
//              listener which will be invoked once the sign out is the successful
//               */
//                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        //On Succesfull signout we navigate the user back to LoginActivity
//                        Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                    }
//                });
//            }
//        });

        return v;
    }


//    private void setDataOnView() {
//        GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
//        Picasso.get().load(googleSignInAccount.getPhotoUrl()).centerInside().fit().into(profileImage);
//        profileName.setText(googleSignInAccount.getDisplayName());
//        profileEmail.setText(googleSignInAccount.getEmail());
//    }

}