package com.example.group_0571.gamecentre.mvcGameModel;

/*
Taken from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/CustomAdapter.java

This Class is an overwrite of the Base Adapter class
It is designed to aid setting the button sizes and positions in the GridView
 */

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private List<View> mViews;
    private int mColumnWidth, mColumnHeight;

    public CustomAdapter(List<View> buttons, int columnWidth, int columnHeight) {
        mViews = buttons;
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public Object getItem(int position) {
        return mViews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = mViews.get(position);
        } else {
            view = convertView;
        }

        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        view.setLayoutParams(params);

        return view;
    }
}
