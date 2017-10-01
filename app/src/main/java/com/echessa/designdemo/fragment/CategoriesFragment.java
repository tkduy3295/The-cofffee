package com.echessa.designdemo.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.echessa.designdemo.DBUtils.Categories;
import com.echessa.designdemo.Adapter.CustomCategoriesAdapter;
import com.echessa.designdemo.R;

import org.json.JSONArray;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    private ListView lvCategories;

    private List<Categories> categoriesList;

    private CustomCategoriesAdapter categoriesAdapter;

    String url ="https://cappuccino-hello.herokuapp.com/api/menu/category/";

    private ArrayList<String> listMenuOfCategory;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_categories, container, false);


        lvCategories = (ListView) view.findViewById(R.id.lvCategories);

        categoriesList = new ArrayList<Categories>();



        listMenuOfCategory = new ArrayList<String>();

            RequestQueue queue = Volley.newRequestQueue(view.getContext());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("response");
                    for (int i = 0; i<jsonArray.length();i++){
                        JSONObject categoriesItem = jsonArray.getJSONObject(i);
                        String id = categoriesItem.getString("id");
                        String name = categoriesItem.getString("name");
                        int createAt = categoriesItem.getInt("createAt");
                        String urlImage = categoriesItem.getString("urlImage");
                        JSONArray items = categoriesItem.getJSONArray("items");
                        for (int k =0; k<items.length();k++){
                            listMenuOfCategory.add((String) items.get(k));
                        }


                        Categories categories = new Categories(id,name,createAt,urlImage,listMenuOfCategory);
                        categoriesList.add(categories);
                    }

                    categoriesAdapter = new CustomCategoriesAdapter(getActivity(),categoriesList);

                    lvCategories.setAdapter(categoriesAdapter);

                    lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {

                            MenuFragment menuFragment = new MenuFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("idCategory", categoriesList.get(position).getId());
                            menuFragment.setArguments(bundle);


                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_root,menuFragment).commit();

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    toast(error.toString());
                }
            });

            queue.add(jsonObjectRequest);




        return view;
    }

    public void toast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
    }



}
