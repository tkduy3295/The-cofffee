package com.echessa.designdemo;


import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.echessa.designdemo.DBUtils.Table;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;



public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private ViewGroup root;

    private Button creatTable;


    private int _xDelta;
    private int _yDelta;


    String urlGetTable = "https://cappuccino-hello.herokuapp.com/api/table/";

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set Font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        // set Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getTable();

        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LogoActivity.class);
                startActivity(intent);

            }
        });



    }

    private void getTable(){

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, urlGetTable, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Gson gson = new Gson();
                    ListTable listTable = gson.fromJson(response.toString(),ListTable.class);

                    for (final Table table : listTable.getResponse()){

                        root = (ViewGroup) findViewById(R.id.root);

                        creatTable = new Button(getBaseContext());

                        creatTable.setText(table.getName());
                        creatTable.setTextColor(R.color.highlight);

                        int widthTable = 170;
                        int heightTable = 170;

                        int positionX = table.getPositionX()*150;
                        int positionY = table.getPositionY()*150;
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(widthTable,heightTable);
                        layoutParams.leftMargin = positionX;
                        layoutParams.topMargin = positionY;


                        if(table.getReceiptId().equals("")){
                            creatTable.setBackgroundResource(R.color.white);
                        }else{
                            creatTable.setBackgroundResource(R.color.primary);
                        }


                        creatTable.setLayoutParams(layoutParams);

                        root.addView(creatTable);

                        creatTable.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(table.getReceiptId().equals("")){
                                    String urlUpdateTable ="https://cappuccino-hello.herokuapp.com/api/table/"+table.getId()+"/receipt/";
                                    updateTable(urlUpdateTable);
                                }else{
                                    Intent intent = new Intent(MainActivity.this, ReceiptActivity.class);
                                    intent.putExtra("getReceiptId",table.getReceiptId());
                                    startActivity(intent);
                                }
                            }
                        });

                        /*creatTable.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {

                                final int X = (int) event.getRawX();
                                final int Y = (int) event.getRawY();


                                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                                    case MotionEvent.ACTION_DOWN:
                                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                                        _xDelta = X - lParams.leftMargin;
                                        _yDelta = Y - lParams.topMargin;


                                        break;
                                    case MotionEvent.ACTION_UP:
                                        break;
                                    case MotionEvent.ACTION_MOVE:
                                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                                        layoutParams.leftMargin = X - _xDelta;
                                        layoutParams.topMargin = Y - _yDelta;

                                        layoutParams.rightMargin = -250;
                                        layoutParams.bottomMargin = -250;
                                        v.setLayoutParams(layoutParams);

                                        break;
                                }
                                root.invalidate();
                                return false;
                            }
                        });*/

                    }

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

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void updateTable(String url){
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent intent = new Intent(MainActivity.this, MenuTabsActivity.class);

                try {

                    JSONObject jsonObject = response.getJSONObject("response");
                    String receiptId = jsonObject.getString("receiptId");

                    //checkFavorite = 1
                    //checkMenuOfCategory = 0;
                    String checkFavoriteOrMenuOfCategory = "1";

                    intent.putExtra("getReceiptIdByTable",receiptId);
                    intent.putExtra("checkFavoriteOrMenuOfCategory",checkFavoriteOrMenuOfCategory);

                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("AAA",error.toString());
            }
        });
        queue.add(jsonObjectRequest);

    }

    private class ListTable{
        private List<Table> response;

        public List<Table> getResponse() {
            return response;
        }

        public void setResponse(List<Table> response) {
            this.response = response;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void toast(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
    }
}
