package com.odys.hexastle.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.odys.hexastle.R;
import com.odys.hexastle.models.Tile;

import java.util.HashMap;
import java.util.List;

public class TileListAdapter extends BaseExpandableListAdapter{
    private Context context;
    private List<String> categories;
    private HashMap<String, List<Tile>> tileCategoryMap;

    public TileListAdapter(Context context, List<String> categories, HashMap<String, List<Tile>> tileCategoryMap) {
        this.context = context;
        this.categories = categories;
        this.tileCategoryMap = tileCategoryMap;
    }

    @Override
    public int getGroupCount() {
        return categories.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return tileCategoryMap.get(categories.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return categories.get(i);
    }

    @Override
    public Object getChild(int groupId, int childId) {
        return tileCategoryMap.get(categories.get(groupId)).get(childId);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int groupId, int childId) {
        return childId;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_category, null);
        }

        String categoryName = (String) getGroup(i);
        TextView categoryTextView = view.findViewById(R.id.categoryTextView);
        categoryTextView.setTypeface(null, Typeface.BOLD);
        categoryTextView.setText(categoryName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            categoryTextView.setTextAppearance(R.style.hexastle_text);
        }

        TextView counter = view.findViewById(R.id.counterTextView);
        counter.setText(getChildrenCount(i)+"");

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final Drawable childImage = tileCategoryMap.get(categories.get(i)).get(i1).getImageResource();
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_subcategory, null);
        }

        ImageView tileImageView = view.findViewById(R.id.tileImageView);
        tileImageView.setImageDrawable(childImage);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
