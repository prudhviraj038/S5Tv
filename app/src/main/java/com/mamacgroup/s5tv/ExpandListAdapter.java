package com.mamacgroup.s5tv;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpandListAdapter implements android.widget.ExpandableListAdapter{
    Context context;
ArrayList<MainCategory> mainCategories;
    private static LayoutInflater inflater=null;
    public ExpandListAdapter(Activity mainActivity, ArrayList<MainCategory> mainCategories) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.mainCategories=mainCategories;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver)
    {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getGroupCount() {
        return mainCategories.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mainCategories.get(i).chanels.size();
    }

    @Override
    public Object getGroup(int i) {
        return mainCategories.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mainCategories.get(i).chanels.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return  Long.parseLong(mainCategories.get(i).chanels.get(i1).ch_id);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View rowView;
        rowView = inflater.inflate(R.layout.el_item, null);
        TextView h_name=(TextView)rowView.findViewById(R.id.h_name);
        ImageView h_img=(ImageView)rowView.findViewById(R.id.h_img);
        if(b){
            h_img.setImageResource(R.drawable.down_btn_icon);
        }else{
            h_img.setImageResource(R.drawable.right_btn_icon);
        }
        h_name.setText(mainCategories.get(i).title);
        return rowView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View rowView;
        rowView = inflater.inflate(R.layout.el_item_ch, null);
        TextView c_name=(TextView)rowView.findViewById(R.id.c_name);
        c_name.setText(mainCategories.get(i).chanels.get(i1).ch_title);
        return rowView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int i) {

    }

    @Override
    public void onGroupCollapsed(int i) {

    }

    @Override
    public long getCombinedChildId(long l, long l1) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long l) {
        return 0;
    }
}