package com.example.messenger;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<Boolean> isUserCreated = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private FirebaseAuth mAuth;

    public RegisterViewModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> getIsUserCreated() {
        return isUserCreated;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void createNewUserWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        isUserCreated.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        errorMessage.setValue(e.getMessage());
                        isUserCreated.setValue(false);
                    }
                });
    }
}
