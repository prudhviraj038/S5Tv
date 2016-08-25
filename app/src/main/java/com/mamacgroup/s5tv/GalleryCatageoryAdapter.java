package com.mamacgroup.s5tv;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class GalleryCatageoryAdapter extends BaseAdapter{
    Context context;
ArrayList<GalleryCategory> galleryCategories;
    private static LayoutInflater inflater=null;
    public GalleryCatageoryAdapter(Activity mainActivity, ArrayList<GalleryCategory> galleryCategories) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.galleryCategories=galleryCategories;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return galleryCategories.size();
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
        rowView = inflater.inflate(R.layout.gallery_cat_item, null);
        holder.title=(TextView) rowView.findViewById(R.id.news_tv);
        holder.title.setText(Html.fromHtml(galleryCategories.get(position).title));
        holder.img=(NetworkImageView) rowView.findViewById(R.id.news_img);
        Log.e("img", galleryCategories.get(position).image);
       // Picasso.with(context).load("http://image.flaticon.com/teams/1-freepik.jpg").into(holder.img);

    ImageLoader imageLoader = CustomVolleyRequest.getInstance(context)
                .getImageLoader();
        imageLoader.get(galleryCategories.get(position).image, ImageLoader.getImageListener(holder.img,
                R.drawable.logo, R.drawable
                        .logo));
        holder.img.setImageUrl(galleryCategories.get(position).image, imageLoader);

        return rowView;
    }

}