package com.example.messenger;

import android.content.Context;
import android.content.Intent;
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

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String EXTRA_CURRENT_USER_ID = "current_id";
    private static final String EXTRA_OTHER_USER_ID = "other_id";

    private TextView textViewUserName;
    private View viewIsOnline;
    private RecyclerView recyclerViewSendMessage;
    private EditText editTextSendMessage;
    private ImageView imageViewSendMessage;

    private MessagesAdapter messagesAdapter;

    private String currentUserId;
    private String otherUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initViews();

        List<Message> messageList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Message message = new Message(otherUserId, currentUserId, "Привет");
            messageList.add(message);
        }
        for (int i = 0; i < 10; i++) {
            Message message = new Message(currentUserId, otherUserId, "Привет");
            messageList.add(message);
        }
        messagesAdapter.setMessages(messageList);
    }

    private void initViews() {
        textViewUserName = findViewById(R.id.TextViewUserName);
        viewIsOnline = findViewById(R.id.ViewIsOnline);
        recyclerViewSendMessage = findViewById(R.id.RecyclerViewSendMessage);
        editTextSendMessage = findViewById(R.id.EditTextSendMessage);
        imageViewSendMessage = findViewById(R.id.imageViewSendMessage);

        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);

        messagesAdapter = new MessagesAdapter(currentUserId);
        recyclerViewSendMessage.setAdapter(messagesAdapter);

    }

    public static Intent newIntent(Context context, String currentUserId, String otherUserId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId);
        return intent;
    }
}