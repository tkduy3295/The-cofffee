package com.echessa.designdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.echessa.designdemo.Adapter.CustomFavoriteAdapter;
import com.echessa.designdemo.Adapter.CustomMenuOfCategoriesAdapter;
import com.echessa.designdemo.DBUtils.Favorite;
import com.echessa.designdemo.DBUtils.MenuOfCategories;
import com.echessa.designdemo.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuOfCategoriesFragment extends Fragment {

    GridView gvMenuOfCategories;

    List<MenuOfCategories> menuOfCategoriesList;

    CustomMenuOfCategoriesAdapter customMenuOfCategoriesAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menuofcategories,container,false);




        gvMenuOfCategories = (GridView) view.findViewById(R.id.gvMenuOfCategories);

        menuOfCategoriesList = new ArrayList<MenuOfCategories>();

        /*MenuOfCategories cafeda = new MenuOfCategories(1,"Cappucino Nóng", R.drawable.cafenong,"Cappuchino (hot)",45000);

        MenuOfCategories cafesua = new MenuOfCategories(2,"Espresso Sữa Đá", R.drawable.cafedenda,"Cappuchino, sữa đặc, đá viên, sữa đặc, đá viên",35000);

        MenuOfCategories cafenong = new MenuOfCategories(3,"Cafe sữa đá ", R.drawable.cafesuada,"Cappuchino (hot)",30000);



        MenuOfCategories sinhtodudu = new MenuOfCategories(4,"Sinh tố đu đủ", R.drawable.sinhtodudu,"Cappuchino (hot)",30000);
        MenuOfCategories sinhtobo = new MenuOfCategories(5,"Sinh tố bơ", R.drawable.sinhtobo,"Cappuchino (hot)",30000);
        MenuOfCategories sinhtomangcau = new MenuOfCategories(6,"Sinh tố mãng cầu", R.drawable.sinhtomangcau,"Cappuchino (hot)",30000);

        MenuOfCategories tralipton = new MenuOfCategories(7,"Trà Lipton", R.drawable.tralipton,"Cappuchino (hot)",20000);
        MenuOfCategories tradao = new MenuOfCategories(8,"Trà đào", R.drawable.tradao,"Cappuchino (hot)",35000);


        MenuOfCategories nuixao = new MenuOfCategories(9,"Nui xào", R.drawable.nuixao,"Cappuchino (hot)",20000);
        MenuOfCategories mixaotrung = new MenuOfCategories(10,"Mì xào trứng", R.drawable.mixaotrung,"Cappuchino (hot)",35000);
        menuOfCategoriesList.add(cafeda);
        menuOfCategoriesList.add(cafesua);
        menuOfCategoriesList.add(cafenong);

        menuOfCategoriesList.add(sinhtodudu);
        menuOfCategoriesList.add(sinhtobo);
        menuOfCategoriesList.add(sinhtomangcau);



        menuOfCategoriesList.add(tralipton);
        menuOfCategoriesList.add(tradao);

        menuOfCategoriesList.add(nuixao);
        menuOfCategoriesList.add(mixaotrung);*/

        customMenuOfCategoriesAdapter = new CustomMenuOfCategoriesAdapter(view.getContext(), menuOfCategoriesList);

        gvMenuOfCategories.setAdapter(customMenuOfCategoriesAdapter);

        gvMenuOfCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(),menuOfCategoriesList.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

}
