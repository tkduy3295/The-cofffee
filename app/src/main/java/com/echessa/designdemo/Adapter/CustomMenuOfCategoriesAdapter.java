package com.echessa.designdemo.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.echessa.designdemo.DBUtils.Favorite;
import com.echessa.designdemo.DBUtils.MenuOfCategories;
import com.echessa.designdemo.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by sung on 30/08/2017.
 */

public class CustomMenuOfCategoriesAdapter extends BaseAdapter {

    private Context context;

    private LayoutInflater layoutInflater;

    private List<MenuOfCategories> menuOfCategoriesList;

    public CustomMenuOfCategoriesAdapter(Context context, List<MenuOfCategories> menuOfCategoriesList) {
        this.context = context;
        this.menuOfCategoriesList = menuOfCategoriesList;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return menuOfCategoriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuOfCategoriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        if(view == null){

            viewHolder = new ViewHolder();

            view = layoutInflater.inflate(R.layout.layout_gridview_menuofcategories,null);

            viewHolder.imageMenuOfCategories = (ImageView) view.findViewById(R.id.imageMenuOfCategories);

            viewHolder.tvNameMenuOfCategories = (TextView) view.findViewById(R.id.tvNameMenuOfCategories);

            viewHolder.tvDescriptionMenuOfCategories = (TextView) view.findViewById(R.id.tvDescriptionMenuOfCategories);

            viewHolder.tvPriceMenuOfCategories = (TextView) view.findViewById(R.id.tvPriceMenuOfCategories);

//            viewHolder.btnOrderMenuOfCategories = (Button) view.findViewById(R.id.btnOrderMenuOfCategories);

            view.setTag(viewHolder);

        }else{
            viewHolder =(ViewHolder) view.getTag();
        }

        final MenuOfCategories menuOfCategories = (MenuOfCategories) menuOfCategoriesList.get(position);
        Typeface mFont = Typeface.createFromAsset(context.getAssets(),"Roboto-Bold.ttf");

        viewHolder.imageMenuOfCategories.setImageResource(menuOfCategories.getImage());

        viewHolder.tvNameMenuOfCategories.setText(menuOfCategories.getName());
        viewHolder.tvNameMenuOfCategories.setTypeface(mFont);


        viewHolder.tvDescriptionMenuOfCategories.setText(menuOfCategories.getDescription());

        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(menuOfCategories.getPrice());

        viewHolder.tvPriceMenuOfCategories.setText(""+price+" đ");

//        viewHolder.btnOrderMenuOfCategories.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(),""+menuOfCategories.getName(),Toast.LENGTH_SHORT).show();
//            }
//        });



        return view;
    }

    private class ViewHolder{
        ImageView imageMenuOfCategories;
        TextView tvNameMenuOfCategories;
        TextView tvDescriptionMenuOfCategories;
        TextView tvPriceMenuOfCategories;
//        Button btnOrderMenuOfCategories;
    }
}
