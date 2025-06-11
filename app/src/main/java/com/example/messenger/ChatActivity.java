package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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

    private ChatViewModel chatViewModel;
    private ChatViewModelFactory chatViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initViews();
        chatViewModelFactory = new ChatViewModelFactory(currentUserId, otherUserId);
        chatViewModel = new ViewModelProvider(this, chatViewModelFactory).get(ChatViewModel.class);
        setObservers();
        setListeners();
    }

    private void setListeners(){
        imageViewSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message(
                        otherUserId,
                        currentUserId,
                        editTextSendMessage.getText().toString().trim());
                chatViewModel.sendMessage(message);
            }
        });
    }

    private void setObservers() {
        chatViewModel.getErrors().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null){
                    Toast.makeText(
                            ChatActivity.this,
                            s,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        chatViewModel.getIsMessageSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    editTextSendMessage.setText("");
                }
            }
        });

        chatViewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messagesAdapter.setMessages(messages);
            }
        });

        chatViewModel.getOtherUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                String userInfo = String.format("%s %s", user.getName(), user.getLastName());
                textViewUserName.setText(userInfo);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatViewModel.setUserOnline(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatViewModel.setUserOnline(false);
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