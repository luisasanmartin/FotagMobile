package com.example.luisasanmartin.fotagmobile;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Luisa on 2016-03-22.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ImageCollectionModel model;

    public ImageAdapter(Context context, ImageCollectionModel icm) {
        this.mContext = context;
        this.model = icm;
    }


    public int getCount() {
        return model.getImageCount();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.image_activity, null);
//        if (view == null) {
//            view.setLayoutParams(new GridView.LayoutParams(300, 200));
//            view.setPadding(8, 8, 8, 8);
//        }
//        return view;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        final int pos = position;
        System.out.println("position: " + position);
        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.image_activity, parent, false);
            holder = new ViewHolder();
            holder.imageRating = (RatingBar) row.findViewById(R.id.ratingBar);
            holder.imageRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                    if (fromUser) {
                        System.out.println("rating changed to: " + rating);
                        model.getImageModel(pos).setRating((int)rating);
                    }

                }
            });
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.image.setImageBitmap(model.getImage(position));
        holder.imageRating.setRating(model.getImageModel(position).getRating());
        return row;
    }

    static class ViewHolder {
        RatingBar imageRating;
        ImageView image;
    }

}
