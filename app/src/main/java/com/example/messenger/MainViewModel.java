package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainViewModel extends ViewModel {

    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<String> failureMessage = new MutableLiveData<>();

    private static final String TAG = "MainViewModel";
    private FirebaseAuth mAuth;

    public MainViewModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<String> getFailureMessage() {
        return failureMessage;
    }

    public void SignInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        user.setValue(authResult.getUser());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        failureMessage.setValue(e.getMessage());
                    }
                });
    }

}
