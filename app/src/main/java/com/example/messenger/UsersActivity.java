package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsersActivity extends AppCompatActivity {

    private static final String NAME = "Name";
    private static final String LASTNAME = "LastName";
    private static final String AGE = "Age";

    private UsersViewModel usersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        initViews();
        setObservers();
    }

    private void setObservers(){
        usersViewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null){
                    startActivity(MainActivity.newIntent(UsersActivity.this));
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(
                R.menu.sign_out_menu,
                menu
        );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.SignOutMenu){
            usersViewModel.logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    public void initViews(){
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
    }

    public static Intent newIntent(Context context){
        return new Intent(context, UsersActivity.class);
    }

    public static Intent newIntent(Context context, String name, String lastName, String age){
        Intent intent = new Intent(context, UsersActivity.class);
        intent.putExtra(NAME, name);
        intent.putExtra(LASTNAME, lastName);
        intent.putExtra(AGE, age);
        return intent;
    }
}