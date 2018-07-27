package com.odys.hexastle.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.odys.hexastle.R;
import com.odys.hexastle.adapters.TileListAdapter;
import com.odys.hexastle.models.Tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TileCreatorActivity extends AppCompatActivity {

    private ExpandableListView tileListView;
    private List<String> categories;
    private HashMap<String, List<Tile>> tileCategoryMap;

    private DrawerLayout drawer;

    private ImageView img;
    private ViewGroup rootLayout;
    private int _xDelta;
    private int _yDelta;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_tiles);

        tileListView = findViewById(R.id.tileList);
        initData();
        ExpandableListAdapter tileListAdapter = new TileListAdapter(this, categories, tileCategoryMap);
        tileListView.setAdapter(tileListAdapter);
        tileListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int i) {
                if(i != previousGroup) {
                    tileListView.collapseGroup(previousGroup);
                    previousGroup = i;
                }
            }
        });

        drawer = findViewById(R.id.tileNavigationLayout);
        FloatingActionButton addButton = findViewById(R.id.addButton);
        
        ObjectAnimator pushValueAnimator = ObjectAnimator.ofFloat(addButton, "alpha", 1.0f, 0.2f);
        pushValueAnimator.setDuration(100);
        pushValueAnimator.setRepeatCount(0);

        ObjectAnimator releaseValueAnimator = ObjectAnimator.ofFloat(addButton, "alpha", 0.2f, 1.0f);
        releaseValueAnimator.setDuration(100);
        releaseValueAnimator.setRepeatCount(0);


        addButton.setOnTouchListener((view, motionEvent) -> {
            switch(motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pushValueAnimator.start();
                    return true;
                case MotionEvent.ACTION_UP:
                    releaseValueAnimator.start();
                    drawer.openDrawer(GravityCompat.START);
                    return true;
            }
            return false;
        });

        rootLayout = findViewById(R.id.viewGroup);
        img =  rootLayout.findViewById(R.id.testImageView);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
        img.setLayoutParams(layoutParams);
        img.setOnTouchListener(new ChoiceTouchListener());
    }

    private void initData() {
        categories = new ArrayList<>();
        tileCategoryMap = new HashMap<>();
        AssetManager am = this.getAssets();
        String[] files;

        for(String s : getResources().getStringArray(R.array.categories)) {
            categories.add(s);
//            try {
                List<Tile> category = new ArrayList<>();
//                files = am.list(s);
//                ArrayList<Drawable> drawables = new ArrayList<>();
//
//                for (String file : files) {
//                    int resID = getResources().getIdentifier(file , "drawable", getPackageName());
//                    category.add(new Tile(resID));
//                }

                tileCategoryMap.put(s, category);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }


//        List<Tile> dachy = new ArrayList<>();
//        Tile tile = new Tile(R.drawable.ic_info);
//        dachy.add(tile);
//        dachy.add(tile);
//        dachy.add(tile);
//
//        tileCategoryMap.put(categories.get(0), dachy);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private final class ChoiceTouchListener implements View.OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
        public boolean onTouch(View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    Log.d("ACTION_DOWN", "nana");
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    img.setAlpha(0.5f);
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d("ACTION_UP", "nana");
                    img.setAlpha(1.0f);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    Log.d("ACTION_POINTER_DOWN", "nana");
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    Log.d("ACTION_POINTER_UP", "nana");
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d("ACTION_MOVE", "nana");
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            rootLayout.invalidate();
            return true;
        }
    }
}
