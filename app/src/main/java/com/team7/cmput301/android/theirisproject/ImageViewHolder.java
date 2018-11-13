/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ImageViewHolder extends RecyclerView.ViewHolder {
    public View imageItem;
    public ImageViewHolder(View itemView) {
        super(itemView);
        this.imageItem = itemView;
    }
}