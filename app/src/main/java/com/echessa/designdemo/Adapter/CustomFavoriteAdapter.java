package com.echessa.designdemo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.echessa.designdemo.DBUtils.Favorite;
import com.echessa.designdemo.DBUtils.Ordered;
import com.echessa.designdemo.MainActivity;
import com.echessa.designdemo.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by sung on 30/08/2017.
 */

public class CustomFavoriteAdapter extends BaseAdapter  {

    private Context context;

    private LayoutInflater layoutInflater;

    private List<Favorite> favoriteList;

    /*private int quatityFavorite = 0;*/



    public CustomFavoriteAdapter(Context context, List<Favorite> favoriteList) {
        this.context = context;
        this.favoriteList = favoriteList;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return favoriteList.size();
    }

    @Override
    public Object getItem(int position) {
        return favoriteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder viewHolder;

        if(view == null){

            viewHolder = new ViewHolder();

            view = layoutInflater.inflate(R.layout.layout_gridview_favorite,null);

            viewHolder.imageFavorite = (ImageView) view.findViewById(R.id.imageFavorite);

            viewHolder.tvNameFavorite = (TextView) view.findViewById(R.id.tvNameFavorite);

            viewHolder.tvQualityFavorite = (TextView) view.findViewById(R.id.tvQualityFavorite);

            viewHolder.tvPriceFavorite = (TextView) view.findViewById(R.id.tvPriceFavorite);

            viewHolder.tvTotalFavorite = (TextView) view.findViewById(R.id.tvTotalFavorite);

            view.setTag(viewHolder);

        }else{

            viewHolder =(ViewHolder) view.getTag();

        }

        final Favorite favorite = (Favorite) favoriteList.get(position);
        Typeface mFont = Typeface.createFromAsset(context.getAssets(),"Roboto-Bold.ttf");

        Picasso.with(context).load(favorite.getUrlImage()).into(viewHolder.imageFavorite);

        viewHolder.tvNameFavorite.setText(favorite.getName());
        viewHolder.tvNameFavorite.setTypeface(mFont);




        viewHolder.tvTotalFavorite.setText(""+favorite.getTotalOrder());

        viewHolder.tvQualityFavorite.setText(""+favorite.getQuatity());



        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(favorite.getPrice());

        viewHolder.tvPriceFavorite.setText(""+price+" đ");
        /*viewHolder.tvPriceFavorite.setTypeface(mFont);*/






        return view;
    }

    private class ViewHolder{
        ImageView imageFavorite;
        TextView tvNameFavorite;
        TextView tvQualityFavorite;
        TextView tvPriceFavorite;
        TextView tvTotalFavorite;


    }
}
