/*
 * Copyright (c) Team 7, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 *
 *
 */

package com.team7.cmput301.android.theirisproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Use this class to encode/decode bitmaps for our app
 * @author itstc
 * */
public class ImageConverter {

    /**
     * base64EncodeBitmap will take in a bitmap image and encode
     * it to a base64 string as that is our database image format
     *
     * @param img: Bitmap image
     * @return String: base64 string
     * */
    public static String base64EncodeBitmap(Bitmap img) {
        byte[] byteImg = convertBitmapToBytes(img);
        return Base64.encodeToString(byteImg, Base64.DEFAULT);
    }

    /**
     * base64DecodeBitmap will take in a string base64 and convert
     * it to a Bitmap image
     *
     * @param blob: base64 string
     * @return Bitmap: bitmap image
     * */
    public static Bitmap base64DecodeBitmap(String blob) {
        byte[] res = Base64.decode(blob, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(res,0, res.length);
    }

    /**
     * convertBitmapToBytes will take a bitmap image and
     * convert it to a byte array
     *
     * @param img: Bitmap image
     * @return byte[]: byte array
     * */
    public static byte[] convertBitmapToBytes(Bitmap img) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
