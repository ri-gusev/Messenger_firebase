package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersViewModel extends ViewModel {

    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<List<User>> userListLV = new MutableLiveData<>();


    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userDbReference;

    public UsersViewModel() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user.setValue(firebaseAuth.getCurrentUser());
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        userDbReference = firebaseDatabase.getReference("User");

        userDbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> userList = new ArrayList<>();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser == null){
                    return;
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    if (user == null){
                        return;
                    }
                    if (!currentUser.getUid().equals(user.getId())){
                        userList.add(user);
                    }
                }
                userListLV.setValue(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue(error.getMessage());
            }
        });
    }



    public void setUserOnline(Boolean isOnline){
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null){
            return;
        }
        userDbReference.child(firebaseUser.getUid()).child("online").setValue(isOnline);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<List<User>> getUserListLV() {
        return userListLV;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void logOut() {
        setUserOnline(false);
        mAuth.signOut();
    }
}
