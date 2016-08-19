package com.mamacgroup.s5tv;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class NewsListAdapter extends BaseAdapter{
    Context context;
    ArrayList<News> newses;
    private static LayoutInflater inflater=null;
    public NewsListAdapter(Activity mainActivity,  ArrayList<News> newses) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.newses=newses;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return newses.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        NetworkImageView img;
        TextView title;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.news_item, null);
        holder.title=(TextView) rowView.findViewById(R.id.news_tv);
        holder.title.setText(newses.get(position).title);
        holder.img=(NetworkImageView) rowView.findViewById(R.id.news_img);
        Log.e("img", newses.get(position).image);
      //  Picasso.with(context).load("http://image.flaticon.com/teams/1-freepik.jpg").into(holder.img);

    ImageLoader imageLoader = CustomVolleyRequest.getInstance(context)
                .getImageLoader();
        imageLoader.get(newses.get(position).image, ImageLoader.getImageListener(holder.img,
                R.drawable.nwessss, android.R.drawable
                        .ic_dialog_alert));
        holder.img.setImageUrl(newses.get(position).image, imageLoader);
        return rowView;
    }

}