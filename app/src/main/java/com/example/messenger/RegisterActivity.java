package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private EditText editTextEmailRegister;
    private EditText editTextPasswordRegister;

    private Button buttonSignUp;

    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        setObservers();
        setOnClickListeners();
    }

    private void setObservers() {
        viewModel.getIsUserCreated().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    Intent intent = UsersActivity.newIntent(RegisterActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Toast.makeText(
                        RegisterActivity.this,
                        error,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void setOnClickListeners() {
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = trimString(editTextEmailRegister);
                String password = trimString(editTextPasswordRegister);
                String name = trimString(editTextName);
                String lastName = trimString(editTextLastName);
                String age = trimString(editTextAge);
                if (email.isEmpty() || password.isEmpty() ||
                        name.isEmpty() || lastName.isEmpty() ||
                        age.isEmpty()) {
                    Toast.makeText(
                            RegisterActivity.this,
                            "There can't be blank fields",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    viewModel.createNewUserWithEmailAndPassword(email, password, name, lastName, age);
                }
            }
        });
    }

    private String trimString(EditText editText){
        return editText.getText().toString().trim();
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

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
    }
}