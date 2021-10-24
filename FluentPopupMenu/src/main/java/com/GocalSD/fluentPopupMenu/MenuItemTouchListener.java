package com.GocalSD.fluentPopupMenu;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class MenuItemTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private ClickListener clickListener;
    //adds a pause in between clicks
    private boolean clickable = true;

    public MenuItemTouchListener(){
    }

    public MenuItemTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;

                float x1 = e1.getX();
                float y1 = e1.getY();

                float x2 = e2.getX();
                float y2 = e2.getY();

                Direction direction = getDirection(x1,y1,x2,y2);
                View child = recyclerView.findChildViewUnder(x1, y1);

                if (child != null && clickListener != null && direction == Direction.down) {
                    clickable = false;
                    result = true;
                    int position = recyclerView.getChildAdapterPosition(child);
                    Log.d("Glance", "Item " + position + " Swiped");
                    clickListener.onSwiped(child, position);
                }else{
                    clickable = true;
                }

                return result;
            }

            public Direction getDirection(float x1, float y1, float x2, float y2){
                double angle = getAngle(x1, y1, x2, y2);
                return Direction.fromAngle(angle);
            }

            public double getAngle(float x1, float y1, float x2, float y2) {
                double rad = Math.atan2(y1-y2,x2-x1) + Math.PI;
                return (rad*180/Math.PI + 180)%360;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                clickable = true;
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                clickable = true;
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null && clickable) {
                    clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    public ClickListener getClickListener(){
        return clickListener;
    }

    public enum Direction{
        up,
        down,
        left,
        right;

        public static Direction fromAngle(double angle){
            if(inRange(angle, 45, 135)){
                return Direction.up;
            }
            else if(inRange(angle, 0,45) || inRange(angle, 315, 360)){
                return Direction.right;
            }
            else if(inRange(angle, 225, 315)){
                return Direction.down;
            }
            else{
                return Direction.left;
            }

        }

        private static boolean inRange(double angle, float init, float end){
            return (angle >= init) && (angle < end);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e) && clickable) {
            clickListener.onClick(child, rv.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
        void onSwiped(View view, int position);
    }
}