/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.team7.cmput301.android.theirisproject.activity.AddBodyLocationDialogFragment;
import com.team7.cmput301.android.theirisproject.activity.ViewImageFragment;
import com.team7.cmput301.android.theirisproject.model.BodyPhoto;
import com.team7.cmput301.android.theirisproject.model.Photo;

import java.util.List;

/**
 * ImageListAdapter is used to display a gallery of bitmap images on the
 * activity given its recycler view
 *
 * @author itstc
 * */
public class ImageListAdapter<M extends Photo> extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {
    public static final int TYPE_BODY_LOCATION_FORM = 1;
    public static final int TYPE_IMAGE_LIST = 0;
    private boolean isForm;
    private int itemLayout;
    private List<M> images;
    private int type = TYPE_IMAGE_LIST;

    private Activity context;
    private ImageListListener listener;

    public interface ImageListListener {
        void onDeletePhoto(Photo photo);
    }

    public ImageListAdapter(Activity context, List<M> images, boolean isForm) {
        this.context = context;
        this.isForm = isForm;
        this.images = images;
        this.itemLayout = R.layout.image_item;
    }

    public ImageListAdapter(Activity context, List<M> images, boolean isForm, ImageListListener listener) {
        this(context, images, isForm);
        this.listener = listener;
    }

    public ImageListAdapter(Activity context, List<M> images, int type) {
        this.context = context;
        this.isForm = false;
        this.images = images;
        this.itemLayout = R.layout.image_item;
        this.type = type;
    }

    @Override
    public ImageListAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View imageItem = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        ImageListAdapter.ImageViewHolder vh = new ImageListAdapter.ImageViewHolder(imageItem);
        return vh;
    }

    /**
     * onBindViewHolder is a factory method that produces a onClick method that responds
     * according to the type of ImageList.
     * */
    @Override
    public void onBindViewHolder(ImageListAdapter.ImageViewHolder holder, int position) {
        Photo photo = images.get(position);
        ImageView image = holder.imageItem.findViewById(R.id.image_item_view);
        image.setImageBitmap(photo.getPhoto());

        // factory method to assign click listener to type of ImageListAdapter
        switch(type) {
            case TYPE_BODY_LOCATION_FORM: bindListenerToBodyLocationForm(holder, photo); break;
            default: bindListenerToImageList(holder, photo); break;
        }
    }

    /**
     * bind Image click to enlarge photo functionality
     * @param holder: current image holder
     * @param photo the photo data
     * */
    private void bindListenerToImageList(ImageListAdapter.ImageViewHolder holder, Photo photo) {
        holder.imageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = context.getFragmentManager();
                ViewImageFragment imageDialog = ViewImageFragment.newInstance(photo.getMetaData(), photo.getPhoto(), photo.getDate());
                imageDialog.show(fm, "fragment_enlarge_image");
            }
        });
    }

    /**
     * bind Image click to pick body location functionality
     * @param holder: current image holder
     * @param photo the photo data
     * */
    private void bindListenerToBodyLocationForm(ImageListAdapter.ImageViewHolder holder, Photo photo) {
        holder.imageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = context.getFragmentManager();
                AddBodyLocationDialogFragment addBodyLocationDialog = AddBodyLocationDialogFragment.newInstance((BodyPhoto) photo);
                addBodyLocationDialog.show(fm, "fragment_pick_body_location");
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void removeItem(int position) {
        Photo target = images.remove(position);
        if (listener != null) listener.onDeletePhoto(target);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void setItems(List<M> images) {
        this.images = images;
    }

    public boolean isAdapterForm() {
        return isForm;
    }

    /**
     * Our View for each item should be able to remove the item by having a
     * remove button
     * */
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public View imageItem;
        public ImageViewHolder(View itemView) {
            super(itemView);
            this.imageItem = itemView;

            // add X button functionality if its a form
            if(isForm) {
                FloatingActionButton removeButton = imageItem.findViewById(R.id.image_item_remove);
                removeButton.setVisibility(View.VISIBLE);
                removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImageListAdapter.this.removeItem(getAdapterPosition());
                    }
                });
            }
        }
    }
}
