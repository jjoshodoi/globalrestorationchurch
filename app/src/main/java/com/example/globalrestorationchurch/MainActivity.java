package com.example.globalrestorationchurch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 999;
    public static final int NEW_USER_RESULT = 343;
    public static String API_KEY;
    private FirebaseUser user;
    public static String userName;

    private static void goLoginScreen(MainActivity context, int resultCode) {
        context.startActivity(new Intent(context, LoginActivity.class));
        context.setResult(resultCode);
        context.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API_KEY = getApplicationContext().getResources().getString(R.string.google_api_key);

        user = getIntent().getParcelableExtra(LoginActivity.FIREBASE_USER_KEY);
        if (user != null) {
            userName = user.getDisplayName();
        }

        // improvements
        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_sermon, R.id.navigation_calendar, R.id.navigation_chat, R.id.navigation_giving, R.id.navigation_connect)
                .build();
        // sermons, calandar, chat, giving, connect
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;
            case R.id.action_account_details:
                Intent intent = new Intent(this, UserDetailsActivity.class);
                intent.putExtra(LoginActivity.FIREBASE_USER_KEY, user);
                startActivityForResult(intent, REQUEST_CODE);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == R.string.are_you_sure_signout) {
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(task -> {
                            Toast.makeText(this, "Sign out successful!", Toast.LENGTH_SHORT).show();
                            MainActivity.goLoginScreen(this, LoginActivity.RESULT_OK);
                        })
                        .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_LONG).show());
            } else if (resultCode == R.string.are_you_sure_delete) {
                AuthUI.getInstance()
                        .delete(MainActivity.this)
                        .addOnCompleteListener(task -> {
                            Toast.makeText(this, "Account has been deleted!", Toast.LENGTH_SHORT).show();
                            MainActivity.goLoginScreen(this, LoginActivity.RESULT_OK);
                        })
                        .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_LONG).show());
            }
            //Write your code if there's no result
        }
    }//onActivityResult

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
//        menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_home_black_24dp)); // image url

        final Target mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                Log.d("DEBUG", "onBitmapLoaded");
                BitmapDrawable mBitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                //                                mBitmapDrawable.setBounds(0,0,24,24);
                // setting icon of Menu Item or Navigation View's Menu Item
                menu.getItem(0).setIcon(mBitmapDrawable);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Log.d("DEBUG", "onBitmapFailed");
            }

            @Override
            public void onPrepareLoad(Drawable drawable) {
                Log.d("DEBUG", "onPrepareLoad");
            }
        };

        Picasso.get().load(user.getPhotoUrl()).into(mTarget);
        return true;
    }
}
