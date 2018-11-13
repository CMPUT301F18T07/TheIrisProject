/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.team7.cmput301.android.theirisproject.model.BodyPhoto;

import java.util.ArrayList;

/**
 * ImageFormAdapter should be used for Image Lists in forms
 *
 * @author itstc
 * */
public class ImageFormAdapter extends RecyclerView.Adapter<ImageFormAdapter.ImageFormViewHolder> {
    private int itemLayout;
    private ArrayList<BodyPhoto> images;
    public ImageFormAdapter(ArrayList<BodyPhoto> images, int itemLayout) {
        this.images = images;
        this.itemLayout = itemLayout;
    }

    @Override
    public ImageFormAdapter.ImageFormViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View imageItem = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        ImageFormAdapter.ImageFormViewHolder vh = new ImageFormAdapter.ImageFormViewHolder(imageItem);
        return vh;
    }

    @Override
    public void onBindViewHolder(ImageFormAdapter.ImageFormViewHolder holder, int position) {
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
    /**
     * Our View for each item should be able to remove the item by having a
     * remove button
     * */
    public class ImageFormViewHolder extends RecyclerView.ViewHolder {
        public View imageItem;
        public ImageFormViewHolder(View itemView) {
            super(itemView);
            this.imageItem = itemView;
            FloatingActionButton removeButton = imageItem.findViewById(R.id.image_item_remove);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageFormAdapter.this.removeItem(getAdapterPosition());
                }
            });
        }
    }
}
