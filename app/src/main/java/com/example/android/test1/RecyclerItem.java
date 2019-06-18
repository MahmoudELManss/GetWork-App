package com.example.android.test1;

import android.widget.ImageView;

public class RecyclerItem {

    private String mName, mCatg, mState, mDescription;
    private int mImageView;

    public RecyclerItem(String name, String catg, String state, String description, int imageView) {
        this.mName = name;
        this.mCatg = catg;
        this.mState = state;
        this.mDescription = description;
        this.mImageView = imageView;

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getCatg() {
        return mCatg;
    }

    public void setCatg(String catg) {
        this.mCatg = catg;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        this.mState = state;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public int getmImageView() {
        return mImageView;
    }

    public void setmImageView(int mImageView) {
        this.mImageView = mImageView;
    }
}
