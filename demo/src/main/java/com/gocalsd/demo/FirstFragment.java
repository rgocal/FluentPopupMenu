package com.gocalsd.demo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.GocalSD.fluentPopupMenu.FluentActionItem;
import com.GocalSD.fluentPopupMenu.FluentButtonActionItem;
import com.GocalSD.fluentPopupMenu.FluentPopupMenu;
import com.GocalSD.fluentPopupMenu.MenuItemTouchListener;
import com.gocalsd.demo.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.Random;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    public static FluentPopupMenu fluentPopupMenu;
    private int color;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);

        Context mContext = container.getContext();

        fluentPopupMenu = new FluentPopupMenu(mContext);

        fluentPopupMenu.setBackgroundRadias(22);
        fluentPopupMenu.setAllowFadeAnimation(true);
        fluentPopupMenu.setFadeDuration(400);
        fluentPopupMenu.setListAnimationDuration(500, 100);

        ArrayList<FluentActionItem> simpleList = new ArrayList<>();
        simpleList.add(new FluentActionItem(1, "ADD", ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_add_24), true));
        simpleList.add(new FluentActionItem(2, "SAVE", ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_save_alt_24), false));
        simpleList.add(new FluentActionItem(3, "SHARE", ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_share_24), false));

        fluentPopupMenu.setupSimpleList(simpleList);
        fluentPopupMenu.getSimpleList().addOnItemTouchListener(new MenuItemTouchListener(mContext, fluentPopupMenu.getSimpleList(), new MenuItemTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(view.getContext(), "Position clicked : " + simpleList.get(position).getActionId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(view.getContext(), simpleList.get(position).getTitle(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSwiped(View view, int position) {

            }
        }));

        ArrayList<FluentButtonActionItem> topButtonList = new ArrayList<>();
        topButtonList.add(new FluentButtonActionItem(1, "Drive D", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Action One Clicked", Toast.LENGTH_SHORT).show();
            }
        }));
        topButtonList.add(new FluentButtonActionItem(2, "Drive E", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Action Two Clicked", Toast.LENGTH_SHORT).show();
            }
        }));
        fluentPopupMenu.setTopActionButtonListVisibility(View.VISIBLE);
        fluentPopupMenu.setupTopButtonGroup(topButtonList);

        ArrayList<FluentButtonActionItem> bottomButtonList = new ArrayList<>();
        bottomButtonList.add(new FluentButtonActionItem(1, "Action", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Action Clicked", Toast.LENGTH_SHORT).show();
            }
        }));
        fluentPopupMenu.setBottomActionButtonListVisibility(View.VISIBLE);
        fluentPopupMenu.setupBottomButtonGroup(bottomButtonList);

        ArrayList<FluentActionItem> detailedList = new ArrayList<>();
        detailedList.add(new FluentActionItem(1, "Add", ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_add_24), true));
        detailedList.add(new FluentActionItem(3, "Save", ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_save_alt_24), false));
        detailedList.add(new FluentActionItem(4, "Search", ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_search_24), false));
        detailedList.add(new FluentActionItem(2, "Share", ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_share_24), false));


        fluentPopupMenu.setupDetailedList(detailedList);
        fluentPopupMenu.getDetailedList().addOnItemTouchListener(new MenuItemTouchListener(mContext, fluentPopupMenu.getDetailedList(), new MenuItemTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(view.getContext(), "Position clicked : " + detailedList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(view.getContext(), "Position long clicked : " + detailedList.get(position).getTitle(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSwiped(View view, int position) {

            }
        }));

        fluentPopupMenu.setOnDismissListener(new FluentPopupMenu.OnDismissListener() {
            @Override
            public void onDismiss() {
                Toast.makeText(mContext, "Isn't this a cool menu? Dismissed...", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rnd = new Random();
                color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                fluentPopupMenu.setMenuColor(color);
                fluentPopupMenu.show(view);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}