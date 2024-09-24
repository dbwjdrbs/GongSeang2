package com.example.helloboard;

public class CommentItem {
    private int imageResId;

    private String strName;

    public CommentItem(int a_resId, String a_strName) {
        imageResId = a_resId;
        strName = a_strName;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return strName;
    }
}
