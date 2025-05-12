package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;

    private Button buttonResetPassword;

    private static final String TAG = "ForgotPasswordActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        initViews();

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(editTextEmail.getText()).trim();
                IfForgotPasswordResetIt(email);
            }
        });
    }

    public void IfForgotPasswordResetIt(String email){
        if (email.isEmpty()) {
            Toast.makeText(
                    ForgotPasswordActivity.this,
                    "Email can't be blank",
                    Toast.LENGTH_SHORT
            ).show();
        }else {
            mAuth.sendPasswordResetEmail(email)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(
                                    ForgotPasswordActivity.this,
                                    "reset password was sent on your email",
                                    Toast.LENGTH_LONG
                            ).show();
                            Log.d(TAG, "reset password was sent");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(
                                    ForgotPasswordActivity.this,
                                    e.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
        }
    }

    public static Intent newIntent(Context context){
        return new Intent(context, ForgotPasswordActivity.class);
    }

    public void initViews(){
        editTextEmail = findViewById(R.id.EditTextEmailForgotActivity);

        buttonResetPassword = findViewById(R.id.ButtonSendNewPassword);

        mAuth = FirebaseAuth.getInstance();
    }
}