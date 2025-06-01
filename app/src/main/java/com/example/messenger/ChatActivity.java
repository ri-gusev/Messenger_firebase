package com.example.messenger;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ChatActivity extends AppCompatActivity {

    private TextView textViewUserName;
    private View viewIsOnline;
    private RecyclerView recyclerViewSendMessage;
    private EditText editTextSendMessage;
    private ImageView imageViewSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);

        initViews();
    }

    private void initViews(){
        textViewUserName = findViewById(R.id.TextViewUserName);
        viewIsOnline = findViewById(R.id.ViewIsOnline);
        recyclerViewSendMessage = findViewById(R.id.RecyclerViewSendMessage);
        editTextSendMessage = findViewById(R.id.EditTextSendMessage);
        imageViewSendMessage = findViewById(R.id.imageViewSendMessage);
    }
}