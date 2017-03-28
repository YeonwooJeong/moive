package com.yonoo.movie;
import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String descStr ;
    public static MainActivity main = new MainActivity();

    public void setImage(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {

        titleStr = title ;
    }

    public Drawable getImage() {

        return this.iconDrawable ;
    }
    public String getTitle() {

        return this.titleStr ;
    }

}



