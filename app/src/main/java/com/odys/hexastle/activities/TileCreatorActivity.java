package com.odys.hexastle.activities;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.odys.hexastle.R;
import com.odys.hexastle.adapters.TileListAdapter;
import com.odys.hexastle.utils.AppConstants;
import com.odys.hexastle.utils.DragDropHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TileCreatorActivity extends AppCompatActivity {

    private ExpandableListView tileListView;
    private ViewGroup rootLayout;
    private DrawerLayout drawer;

    private float SCALE_BIG = 4.0f;
    private float SCALE_OTHER1 = 3.05f;
    private float SCALE_OTHER2 = 4.15f;
    private float SCALE_OTHER3 = 3.42f;
    private float SCALE_SMALL = 0.5f;
    private ArrayList bigCategories = new ArrayList(){{
        add("basic");
        add("ground");
        add("trees");
        add("buildings");
        add("custom");
    }};

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_tiles);

        rootLayout = findViewById(R.id.viewGroup);
        ImageView dropZone = findViewById(R.id.dropZone);
        ImageView bin = findViewById(R.id.binImageView);

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
            if (picked.getTag() != null) {
                if (picked.getTag().toString().contains("nature_extra")) {
                    if (!picked.getTag().toString().contains("rockSmall")) {
                        if (picked.getTag().toString().contains("rock")) {
                            newTile.setScaleX(SCALE_BIG);
                            newTile.setScaleY(SCALE_BIG);
                        } else {
                            newTile.setScaleX(SCALE_SMALL);
                            newTile.setScaleY(SCALE_SMALL);
                        }
                    }
                } else if (picked.getTag().toString().contains("basic")
                        || picked.getTag().toString().contains("ground")
                        || picked.getTag().toString().contains("buildings")
                        || picked.getTag().toString().contains("trees")
                        || picked.getTag().toString().contains("custom")) {
                    newTile.setScaleX(SCALE_BIG);
                    newTile.setScaleY(SCALE_BIG);
                } else if (picked.getTag().toString().contains("roofs")) {
                    if (picked.getTag().toString().contains("Ring")) {
                        newTile.setScaleX(SCALE_OTHER3);
                        newTile.setScaleY(SCALE_OTHER3);
                    } else if(picked.getTag().toString().contains("bridge")) {
                        newTile.setScaleX(SCALE_BIG);
                        newTile.setScaleY(SCALE_BIG);
                    } else if (picked.getTag().toString().contains("Pointy") || picked.getTag().toString().contains("Straight") || picked.getTag().toString().contains("Tall")) {
                        newTile.setScaleX(SCALE_OTHER2);
                        newTile.setScaleY(SCALE_OTHER2);
                    } else {
                        newTile.setScaleX(SCALE_OTHER1);
                        newTile.setScaleY(SCALE_OTHER1);
                    }
                }
            }
            rootLayout.addView(newTile);

            int[] screenLocation = new int[2];
            dropZone.getLocationOnScreen(screenLocation);
            int height = dropZone.getHeight();
            int width = dropZone.getWidth();
            Random r = new Random();
            float x = -1;
            float y = -1;
            while (x<0) {
                x = screenLocation[0] + r.nextFloat() * (width - 200 - screenLocation[0]);
            }
            while (y<0) {
                y = screenLocation[1] + r.nextFloat() * (height - 300 - screenLocation[1]);
            }
            newTile.setX(x);
            newTile.setY(y);
            new DragDropHandler(rootLayout, newTile, bin.getX(), bin.getY());
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
            exitAlertDialog();
        }
    }

    private void exitAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setPositiveButton(R.string.save, (dialogInterface, i) -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                saveCreatedTile();
            } else Toast.makeText(TileCreatorActivity.this, R.string.no_permission, Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        });
        alertDialog.setNeutralButton(R.string.discard, (dialogInterface, i) -> super.onBackPressed());
        alertDialog.setMessage(R.string.save_or_discard);
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.show();
    }

    private void saveCreatedTile() {
        String mTempDir = Environment.getExternalStorageDirectory()+"/"+"TestTemp";
        File mtempFile = new File(mTempDir);
        if(!mtempFile.exists()) mtempFile.mkdir();

        String mSaveImageName = "Test.png";
        Bitmap mBackGround = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);

        for (int i=0; i<rootLayout.getChildCount(); i++) {
            ImageView image = (ImageView) rootLayout.getChildAt(i);
            image.buildDrawingCache();
            Bitmap mTopImage = image.getDrawingCache();
            Canvas mCanvas = new Canvas(mBackGround);
            mCanvas.drawBitmap(mTopImage, 0f, 0f, null);
        }

        try {
            BitmapDrawable mBitmapDrawable = new BitmapDrawable(mBackGround);
            Bitmap mNewSaving = mBitmapDrawable.getBitmap();
            String ftoSave = mTempDir +"/"+ mSaveImageName;
            File mFile = new File(ftoSave);
            FileOutputStream mFileOutputStream = new FileOutputStream(mFile);
            mNewSaving.compress(Bitmap.CompressFormat.PNG, 100 , mFileOutputStream);
            mFileOutputStream.flush();
            mFileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("TAG", "Image Created");
    }

}