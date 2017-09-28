package com.echessa.designdemo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.echessa.designdemo.DBUtils.Favorite;
import com.echessa.designdemo.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
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

            viewHolder.btnQuatityFavorite = (Button) view.findViewById(R.id.btnQuatityFavorite);

            viewHolder.tvPriceFavorite = (TextView) view.findViewById(R.id.tvPriceFavorite);

            viewHolder.tvTotalFavorite = (TextView) view.findViewById(R.id.tvTotalFavorite);

            /*viewHolder.btnSubFavorite = (Button) view.findViewById(R.id.btnSubFavorite);*/

            /*viewHolder.btnQuatityFavorite = (Button) view.findViewById(R.id.btnQuatityFavorite);*/

            /*viewHolder.btnPlusFavorite = (Button) view.findViewById(R.id.btnPlusFavorite);*/

            view.setTag(viewHolder);

        }else{

            viewHolder =(ViewHolder) view.getTag();

        }

        final Favorite favorite = (Favorite) favoriteList.get(position);
        Typeface mFont = Typeface.createFromAsset(context.getAssets(),"Roboto-Bold.ttf");

        Picasso.with(context).load(favorite.getUrlImage()).into(viewHolder.imageFavorite);

        viewHolder.tvNameFavorite.setText(favorite.getName());
        viewHolder.tvNameFavorite.setTypeface(mFont);


        viewHolder.btnQuatityFavorite.setText(""+0);

        viewHolder.tvTotalFavorite.setText(""+favorite.getTotalOrder());




        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(favorite.getPrice());

        viewHolder.tvPriceFavorite.setText(""+price+" đ");
        /*viewHolder.tvPriceFavorite.setTypeface(mFont);*/

        /*viewHolder.btnQuatityFavorite.setText(""+quatityFavorite);*/








        /*viewHolder.btnPlusFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quatityFavorite = Integer.parseInt(viewHolder.btnQuatityFavorite.getText().toString())+1;
                Toast.makeText(v.getContext(),""+quatityFavorite,Toast.LENGTH_SHORT).show();
                viewHolder.btnQuatityFavorite.setText(""+quatityFavorite);
                viewHolder.btnQuatityFavorite.setVisibility(View.VISIBLE);
                viewHolder.btnSubFavorite.setVisibility(View.VISIBLE);




                viewHolder.btnSubFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quatityFavorite = Integer.parseInt(viewHolder.btnQuatityFavorite.getText().toString())-1;
                        Toast.makeText(v.getContext(),""+quatityFavorite,Toast.LENGTH_SHORT).show();
                        if(quatityFavorite == 0){
                            viewHolder.btnQuatityFavorite.setVisibility(View.INVISIBLE);
                            viewHolder.btnSubFavorite.setVisibility(View.INVISIBLE);
                            viewHolder.btnQuatityFavorite.setText(""+0);

                        }else{
                            viewHolder.btnQuatityFavorite.setText(""+quatityFavorite);
                        }
                    }
                });
            }
        });*/



        return view;
    }

    private class ViewHolder{
        ImageView imageFavorite;
        TextView tvNameFavorite;
        /*TextView tvDescriptionFavorite;*/
        Button btnQuatityFavorite;
        TextView tvPriceFavorite;

        TextView tvTotalFavorite;

        /*Button btnSubFavorite;
        Button btnQuatityFavorite;
        Button btnPlusFavorite;*/

    }
}
