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

import java.util.ArrayList;

/**
 * ImageListAdapter should be used to display Images on a RecyclerView
 *
 * @author itstc
 * */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {
    private int itemLayout;
    private ArrayList<Bitmap> images;
    public ImageListAdapter(ArrayList<Bitmap> images, int itemLayout) {
        this.images = images;
        this.itemLayout = itemLayout;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View imageItem = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        ImageViewHolder vh = new ImageViewHolder(imageItem);
        return vh;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ImageView image = holder.imageItem.findViewById(R.id.image_item_view);
        image.setImageBitmap(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public View imageItem;
        public ImageViewHolder(View itemView) {
            super(itemView);
            this.imageItem = itemView;
        }
    }
}
