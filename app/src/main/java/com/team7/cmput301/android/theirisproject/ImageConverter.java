/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Use this class to encode/decode bitmaps for our app
 * @author itstc
 * */
public class ImageConverter {

    public static String base64EncodeBitmap(Bitmap img) {
        return Base64.encodeToString(img.getNinePatchChunk(), Base64.DEFAULT);
    }

    public static Bitmap base64DecodeBitmap(String blob) {
        return BitmapFactory.decodeByteArray(Base64.decode(blob, Base64.DEFAULT),0, 256*256);
    }
}
