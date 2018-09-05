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

import com.odys.hexastle.R;
import com.odys.hexastle.adapters.TileListAdapter;
import com.odys.hexastle.utils.AppConstants;
import com.odys.hexastle.utils.DragDropHandler;

public class TileCreatorActivity extends AppCompatActivity {

    private ExpandableListView tileListView;
    private DrawerLayout drawer;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_tiles);

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

        ViewGroup rootLayout = findViewById(R.id.viewGroup);
        ImageView img = rootLayout.findViewById(R.id.testImageView);
        img.setX(getWindowManager().getDefaultDisplay().getWidth()/2-85); //hardcoded center
        img.setY(130); //hardcoded center

        DragDropHandler dragDropHandler = new DragDropHandler(rootLayout, img);
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
