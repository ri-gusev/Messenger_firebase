package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends ViewModel {

    private MutableLiveData<List<Message>> messages = new MutableLiveData<>();
    private MutableLiveData<String> errors = new MutableLiveData<>();
    private MutableLiveData<User> otherUser = new MutableLiveData<>();
    private MutableLiveData<Boolean> isMessageSent = new MutableLiveData<>();

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userDbReference = firebaseDatabase.getReference("User");
    private DatabaseReference messageDbReference = firebaseDatabase.getReference("Message");

    private String currentUserId;
    private String otherUserId;

    public ChatViewModel(String currentUserId, String otherUserId) {
        this.currentUserId = currentUserId;
        this.otherUserId = otherUserId;
        userDbReference.child(otherUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                otherUser.setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        messageDbReference
                .child(currentUserId)
                .child(otherUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Message> messageList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Message message = dataSnapshot.getValue(Message.class);
                            messageList.add(message);
                        }
                        messages.setValue(messageList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void sendMessage(Message message) {
        messageDbReference
                .child(message.getSenderId())
                .child(message.getReceiverId())
                .push()
                .setValue(message)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        messageDbReference
                                .child(message.getReceiverId())
                                .child(message.getSenderId())
                                .push()
                                .setValue(message)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        isMessageSent.setValue(true);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        errors.setValue(e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        errors.setValue(e.getMessage());
                    }
                });
    }

    public LiveData<String> getErrors() {
        return errors;
    }

    public LiveData<Boolean> getIsMessageSent() {
        return isMessageSent;
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public LiveData<User> getOtherUser() {
        return otherUser;
    }
}
