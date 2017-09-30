package com.echessa.designdemo.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.echessa.designdemo.DBUtils.Menu;
import com.echessa.designdemo.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by sung on 30/08/2017.
 */

public class CustomMenuAdapter extends BaseAdapter  {

    private Context context;

    private LayoutInflater layoutInflater;

    private List<Menu> menuList;



    public CustomMenuAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
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

            view = layoutInflater.inflate(R.layout.layout_gridview_menu,null);

            viewHolder.imageMenu = (ImageView) view.findViewById(R.id.imageMenu);

            viewHolder.tvNameMenu = (TextView) view.findViewById(R.id.tvNameMenu);

            viewHolder.tvDescriptionMenu = (TextView) view.findViewById(R.id.tvDescriptionMenu);

            viewHolder.tvQualityMenu = (TextView) view.findViewById(R.id.tvQualityMenu);

            viewHolder.tvPriceMenu = (TextView) view.findViewById(R.id.tvPriceMenu);

            viewHolder.tvTotalMenu = (TextView) view.findViewById(R.id.tvTotalMenu);

            view.setTag(viewHolder);

        }else{

            viewHolder =(ViewHolder) view.getTag();

        }

        final Menu menu = (Menu) menuList.get(position);
        Typeface mFont = Typeface.createFromAsset(context.getAssets(),"Roboto-Bold.ttf");

        Picasso.with(context).load(menu.getUrlImage()).into(viewHolder.imageMenu);

        viewHolder.tvNameMenu.setText(menu.getName());
        viewHolder.tvNameMenu.setTypeface(mFont);

        viewHolder.tvDescriptionMenu.setText(menu.getDescription());




        viewHolder.tvTotalMenu.setText(""+ menu.getTotalOrder());

        viewHolder.tvQualityMenu.setText(""+ menu.getQuatity());



        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(menu.getPrice());

        viewHolder.tvPriceMenu.setText(""+price+" đ");
        /*viewHolder.tvPriceMenu.setTypeface(mFont);*/






        return view;
    }

    private class ViewHolder{
        ImageView imageMenu;
        TextView tvNameMenu;
        TextView tvDescriptionMenu;
        TextView tvQualityMenu;
        TextView tvPriceMenu;
        TextView tvTotalMenu;


    }
}
