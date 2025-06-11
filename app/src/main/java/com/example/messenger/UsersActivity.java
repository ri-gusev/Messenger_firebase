package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class UsersActivity extends AppCompatActivity {

    private static final String EXTRA_CURRENT_USER_ID = "current_id";

    private UsersViewModel usersViewModel;
    private RecyclerView recyclerView;
    private UsersAdapter usersAdapter;

    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        initViews();

        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);

        setObservers();
        setListeners();
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

        usersViewModel.getUserListLV().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                usersAdapter.setUsers(users);
            }
        });

        usersViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(
                        UsersActivity.this,
                        s,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void setListeners(){
        usersAdapter.setOnUserClickListener(new UsersAdapter.onUserClickListener() {
            @Override
            public void onUserClick(User user) {
                startActivity(ChatActivity.newIntent(UsersActivity.this, currentUserId, user.getId()));
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

    @Override
    protected void onResume() {
        super.onResume();
        usersViewModel.setUserOnline(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        usersViewModel.setUserOnline(false);
    }

    public void initViews(){
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        usersAdapter = new UsersAdapter();
        recyclerView = findViewById(R.id.RecyclerViewUsers);
        recyclerView.setAdapter(usersAdapter);
    }

    public static Intent newIntent(Context context, String currentUserId){
        Intent intent = new Intent(context, UsersActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        return intent;
    }
}