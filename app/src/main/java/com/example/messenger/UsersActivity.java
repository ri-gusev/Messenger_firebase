package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Random;


public class UsersActivity extends AppCompatActivity {

    private static final String TAG = "UsersActivity";

    private static final String NAME = "Name";
    private static final String LASTNAME = "LastName";
    private static final String AGE = "Age";

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbUser = firebaseDatabase.getReference("User");

    private UsersViewModel usersViewModel;
    private RecyclerView recyclerView;
    private UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        initViews();
        setObservers();
        setListeners();

//        for (int i = 0; i < 10; i++) {
//            User user = new User("Name"+i, "LastName"+i,
//                    "age"+i,new Random().nextBoolean(), ""+i);
//            dbUser.push().setValue(user);
//        }

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

    private void setListeners(){
        usersAdapter.setOnUserClickListener(new UsersAdapter.onUserClickListener() {
            @Override
            public void onUserClick() {
                // Intent to textUserActivity
            }
        });

        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User value = dataSnapshot.getValue(User.class);
                    Log.d(TAG, value.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.toString());
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
        usersAdapter = new UsersAdapter();
        recyclerView = findViewById(R.id.RecyclerViewUsers);
        recyclerView.setAdapter(usersAdapter);
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