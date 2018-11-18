/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.team7.cmput301.android.theirisproject.model.BodyPhoto;

import java.util.List;

/**
 * ImageFormAdapter should be used for Image Lists in forms
 *
 * @author itstc
 * */
public class BodyPhotoListAdapter extends RecyclerView.Adapter<BodyPhotoListAdapter.ImageFormViewHolder> {
    private boolean isForm;
    private int itemLayout;
    private List<BodyPhoto> images;
    public BodyPhotoListAdapter(List<BodyPhoto> images, boolean isForm) {
        this.isForm = isForm;
        this.images = images;
        this.itemLayout = R.layout.problem_image_item;
    }

    @Override
    public BodyPhotoListAdapter.ImageFormViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View imageItem = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        BodyPhotoListAdapter.ImageFormViewHolder vh = new BodyPhotoListAdapter.ImageFormViewHolder(imageItem);
        return vh;
    }

    @Override
    public void onBindViewHolder(BodyPhotoListAdapter.ImageFormViewHolder holder, int position) {
        ImageView image = holder.imageItem.findViewById(R.id.image_item_view);
        image.setImageBitmap(images.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void removeItem(int position) {
        images.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void setItems(List<BodyPhoto> images) {
        this.images = images;
    }

    /**
     * Our View for each item should be able to remove the item by having a
     * remove button
     * */
    public class ImageFormViewHolder extends RecyclerView.ViewHolder {
        public View imageItem;
        public ImageFormViewHolder(View itemView) {
            super(itemView);
            this.imageItem = itemView;

            // add X button functionality if its a form
            if(isForm) {
                FloatingActionButton removeButton = imageItem.findViewById(R.id.image_item_remove);
                removeButton.setVisibility(View.VISIBLE);
                removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BodyPhotoListAdapter.this.removeItem(getAdapterPosition());
                    }
                });
            }
        }
    }
}
