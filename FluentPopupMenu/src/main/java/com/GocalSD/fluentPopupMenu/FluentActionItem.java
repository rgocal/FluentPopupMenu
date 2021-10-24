package com.GocalSD.fluentPopupMenu;

import android.graphics.drawable.Drawable;

public class FluentActionItem {

    private boolean isSticky;
    private Drawable icon;
    private String title;
    private int actionId = -1;

    public FluentActionItem(final int actionId, final String title, final Drawable icon, final boolean sticky) {
        this.title = title;
        this.icon = icon;
        this.actionId = actionId;
        this.isSticky = sticky;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getActionId() {
        return actionId;
    }

    public boolean isItemSticky(){
        return this.isSticky;
    }

    public void setStickyItem(boolean sticky){
        this.isSticky = sticky;
    }
}