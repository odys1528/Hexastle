package com.odys.hexastle.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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
import com.odys.hexastle.utils.AppConstants;
import com.odys.hexastle.utils.DragDropHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TileCreatorActivity extends AppCompatActivity {

    private ExpandableListView tileListView;
    private List<String> categories;
    private HashMap<String, List<Tile>> tileCategoryMap;

    private DrawerLayout drawer;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_tiles);

        ViewGroup rootLayout = findViewById(R.id.viewGroup);
        ImageView dropZone = findViewById(R.id.dropZone);

        tileListView = findViewById(R.id.tileList);
        ExpandableListAdapter tileListAdapter = new TileListAdapter(this,
                AppConstants.Companion.getCategories(), AppConstants.Companion.getTileMap());
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
        tileListView.setOnChildClickListener((expandableListView, view, group, child, id) -> {
            ImageView picked = view.findViewById(R.id.tileImageView);
            ImageView newTile = new ImageView(this);
            newTile.setImageDrawable(picked.getDrawable());
            newTile.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            rootLayout.addView(newTile);

            int[] screenLocation = new int[2];
            dropZone.getLocationOnScreen(screenLocation);
            int height = dropZone.getHeight();
            int width = dropZone.getWidth();
            Random r = new Random();
            float x = -1;
            float y = -1;
            while (x<0) {
                x = screenLocation[0] + r.nextFloat() * (width - 100 - screenLocation[0]);
            }
            while (y<0) {
                y = screenLocation[1] + r.nextFloat() * (height - 150 - screenLocation[1]);
            }
            newTile.setX(x);
            newTile.setY(y);
            new DragDropHandler(rootLayout, newTile);
            return false;
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