package com.echessa.designdemo.fragment;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.echessa.designdemo.Adapter.CustomMenuAdapter;
import com.echessa.designdemo.DBUtils.Menu;

import com.echessa.designdemo.DBUtils.Ordered;
import com.echessa.designdemo.MainActivity;
import com.echessa.designdemo.R;
import com.echessa.designdemo.ReceiptActivity;
import com.google.gson.Gson;

import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private GridView gvMenu;

    private List<Menu> listMenu;

    private CustomMenuAdapter customMenuAdapter;

    Button btnSeeReceipt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu,container,false);

        //setup font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


        gvMenu = (GridView) view.findViewById(R.id.gvMenu);

        listMenu = new ArrayList<Menu>();

        String checkFavoriteOrMenuOfCategory = getActivity().getIntent().getStringExtra("checkFavoriteOrMenuOfCategory");


        if(checkFavoriteOrMenuOfCategory.equals("1")){

            String urlMenuByFavorite = "https://cappuccino-hello.herokuapp.com/api/menu/favorite/";

            getMenu(view, urlMenuByFavorite);

            //set checkFavoriteOrMenuOfCategory again
            if(checkFavoriteOrMenuOfCategory.equals("1"))
                getActivity().getIntent().putExtra("checkFavoriteOrMenuOfCategory","0");

        }else if(checkFavoriteOrMenuOfCategory.equals("0")){
            Bundle bundle = getArguments();
            if(bundle!= null){
                String idCategory = bundle.getString("idCategory");
                String urlMenuByCategory ="https://cappuccino-hello.herokuapp.com/api/menu/category/"+idCategory;
                getMenu(view, urlMenuByCategory);

                //set checkFavoriteOrMenuOfCategory again
                getActivity().getIntent().putExtra("checkFavoriteOrMenuOfCategory","1");
            }
        }

        btnSeeReceipt = (Button) view.findViewById(R.id.btnSeeReceipt);

        btnSeeReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(view.getContext(), ReceiptActivity.class);
                intent.putExtra("getReceiptId",getActivity().getIntent().getStringExtra("getReceiptIdByTable"));
                startActivity(intent);
            }
        });



        return view;
    }



    public void getMenu(View view, String url){
        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    List<Ordered> listOrdered;
                    Bundle bundle =  getActivity().getIntent().getBundleExtra("listOrdered");
                    if(bundle == null){
                        listOrdered= new ArrayList<Ordered>();
                    }else{
                        listOrdered = (List<Ordered>)bundle.getSerializable("bundleListOrdered");
                    }

                    Gson gson = new Gson();
                    listMenu = gson.fromJson(response.toString(),ListMenu.class).getResponse();

                    for (Menu menu : listMenu){
                        for(Ordered ordered: listOrdered){
                            if(ordered.getItemId().equals(menu.getId())){
                                menu.setFavorite(ordered.getQuantity());
                                break;
                            }
                        }
                    }



                    customMenuAdapter = new CustomMenuAdapter(getActivity(),listMenu);

                    gvMenu.setAdapter(customMenuAdapter);

                    final Intent intent = getActivity().getIntent();

                    final String getReceiptIdByTable = intent.getStringExtra("getReceiptIdByTable");

                    gvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String urlCreatMenuItem = "https://cappuccino-hello.herokuapp.com/api/receipt/"+getReceiptIdByTable;

                        insertReceipt(urlCreatMenuItem,"menuItemId", listMenu.get(position).getId());

                        int quatityOrderNew = listMenu.get(position).getFavorite()+1;
                        listMenu.get(position).setFavorite(quatityOrderNew);

                        listMenu.set(position, listMenu.get(position));
                        customMenuAdapter.notifyDataSetChanged();
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

        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        queue.add(jsonObjectRequest);
    }



    public void insertReceipt(String url, final String key , final String value){
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("AAAError:",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(key,value);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private class ListMenu{
        private List<Menu> response;

        public List<Menu> getResponse() {
            return response;
        }

        public void setResponse(List<Menu> response) {
            this.response = response;
        }
    }

    public void toast(String msg){
        Toast.makeText(this.getActivity(),msg,Toast.LENGTH_LONG).show();
    }

}
