package com.odys.hexastle.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.TypedValue;

import com.odys.hexastle.R;
import com.odys.hexastle.models.Tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TileDataLoader {

    private Intent intent;
    private Context context;
    private ProgressDialog bar;

    public TileDataLoader(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    public void init() {
        new TileLoader().execute();
    }

    private class TileLoader extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            bar = new ProgressDialog(context);
            bar.setCancelable(false);

            bar.setMessage(context.getString(R.string.loading_text));

            bar.setIndeterminate(true);
            bar.setCanceledOnTouchOutside(false);
            bar.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            bar.dismiss();

            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            context.startActivity(intent);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            loadCategories();
            loadMap();
            return null;
        }
    }

    private void loadMap() {
        HashMap<String, List<Tile>> tileCategoryMap = new HashMap<>();
        AssetManager am = context.getAssets();
        List<Tile> category = new ArrayList<>();
        String[] files;

        for(String s : context.getResources().getStringArray(R.array.categories)) {
            try {
                category = new ArrayList<>();
                files = am.list(s);

                for (String file : files) {
                    Drawable drawable = Drawable.createFromResourceStream(context.getResources(),
                            new TypedValue(), context.getAssets().open(s+"/"+file), null);
                    category.add(new Tile(drawable));
                }

                tileCategoryMap.put(s, category);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        AppConstants.Companion.setTileMap(tileCategoryMap);
    }

    private void loadCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for(String s : context.getResources().getStringArray(R.array.categories)) {
            categories.add(s);
        }
        AppConstants.Companion.setCategories(categories);
    }
}
