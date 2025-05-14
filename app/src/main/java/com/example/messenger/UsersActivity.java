package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class UsersActivity extends AppCompatActivity {

    private static final String NAME = "Name";
    private static final String LASTNAME = "LastName";
    private static final String AGE = "Age";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);



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
            mAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    public void initViews(){
        mAuth = FirebaseAuth.getInstance();
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