package com.odys.hexastle.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.odys.hexastle.R;

public class TileCreatorActivity extends AppCompatActivity {

    private ImageView img;
    private ViewGroup rootLayout;
    private int _xDelta;
    private int _yDelta;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_creator);
        rootLayout = findViewById(R.id.viewGroup);
        img =  rootLayout.findViewById(R.id.testImageView);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
        img.setLayoutParams(layoutParams);
        img.setOnTouchListener(new ChoiceTouchListener());
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
