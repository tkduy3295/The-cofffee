package com.echessa.designdemo.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.echessa.designdemo.DBUtils.Categories;
import com.echessa.designdemo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sung on 29/08/2017.
 */

public class CustomCategoriesAdapter extends  BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Categories> categoriesList;

    public CustomCategoriesAdapter(Context context, List<Categories> categoriesList) {

        this.context = context;

        this.categoriesList = categoriesList;

        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return categoriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.layout_listview_categories,null);

            viewHolder = new ViewHolder();

            viewHolder.imageCategories = (ImageView) convertView.findViewById(R.id.imageCategories);

            viewHolder.tvNameCategories = (TextView) convertView.findViewById(R.id.tvNameCategories);

            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Categories categories = this.categoriesList.get(position);
        Typeface mFont = Typeface.createFromAsset(context.getAssets(),"Roboto-Bold.ttf");

        Picasso.with(context).load(categories.getUrlImage()).into(viewHolder.imageCategories);

        viewHolder.tvNameCategories.setText(categories.getName());
        viewHolder.tvNameCategories.setTypeface(mFont);


        return convertView;
    }



    static class ViewHolder extends FragmentActivity {
        ImageView imageCategories;
        TextView tvNameCategories;
    }





}
