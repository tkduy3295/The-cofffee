package com.echessa.designdemo.fragment;


import android.content.Intent;
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
import com.echessa.designdemo.Adapter.CustomMenuAdapter;
import com.echessa.designdemo.DBUtils.Menu;

import com.echessa.designdemo.R;

import org.json.JSONArray;
import org.json.JSONException;
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

    private boolean checkFirstOrder = false;

    private String receiptID ="";

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

            String urlMenuByFavorite = "https://cappuccino-hello.herokuapp.com/api/menu/favorite";

            getMenu(view, urlMenuByFavorite);

            //set checkFavoriteOrMenuOfCategory again
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

        return view;
    }

    public void getMenu(View view, String url){
        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArrayMenu = response.getJSONArray("response");

                    for (int i = 0 ; i < jsonArrayMenu.length() ; i++){
                        JSONObject MenuItem = jsonArrayMenu.getJSONObject(i);

                        String id = MenuItem.getString("id");
                        String name = MenuItem.getString("name");
                        String desciption = "Tên description";
                        int price = MenuItem.getInt("price");
                        int createAt = MenuItem.getInt("createAt");
                        String urlImage = MenuItem.getString("urlImage");
                        int totalOrder = MenuItem.getInt("totalOrder");
                        int quatity = 0;

                        Menu menu = new Menu(id,name,desciption,price,createAt,urlImage,totalOrder,quatity);

                        listMenu.add(menu);

                    }


                    customMenuAdapter = new CustomMenuAdapter(getActivity(), listMenu);

                    gvMenu.setAdapter(customMenuAdapter);

                    final Intent intent = getActivity().getIntent();

                    final String idTable = intent.getStringExtra("idTable");
                    final String getReceiptId = intent.getStringExtra("getReceiptId");

                    gvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                                    insertReceipt(Request.Method.PUT,urlCreatMenuItem,"menuItemId", listMenu.get(position).getId(),false);
                                }

                            }

                            int quatityOrderNew = listMenu.get(position).getQuatity()+1;
                            listMenu.get(position).setQuatity(quatityOrderNew);

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
