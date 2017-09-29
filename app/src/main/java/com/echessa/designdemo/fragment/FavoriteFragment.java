package com.echessa.designdemo.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.echessa.designdemo.Adapter.CustomFavoriteAdapter;
import com.echessa.designdemo.DBUtils.Favorite;

import com.echessa.designdemo.DBUtils.Table;
import com.echessa.designdemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private GridView gvFavorite;

    private List<Favorite> favoriteList;

    private CustomFavoriteAdapter customFavoriteAdapter;

    private TextView tvQualityFavorite;

    private boolean checkFirstOrder = false;

    private String url = "https://cappuccino-hello.herokuapp.com/api/menu/favorite";

    private String receiptID ="";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_favorite,container,false);

        //setup font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


        gvFavorite = (GridView) view.findViewById(R.id.gvFavorite);

        favoriteList = new ArrayList<Favorite>();

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArrayFavorite = response.getJSONArray("response");

                    for (int i = 0 ; i < jsonArrayFavorite.length() ; i++){
                        JSONObject favoriteItem = jsonArrayFavorite.getJSONObject(i);

                        String id = favoriteItem.getString("id");
                        String name = favoriteItem.getString("name");
                        String desciption = "Tên description";
                        int price = favoriteItem.getInt("price");
                        int createAt = favoriteItem.getInt("createAt");
                        String urlImage = favoriteItem.getString("urlImage");
                        int totalOrder = favoriteItem.getInt("totalOrder");
                        int quatity = 0;

                        Favorite favorite = new Favorite(id,name,desciption,price,createAt,urlImage,totalOrder,quatity);

                        favoriteList.add(favorite);

                    }


                    customFavoriteAdapter = new CustomFavoriteAdapter(getActivity(), favoriteList);

                    gvFavorite.setAdapter(customFavoriteAdapter);

                    final Intent intent = getActivity().getIntent();

                    final String idTable = intent.getStringExtra("idTable");
                    final String getReceiptId = intent.getStringExtra("getReceiptId");

                    gvFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            if(getReceiptId.equals("")){
                                if(checkFirstOrder == false){

                                    checkFirstOrder = true;

                                    intent.putExtra("getReceiptId","1");


                                    String urlCreatReceipt = "https://cappuccino-hello.herokuapp.com/api/receipt/";

                                    insertReceipt(Request.Method.POST, urlCreatReceipt, "tableId" , idTable , true);



                                }else{
                                    String urlCreatMenuItem = "https://cappuccino-hello.herokuapp.com/api/receipt/"+receiptID;

                                    insertReceipt(Request.Method.PUT,urlCreatMenuItem,"menuItemId",favoriteList.get(position).getId(),false);
                                }

                            }

                            int quatityOrderNew = favoriteList.get(position).getQuatity()+1;
                            favoriteList.get(position).setQuatity(quatityOrderNew);

                            favoriteList.set(position,favoriteList.get(position));
                            customFavoriteAdapter.notifyDataSetChanged();



                        }


                    });

                    } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        queue.add(jsonObjectRequest);


        return view;
    }

    public void insertReceipt(int request, String url, final String key , final String value , final boolean CheckRetureReceiptID){
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(request, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(CheckRetureReceiptID == true){

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject object = jsonObject.getJSONObject("response");
                        receiptID = object.getString("receiptID");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                toast("Bạn đã thêm thành công !!!!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Error:",error.toString());
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

    public void toast(String msg){
        Toast.makeText(this.getActivity(),msg,Toast.LENGTH_LONG).show();
    }

}
