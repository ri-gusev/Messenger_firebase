package com.example.messenger;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;

    private Button buttonSendLogInData;

    private TextView textViewIfForgotPassword;
    private TextView textViewToRegisterActivity;

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setObservers();
        setOnClickListeners();
    }

    private void setObservers(){
        mainViewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    startActivity(UsersActivity.newIntent(MainActivity.this));
                }
            }
        });

        mainViewModel.getFailureMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(
                        MainActivity.this,
                        message,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void setOnClickListeners() {
        buttonSendLogInData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(editTextEmail.getText()).trim();
                String password = String.valueOf(editTextPassword.getText()).trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                            MainActivity.this,
                            "Email or password can't be blank",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    mainViewModel.SignInUser(email, password);
                }
            }
        });

        textViewIfForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ForgotPasswordActivity.newIntent(MainActivity.this));
            }
        });

        textViewToRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegisterActivity.newIntent(MainActivity.this));
            }
        });
    }

    public void initViews() {
        editTextEmail = findViewById(R.id.EditTextEmail);
        editTextPassword = findViewById(R.id.EditTextPassword);

        buttonSendLogInData = findViewById(R.id.ButtonSendLogInData);

        textViewIfForgotPassword = findViewById(R.id.TextViewForgotPassword);
        textViewToRegisterActivity = findViewById(R.id.TextViewSignUp);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

    }
}