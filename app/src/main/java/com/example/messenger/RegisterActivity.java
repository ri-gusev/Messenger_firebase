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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private EditText editTextEmailRegister;
    private EditText editTextPasswordRegister;

    private Button buttonSignUp;

    private static final String TAG = "RegisterActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        initViews();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmailRegister.getText().toString().trim();
                String password = editTextPasswordRegister.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String lastName = editTextLastName.getText().toString().trim();
                String age = editTextAge.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty() ||
                        name.isEmpty() || lastName.isEmpty() ||
                        age.isEmpty()) {
                    Toast.makeText(
                            RegisterActivity.this,
                            "There can't be blank fields",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    createNewUserWithEmailAndPassword(email, password);
                    //launch intent to user activity
                }
            }
        });
    }

    private void createNewUserWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "success sign up");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                RegisterActivity.this,
                                e.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }


    public void initViews() {
        editTextName = findViewById(R.id.EditTextName);
        editTextLastName = findViewById(R.id.EditTextLastName);
        editTextAge = findViewById(R.id.EditTextAge);
        editTextEmailRegister = findViewById(R.id.EditTextEmailRegister);
        editTextPasswordRegister = findViewById(R.id.EditTextPasswordRegister);

        buttonSignUp = findViewById(R.id.ButtonSignUp);

        mAuth = FirebaseAuth.getInstance();
    }
}