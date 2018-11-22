package com.team7.cmput301.android.theirisproject.model;

public class BodyLocation {

    private String bodyPhotoId;
    private int x;
    private int y;

    public BodyLocation(String bodyPhotoId, int x, int y) {
        this.bodyPhotoId = bodyPhotoId;
        this.x = x;
        this.y = y;
    }

    public int[] getLocation() {
        return new int[]{x,y};
    }

    public String getBodyPhotoId() {
        return bodyPhotoId;
    }
}
