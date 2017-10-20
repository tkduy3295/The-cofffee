package com.echessa.designdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.echessa.designdemo.DBUtils.Category;
import com.echessa.designdemo.Adapter.CustomCategoriesAdapter;
import com.echessa.designdemo.R;
import com.google.gson.Gson;

import org.json.JSONArray;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    private ListView lvCategories;

    private List<Category> listCategory;

    private CustomCategoriesAdapter categoriesAdapter;

    String url ="https://cappuccino-hello.herokuapp.com/api/menu/category/";

    private ArrayList<String> listMenuOfCategory;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_categories, container, false);


        lvCategories = (ListView) view.findViewById(R.id.lvCategories);

        listCategory = new ArrayList<Category>();



        listMenuOfCategory = new ArrayList<String>();

            RequestQueue queue = Volley.newRequestQueue(view.getContext());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                try {

                    Gson gson = new Gson();

                    listCategory = gson.fromJson(response.toString(), ListCategory.class).getListCategory();

                    categoriesAdapter = new CustomCategoriesAdapter(getActivity(), listCategory);

                    lvCategories.setAdapter(categoriesAdapter);

                    lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {

                        MenuFragment menuFragment = new MenuFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("idCategory", listCategory.get(position).getId());
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

    private class ListCategory{
        private List<Category> response;

        public List<Category> getListCategory() {
            return response;
        }

        public void setListCategory(List<Category> listCategory) {
            this.response = listCategory;
        }
    }

    public void toast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
    }



}
