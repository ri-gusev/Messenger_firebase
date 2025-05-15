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

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;

    private Button buttonResetPassword;

    private ForgotPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();
        setObservers();
        setOnClickListeners();
    }

    private void setObservers(){
        viewModel.getIfPasswordWasSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean changed) {
                if (changed){
                    Toast.makeText(
                            ForgotPasswordActivity.this,
                            "reset password was sent on your email",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Toast.makeText(
                        ForgotPasswordActivity.this,
                        error,
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void setOnClickListeners() {
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(editTextEmail.getText()).trim();
                if (email.isEmpty()) {
                    Toast.makeText(
                            ForgotPasswordActivity.this,
                            "Email can't be blank",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    viewModel.IfForgotPasswordResetIt(email);
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }

    public void initViews() {
        editTextEmail = findViewById(R.id.EditTextEmailForgotActivity);

        buttonResetPassword = findViewById(R.id.ButtonSendNewPassword);

        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
    }
}