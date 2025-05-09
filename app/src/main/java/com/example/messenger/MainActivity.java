package com.example.messenger;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;

    private Button buttonSendLogInData;

    private TextView textViewIfForgotPassword;
    private TextView textViewToRegisterActivity;

    private FirebaseAuth mAuth;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViews();

        buttonSendLogInData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(editTextEmail.getText()).trim();
                String password = String.valueOf(editTextPassword.getText()).trim();
                SignInUser(email, password);
            }
        });

        textViewIfForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch intent to forgot password activity
            }
        });

        textViewToRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch intent to register activity
            }
        });
    }

    public void SignInUser(String email, String password){
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                    MainActivity.this,
                    "Email or password can't be blank",
                    Toast.LENGTH_SHORT
            ).show();
        }else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            //Launch intent to users activity
                            Log.d(TAG, "success sign in");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(
                                    MainActivity.this,
                                    e.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
        }
    }

//    public void IfForgotPasswordResetIt(String email){
//        if (email.isEmpty()) {
//            Toast.makeText(
//                    MainActivity.this,
//                    "Email can't be blank",
//                    Toast.LENGTH_SHORT
//            ).show();
//        }else {
//            mAuth.sendPasswordResetEmail(email)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            Toast.makeText(
//                                    MainActivity.this,
//                                    "reset password was sent on your email",
//                                    Toast.LENGTH_LONG
//                            ).show();
//                            Log.d(TAG, "reset password was sent");
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(
//                                    MainActivity.this,
//                                    e.getMessage(),
//                                    Toast.LENGTH_LONG
//                            ).show();
//                        }
//                    });
//        }
//    }

    public void initViews(){
        editTextEmail = findViewById(R.id.EditTextEmail);
        editTextPassword = findViewById(R.id.EditTextPassword);

        buttonSendLogInData = findViewById(R.id.ButtonSendLogInData);

        textViewIfForgotPassword = findViewById(R.id.TextViewForgotPassword);
        textViewToRegisterActivity = findViewById(R.id.TextViewSignIn);

        mAuth = FirebaseAuth.getInstance();
    }
}