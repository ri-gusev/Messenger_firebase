package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordViewModel extends ViewModel {

    private MutableLiveData<Boolean> ifPasswordWasSent = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private FirebaseAuth mAuth;

    public ForgotPasswordViewModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> getIfPasswordWasSent() {
        return ifPasswordWasSent;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void IfForgotPasswordResetIt(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        ifPasswordWasSent.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        errorMessage.setValue(e.getMessage());
                    }
                });
    }
}