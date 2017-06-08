package com.developers.marvelous.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developers.marvelous.R;

import java.util.ArrayList;

/**
 * Created by Amanjeet Singh on 08-Jun-17.
 */

public class ComicListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> comicList;
    private TextView comicNames;

    public ComicListAdapter(Context context, ArrayList comicList){
        this.comicList=comicList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return comicList.size();
    }

    @Override
    public Object getItem(int i) {
        return comicList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.comic_list_row,null);
        comicNames= (TextView) view.findViewById(R.id.comic_title);
        comicNames.setText(comicList.get(i));
        return view;
    }
}
