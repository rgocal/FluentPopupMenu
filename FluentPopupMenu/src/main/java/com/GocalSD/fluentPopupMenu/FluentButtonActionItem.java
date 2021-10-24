package com.GocalSD.fluentPopupMenu;

import android.view.View;

public class FluentButtonActionItem {

    private int itemId;
    private String itemTitle;
    private boolean isSticky;
    private View.OnClickListener listener;

    public FluentButtonActionItem(final int actionId, final String title, final boolean sticky, final View.OnClickListener clickListener) {
        this.itemTitle = title;
        this.itemId = actionId;
        this.isSticky = sticky;
        this.listener = clickListener;
    }

    public void setItemTitle(String title){
        this.itemTitle = title;
    }

    public String getItemTitle(){
        return this.itemTitle;
    }

    public void setItemId(int id){
        this.itemId = id;
    }

    public int getItemId(){
        return this.itemId;
    }

    public void setItemSticky(boolean sticky){
        this.isSticky = sticky;
    }

    public boolean isItemSticky(){
        return this.isSticky;
    }

    public void setClickListener(View.OnClickListener clickListener){
        this.listener = clickListener;
    }

    public View.OnClickListener getListener(){
        return this.listener;
    }

}
