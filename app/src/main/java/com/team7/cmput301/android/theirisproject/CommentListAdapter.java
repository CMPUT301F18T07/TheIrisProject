/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team7.cmput301.android.theirisproject.activity.ContactDialogFragment;
import com.team7.cmput301.android.theirisproject.model.Comment;
import com.team7.cmput301.android.theirisproject.model.User;

import java.util.List;

/**
 * CommentListAdapter handles putting comments
 * into the RecyclerView by inflating the data
 * into a CommentViewHolder
 *
 * @author itstc
 * */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>  {

    private final int COMMENT_ITEM_LAYOUT = R.layout.comment_item;
    private List<Comment> comments;

    private Context context;

    public CommentListAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View commentItem = LayoutInflater
                .from(parent.getContext())
                .inflate(COMMENT_ITEM_LAYOUT, parent, false);
        return new CommentViewHolder(commentItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment current = comments.get(position);
        holder.setNameView(current.getAuthor(), current.getRole().toString());
        holder.setDateView(current.getDate());
        holder.setBodyView(current.getBody());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((Activity)context).getFragmentManager();
                ContactDialogFragment dialog = ContactDialogFragment.newInstance(current.getContact());
                dialog.show(fm, "fragment_contact_card");
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setItems(List<Comment> comments) {
        this.comments = comments;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        public View commentItem;
        private TextView nameView;
        private TextView dateView;
        private TextView bodyView;
        private TextView roleView;
        public CommentViewHolder(View item) {
            super(item);
            roleView = item.findViewById(R.id.comment_item_role);
            nameView = item.findViewById(R.id.comment_item_name);
            dateView = item.findViewById(R.id.comment_item_date);
            bodyView = item.findViewById(R.id.comment_item_body);
        }

        public void setNameView(String name, String role) {
            if (role.equals(User.UserType.PATIENT.toString())) this.roleView.setBackgroundColor(Color.RED);
            else this.roleView.setBackgroundColor(Color.GREEN);
            this.nameView.setText(name);
        }

        public void setDateView(String date) {
            this.dateView.setText(date);
        }

        public void setBodyView(String body) {
            this.bodyView.setText(body);
        }
    }

}
