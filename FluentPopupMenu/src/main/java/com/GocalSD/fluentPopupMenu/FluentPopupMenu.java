package com.GocalSD.fluentPopupMenu;

import static com.GocalSD.fluentPopupMenu.FluentArrowDrawable.ARROW_DOWN;
import static com.GocalSD.fluentPopupMenu.FluentArrowDrawable.ARROW_UP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.PopupWindow.OnDismissListener;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

/*
A popup menu inspired by Windows 11
This popup menu was created to behave similar to the pixel launcher popup menu but to offer a new user interface.
Devs can use a Button Group to toggle action buttons at the bottom or top or both
Devs can take advantage of the mini bar list at the top of the menu or the Vertical list menu with long descriptions
Timers, animations, color and radias are also options
 */

public class FluentPopupMenu extends FluentBasePopupWindow implements OnDismissListener {

    private final View mArrowUp, mArrowDown, divider;
    private final LinearLayoutCompat popupHolder;
    private final RecyclerView simpleList, detailedList;
    private ArrayList<FluentActionItem> simpleItemList = new ArrayList<>();
    private ArrayList<FluentActionItem> detailedItemList = new ArrayList<>();
    private int normalColor;

    private final LinearLayoutCompat buttonActionListTop, buttonActionListBottom;

    private int backgroundRadias = 12, lengthDuration = 500, startDuration = 100, fadeDuration = 400;
    private boolean allowFadeAnimation = true;
    private OnDismissListener mDismissListener;

    @SuppressLint("RestrictedApi")
    public void setMenuColor(int menuColor) {
        this.normalColor = menuColor;
        MaterialShapeDrawable mDrawableBody = new MaterialShapeDrawable();
        MaterialShapeDrawable listedBackground = new MaterialShapeDrawable();

        int[][] states = new int[][] {
                new int[] { android.R.attr.state_hovered}, // hovered
        };
        int[] colors = new int[] {menuColor};
        ColorStateList myColorList = new ColorStateList(states, colors);

        int[] colorsTwo = new int[] {ColorProvider.getDarkerShadeColor(menuColor)};
        ColorStateList myColorListTwo = new ColorStateList(states, colorsTwo);

        mDrawableBody.setFillColor(myColorList);
        mDrawableBody.setState(states[0]);
        mDrawableBody.setCornerSize(getBackgroundRadias());
        mDrawableBody.setShadowColor(ColorProvider.getDarkerShadeColor(menuColor));
        mDrawableBody.setShadowCompatibilityMode(MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS);
        mDrawableBody.setShadowVerticalOffset(0);
        mDrawableBody.setShadowBitmapDrawingEnable(true);
        float z = mDrawableBody.getElevation() + mDrawableBody.getTranslationZ();
        mDrawableBody.setElevation(z);

        listedBackground.setFillColor(myColorListTwo);
        listedBackground.setState(states[0]);
        listedBackground.setCornerSize(getBackgroundRadias());
        detailedList.setBackground(listedBackground);

        popupHolder.setBackground(mDrawableBody);

        //add a divider if top button group is visible
        if(buttonActionListTop.getVisibility() == View.VISIBLE) {
            divider.setBackgroundColor(ColorProvider.getTransparentColor(ColorProvider.isDark(menuColor) ? Color.WHITE : Color.BLACK, 2.25));
            divider.setVisibility(View.VISIBLE);
        }

        mArrowDown.setBackground(new FluentArrowDrawable(ARROW_DOWN, menuColor, 1, ColorProvider.getDarkerShadeColor(menuColor)));
        mArrowUp.setBackground(new FluentArrowDrawable(ARROW_UP, menuColor, 1, ColorProvider.getDarkerShadeColor(menuColor)));
    }

    public void setupTopButtonGroup(ArrayList<FluentButtonActionItem> buttonActionList){
        for(FluentButtonActionItem actionItem : buttonActionList){
            Button materialButton = new Button(mWindow.getContentView().getContext(), null, R.style.Widget_MaterialComponents_Button_TextButton);
            materialButton.setTextSize(16);
            materialButton.setTextColor(ColorProvider.isDark(normalColor) ? Color.WHITE : Color.BLACK);
            Typeface font = Typeface.createFromAsset(mContext.getAssets(),"fonts/Inter-Medium.ttf");
            materialButton.setTypeface(font);
            if(buttonActionList.size() < 1){
                materialButton.setPadding(4,0,4,0);
            }else{
                materialButton.setPadding(12,0,12,0);
            }
            materialButton.setText(actionItem.getItemTitle());
            materialButton.setId(actionItem.getItemId());
            materialButton.setOnClickListener(actionItem.getListener());

            if(!actionItem.isItemSticky()){
                materialButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mWindow.dismiss();
                    }
                });
            }
            buttonActionListTop.setDividerDrawable(new ColorDrawable(ColorProvider.isDark(normalColor) ? Color.WHITE : Color.BLACK));
            buttonActionListTop.setShowDividers(LinearLayoutCompat.SHOW_DIVIDER_MIDDLE);
            buttonActionListTop.setClipToPadding(true);
            buttonActionListTop.addView(materialButton);
        }
    }

    public void setupBottomButtonGroup(ArrayList<FluentButtonActionItem> buttonActionList){
        for(FluentButtonActionItem actionItem : buttonActionList){
            Button materialButton = new Button(mWindow.getContentView().getContext(), null, R.style.Widget_MaterialComponents_Button_TextButton);
            materialButton.setTextSize(16);
            materialButton.setTextColor(ColorProvider.isDark(normalColor) ? Color.WHITE : Color.BLACK);
            Typeface font = Typeface.createFromAsset(mContext.getAssets(),"fonts/Inter-Medium.ttf");
            materialButton.setTypeface(font);
            if(buttonActionList.size() < 1){
                materialButton.setPadding(4,0,4,0);
            }else{
                materialButton.setPadding(12,0,12,0);
            }
            materialButton.setText(actionItem.getItemTitle());
            materialButton.setId(actionItem.getItemId());
            materialButton.setOnClickListener(actionItem.getListener());

            if(!actionItem.isItemSticky()){
                materialButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mWindow.dismiss();
                    }
                });
            }
            buttonActionListBottom.setDividerDrawable(new ColorDrawable(ColorProvider.isDark(normalColor) ? Color.WHITE : Color.BLACK));
            buttonActionListBottom.setShowDividers(LinearLayoutCompat.SHOW_DIVIDER_MIDDLE);
            buttonActionListBottom.setClipToPadding(true);
            buttonActionListBottom.addView(materialButton);
        }
    }

    public void setupSimpleList(ArrayList<FluentActionItem> itemList){
        this.simpleItemList = itemList;
        MiniAdapter systemShortcutAdapter = new MiniAdapter(mContext, simpleItemList);
        simpleList.setAdapter(systemShortcutAdapter);
        simpleList.setItemAnimator(new DefaultItemAnimator());
        simpleList.setClipToPadding(true);
    }

    public void setupDetailedList(ArrayList<FluentActionItem> itemList){
        this.detailedItemList = itemList;
        ListAdapterOne listAdapter = new ListAdapterOne(mContext, detailedItemList);
        detailedList.setAdapter(listAdapter);
        detailedList.setItemAnimator(new DefaultItemAnimator());
        detailedList.setClipToPadding(true);
    }

    public RecyclerView getSimpleList(){
        return this.simpleList;
    }

    public RecyclerView getDetailedList(){
        return this.detailedList;
    }

    public void setAllowFadeAnimation(boolean allowFading){
        this.allowFadeAnimation = allowFading;
    }

    public boolean isFadingAllowed(){
        return this.allowFadeAnimation;
    }

    public void setBackgroundRadias(int radias){
        this.backgroundRadias = radias;
    }

    public int getBackgroundRadias(){
        return this.backgroundRadias;
    }

    public void setFadeDuration(int duration){
        this.fadeDuration = duration;
    }

    public int getFadeDuration(){
        return this.fadeDuration;
    }

    public void setListAnimationDuration(int length, int start){
        this.lengthDuration = length;
        this.startDuration = start;
    }

    public int getLengthDuration(){
        return this.lengthDuration;
    }

    public int getStartDuration(){
        return this.startDuration;
    }

    public void setTopActionButtonListVisibility(int hasActionButtons){
        /*
        0 == Visible
        1 == Invisible
        2 == Gone
         */
        buttonActionListTop.setVisibility(hasActionButtons);
    }

    public void setBottomActionButtonListVisibility(int hasActionButtons){
        /*
        0 == Visible
        1 == Invisible
        2 == Gone
         */
        buttonActionListBottom.setVisibility(hasActionButtons);
    }

    public ArrayList<FluentActionItem> getSimpleItemList(){
        return  this.simpleItemList;
    }

    public ArrayList<FluentActionItem> getDetailedItemList(){
        return  this.detailedItemList;
    }

    public FluentPopupMenu(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mRootView = inflater.inflate(R.layout.quick_action_fluent_menu, null);
        popupHolder = mRootView.findViewById(R.id.track);
        simpleList = mRootView.findViewById(R.id.simple_list);
        detailedList = mRootView.findViewById(R.id.detailed_list);
        buttonActionListTop = mRootView.findViewById(R.id.action_button_list_top);
        buttonActionListBottom = mRootView.findViewById(R.id.action_button_list_bottom);
        mArrowDown = mRootView.findViewById(R.id.arrow_down);
        mArrowUp = mRootView.findViewById(R.id.arrow_up);
        divider = mRootView.findViewById(R.id.divider);

        setContentView(mRootView);
    }

    public void show (View anchor) {
        preShow();
        int[] location = new int[2];
        anchor.getLocationOnScreen(location);

        Rect anchorRect = new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1]
                + anchor.getHeight());

        DisplayMetrics displaymetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(displaymetrics);

        mRootView.measure(anchor.getMeasuredWidth(), anchor.getMeasuredHeight());

        int rootWidth = mRootView.getMeasuredWidth();
        int rootHeight = mRootView.getMeasuredHeight();

        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        int arrowPos;
        int xPos;
        if ((anchorRect.left + rootWidth) > screenWidth) {
            xPos = anchorRect.left - (rootWidth - anchor.getWidth());
            xPos = Math.max(xPos, 0);

            arrowPos = anchorRect.centerX() - xPos;
        } else {
            if (anchor.getWidth() > rootWidth) {
                xPos = anchorRect.centerX() - (rootWidth / 2);
            } else {
                xPos = anchorRect.left;
            }

            arrowPos = anchorRect.centerX() - xPos;
        }

        int dyTop = anchorRect.top;
        int dyBottom = screenHeight - anchorRect.bottom;

        boolean onTop = dyTop > dyBottom;

        isArrowOnTop(onTop);

        int yPos;
        if (onTop) {
            if (rootHeight > dyTop) {
                yPos = 15;
                ViewGroup.LayoutParams l = popupHolder.getLayoutParams();
                l.height = dyTop-anchor.getHeight();
            } else {
                yPos = anchorRect.top-rootHeight;
            }
        } else {
            yPos = anchorRect.bottom;

            if (rootHeight > dyBottom) {
                ViewGroup.LayoutParams l = popupHolder.getLayoutParams();
                l.height = dyBottom;
            }
        }

        detailedList.addOnItemTouchListener(new MenuItemTouchListener(mContext, detailedList, new MenuItemTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(!getDetailedItemList().get(position).isItemSticky()){
                    mWindow.dismiss();
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if(!getDetailedItemList().get(position).isItemSticky()){
                    mWindow.dismiss();
                }
            }

            @Override
            public void onSwiped(View view, int position) {
                if(!getDetailedItemList().get(position).isItemSticky()){
                    mWindow.dismiss();
                }
            }
        }));

        simpleList.addOnItemTouchListener(new MenuItemTouchListener(mContext, simpleList, new MenuItemTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(!getSimpleItemList().get(position).isItemSticky()){
                    mWindow.dismiss();
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if(!getSimpleItemList().get(position).isItemSticky()){
                    mWindow.dismiss();
                }
            }

            @Override
            public void onSwiped(View view, int position) {
                if(!getSimpleItemList().get(position).isItemSticky()){
                    mWindow.dismiss();
                }
            }
        }));

        setMenuListAnimation(simpleList);

        mWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        showArrow(((onTop) ? R.id.arrow_down : R.id.arrow_up), arrowPos);
        mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
        setFadeAnimation(popupHolder);
        setFadeAnimation(detailedList);
        setMenuListAnimation(detailedList);
    }

    private void isArrowOnTop(boolean onTop) {
        popupHolder.removeView(simpleList);
        popupHolder.removeView(divider);
        if(!onTop){
            popupHolder.addView(simpleList, 0);
            popupHolder.addView(divider, 1);
        }else{
            popupHolder.addView(divider, 3);
            popupHolder.addView(simpleList, 4);
        }
    }

    private void setMenuListAnimation(RecyclerView recyclerView){
        if(isFadingAllowed()) {
            AnimationSet set = new AnimationSet(true);

            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(getLengthDuration());
            set.addAnimation(animation);

            animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
            );
            animation.setDuration(getStartDuration());
            set.addAnimation(animation);

            LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
            recyclerView.setLayoutAnimation(controller);
        }
    }

    private void setFadeAnimation(View view) {
        if(isFadingAllowed()) {
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(getFadeDuration());
            view.startAnimation(anim);
        }
    }

    private void showArrow(int whichArrow, int requestedX) {
        final View showArrow = (whichArrow == R.id.arrow_up) ? mArrowUp : mArrowDown;
        final View hideArrow = (whichArrow == R.id.arrow_up) ? mArrowDown : mArrowUp;
        final int arrowWidth = mArrowUp.getMeasuredWidth();

        showArrow.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams)
                showArrow.getLayoutParams();

        param.leftMargin = requestedX - arrowWidth / 2;
        hideArrow.setVisibility(View.INVISIBLE);
    }

    public void setOnDismissListener(OnDismissListener listener) {
        setOnDismissListener(this);
        mDismissListener = listener;
    }

    @Override
    public void onDismiss() {
        if (mDismissListener != null) {
            mDismissListener.onDismiss();
        }
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    public class MiniAdapter extends RecyclerView.Adapter<MiniAdapter.MyViewHolder> {

        private final List<FluentActionItem> mDataSet;
        private final Context mContext;

        public MiniAdapter(Context context, List<FluentActionItem> list) {
            this.mContext = context;
            this.mDataSet = list;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position){
            return position;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View itemView = LayoutInflater.from(mContext).inflate(R.layout.quick_action_fluent_item_simple, null);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            Drawable chipDrawable = getIcon(position);
            chipDrawable.setTint(ColorProvider.isDark(normalColor) ? Color.WHITE : Color.BLACK);
            holder.icon.setImageDrawable(chipDrawable);
        }

        private Drawable getIcon(int position){
            return mDataSet.get(position).getIcon();
        }

        @Override
        public int getItemCount() {
            try {
                return mDataSet.size();
            }catch (NullPointerException e){
                return mDataSet == null ? 0 : mDataSet.size();
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            AppCompatImageView icon;

            MyViewHolder(View view) {
                super(view);
                icon = view.findViewById(R.id.icon);
            }
        }
    }

    public class ListAdapterOne extends RecyclerView.Adapter<ListAdapterOne.MyViewHolder> {

        private final List<FluentActionItem> mDataSet;
        private final Context mContext;

        public ListAdapterOne(Context context, List<FluentActionItem> list) {
            this.mContext = context;
            this.mDataSet = list;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position){
            return position;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View itemView = LayoutInflater.from(mContext).inflate(R.layout.quick_action_fluent_item, null);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            Drawable drawableIcon = getIcon(position);
            int listColor = ColorProvider.getDarkerShadeColor(normalColor);

            drawableIcon.setTint(ColorProvider.isDark(listColor) ? Color.WHITE : Color.BLACK);
            holder.icon.setText(getPositionTitle(position));
            holder.icon.setTextColor(ColorProvider.isDark(listColor) ? Color.WHITE : Color.BLACK);
            holder.icon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.icon.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableIcon, null,null,null);
            holder.icon.setCompoundDrawablePadding(8);
        }

        private Drawable getIcon(int position){
            return mDataSet.get(position).getIcon();
        }

        public String getPositionTitle(int position){
            return mDataSet.get(position).getTitle();
        }

        @Override
        public int getItemCount() {
            try {
                return mDataSet.size();
            }catch (NullPointerException e){
                return mDataSet == null ? 0 : mDataSet.size();
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            MaterialTextView icon;

            MyViewHolder(View view) {
                super(view);
                icon = view.findViewById(R.id.icon_text);
            }
        }
    }

}
