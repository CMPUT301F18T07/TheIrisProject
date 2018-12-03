/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.team7.cmput301.android.theirisproject;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.team7.cmput301.android.theirisproject.model.Photo;

import java.util.List;

/**
 *
 * Reference: https://www.youtube.com/watch?v=Q2FPDI99-as
 *
 * @author jtfwong
 * */
public class ImageSlideAdapter<M extends Photo> extends PagerAdapter {
    private Context context;
    private List<M> images;

    public ImageSlideAdapter(Context context, List<M> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    /***
     * isViewFromObject() checks if image created is an object
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /***
     * instantiateItem() creates image views from a list of
     * all record photos associated with the problem
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Photo photo = images.get(position);
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(photo.getPhoto());
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    public void setItems(List<M> images) {
        this.images = images;
    }
}