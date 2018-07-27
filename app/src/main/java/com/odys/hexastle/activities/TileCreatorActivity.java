package com.odys.hexastle.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.odys.hexastle.R;
import com.odys.hexastle.adapters.TileListAdapter;
import com.odys.hexastle.models.Tile;
import com.odys.hexastle.utils.DragDropHandler;

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
        DragDropHandler dragDropHandler = new DragDropHandler(rootLayout, img);
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

}
