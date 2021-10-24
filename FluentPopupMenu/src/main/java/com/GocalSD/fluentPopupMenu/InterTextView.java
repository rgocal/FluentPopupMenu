package com.GocalSD.fluentPopupMenu;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.google.android.material.textview.MaterialTextView;

public class InterTextView extends MaterialTextView {

    public InterTextView(Context context) {
        super(context);
    }

    public InterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);

    }

    public InterTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont(context);

    }

    private void setFont(Context mContext) {
        Typeface font = Typeface.createFromAsset(mContext.getAssets(),"fonts/Inter-Medium.ttf");
        setTypeface(font);
    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if(focused)
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean focused) {
        if(focused)
            super.onWindowFocusChanged(focused);
    }


    @Override
    public boolean isFocused() {
        return true;
    }
}