package com.echessa.designdemo;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
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
import com.echessa.designdemo.DBUtils.Position;
import com.echessa.designdemo.DBUtils.Table;

import org.json.JSONArray;
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

    private List<Table> tableList ;

    private List<String> listOrder = new ArrayList<String>();

    String urlGetTable = "https://cappuccino-hello.herokuapp.com/api/table/";

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

        tableList = new ArrayList<Table>();

        getTable();





    }

    private void getTable(){

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, urlGetTable, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray tables = response.getJSONArray("response");

                    for (int i=0;i<tables.length();i++) {

                        JSONObject table = tables.getJSONObject(i);

                        String id = table.getString("id");
                        String name = table.getString("name");
                        String receiptId = table.getString("receiptId");

                        int x = table.getInt("positionX");
                        int y = table.getInt("positionY");


                        Table gettable = new Table(id, name,x,y,receiptId);
                        tableList.add(gettable);
                    }
                    for (int j = 0 ; j < tableList.size() ; j++){

                        root = (ViewGroup) findViewById(R.id.root);

                        creatTable = new Button(getBaseContext());

                        final Table getTable = tableList.get(j);

                        creatTable.setText(getTable.getName());
                        creatTable.setTextColor(R.color.highlight);

                        int widthTable = 150;
                        int heightTable = 170;

                        int positionX = getTable.getPositionX()*150;
                        int positionY = getTable.getPositionY()*150;
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(widthTable,heightTable);
                        layoutParams.leftMargin = positionX;
                        layoutParams.topMargin = positionY;
//                      layoutParams.bottomMargin = 0;
//                      layoutParams.rightMargin = 0;


                        if(getTable.getReceiptId().equals("")){
                            creatTable.setBackgroundResource(R.color.white);
                        }else{
                            creatTable.setBackgroundResource(R.color.primary);
                        }


                        creatTable.setLayoutParams(layoutParams);

                        root.addView(creatTable);


                        creatTable.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(getTable.getReceiptId().equals("")){
                                    String urlUpdateTable ="https://cappuccino-hello.herokuapp.com/api/table/"+getTable.getId()+"/receipt/";
                                    updateTable(urlUpdateTable);
                                }else{
                                    Intent intent = new Intent(MainActivity.this, ReceiptActivity.class);
                                    intent.putExtra("getReceiptId",getTable.getReceiptId());
                                    startActivity(intent);
                                }
                            }
                        });

                        creatTable.setOnTouchListener(new View.OnTouchListener() {
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
                        });
                    }

                } catch (JSONException e) {
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void toast(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
    }
}
