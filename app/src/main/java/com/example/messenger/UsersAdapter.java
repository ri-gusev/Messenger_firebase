package com.example.messenger;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<User> users = new ArrayList<>();
    private onUserClickListener onUserClickListener;

    public void setOnUserClickListener(UsersAdapter.onUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.user_item,
                parent,
                false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);

        String id = user.getId();
        String name = user.getName();
        String lastName = user.getLastName();
        String age = user.getAge();
        boolean isOnline = user.isOnline();

        String userInfo = String.format("%s %s, %s", name, lastName, age);
        holder.textViewUserInfo.setText(userInfo);

        int bgId;

        if (isOnline) {
            bgId = R.drawable.circle_green;
        } else {
            bgId = R.drawable.circle_red;
        }

        Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), bgId);
        holder.viewIsOnline.setBackground(drawable);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserClickListener != null) {
                    onUserClickListener.onUserClick();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    interface onUserClickListener {
        void onUserClick();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewUserInfo;
        private View viewIsOnline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserInfo = itemView.findViewById(R.id.TextViewUserInfo);
            viewIsOnline = itemView.findViewById(R.id.ViewCircleOnline);
        }
    }
}
