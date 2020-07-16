package com.example.globalrestorationchurch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    public static final String FIREBASE_USER_KEY = "FIREBASEKEY";
    private static final int MY_REQUEST_CODE = 123;
    private FirebaseAuth mAuth;

    private static void showSignInOptions(LoginActivity context) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build()
        );

        context.startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppTheme)
                        .setLogo(R.drawable.common_google_signin_btn_icon_dark)
                        .build(), MY_REQUEST_CODE
        );
    }

    private static void signIn(LoginActivity context, FirebaseAuth mAuth) {
        FirebaseUser user = mAuth.getCurrentUser();

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(FIREBASE_USER_KEY, user);
        context.startActivity(intent);
        context.finish();

        if (user != null) {
            Toast.makeText(context, "Welcome! " + user.getDisplayName(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Welcome!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (mAuth.getCurrentUser() != null) {
            LoginActivity.signIn(this, mAuth);
        } else {
            showSignInOptions(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                LoginActivity.signIn(this, mAuth);
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(this, "Sign in cancelled!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(this, "Unknown error occurred!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}