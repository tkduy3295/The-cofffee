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
import com.echessa.designdemo.DBUtils.Ordered;
import com.echessa.designdemo.DBUtils.Position;
import com.echessa.designdemo.DBUtils.SizeTable;
import com.echessa.designdemo.DBUtils.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
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

    String url = "https://cappuccino-hello.herokuapp.com/api/table/";

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

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("response");
                    for (int i=0;i<jsonArray.length();i++) {

                        JSONObject itemMenu = jsonArray.getJSONObject(i);

                        String id = itemMenu.getString("id");
                        String name = itemMenu.getString("name");
                        String receiptId = itemMenu.getString("receiptId");
                        String createAt = itemMenu.getString("createAt");


                        JSONObject position = itemMenu.getJSONObject("position");
                        int x = position.getInt("x");
                        int y = position.getInt("y");


                        JSONObject size = itemMenu.getJSONObject("size");
                        int w = size.getInt("w");
                        int h = size.getInt("h");

                        Table table = new Table(id, name, receiptId, createAt, new Position(x, y), new SizeTable(w, h));
                        tableList.add(table);
                    }

                    for (int j = 0 ; j < tableList.size() ; j++){

                        root = (ViewGroup) findViewById(R.id.root);

                        creatTable = new Button(getBaseContext());

                        final Table getTable = tableList.get(j);

//                          creatTable.setId(Integer.parseInt(getTable.getId()));
                        creatTable.setText(getTable.getName());
                        creatTable.setTextColor(R.color.highlight);

                        int widthTable = getTable.getSizeTable().getWidth()*200;
                        int heightTable = getTable.getSizeTable().getHeight()*200;

                        int positionX = getTable.getPosition().getX()*200;
                        int positionY = getTable.getPosition().getY()*200;
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(widthTable,heightTable);
                        layoutParams.leftMargin = positionX;
                        layoutParams.topMargin = positionY;
//                      layoutParams.bottomMargin = 0;
//                      layoutParams.rightMargin = 0;


                        if(getTable.getReceiptId().equals("")){
                            creatTable.setBackgroundResource(R.color.white);
                        }else if(getTable.getReceiptId().equals("1")){
                            creatTable.setBackgroundResource(R.color.primary);
                        }


                        creatTable.setLayoutParams(layoutParams);

                        root.addView(creatTable);


                        creatTable.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            Intent intent;
                            if(getTable.getReceiptId().equals("")){

                                intent = new Intent(MainActivity.this, MenuTabsActivity.class);

                                String idTable =getTable.getId();
                                String getReceiptId = getTable.getReceiptId();

                                intent.putExtra("idTable",idTable);
                                intent.putExtra("getReceiptId",getReceiptId);

                                startActivity(intent);
                                /*Bundle bundleTable = new Bundle();

                                String idTable =getTable.getId();
                                intent = new Intent(MainActivity.this,MenuTabsActivity.class);
                                intent.putExtra("idTable", idTable);
                                startActivity(intent);

                                String statusTable ="0";
                                List<Ordered> orderedListTable = new ArrayList<Ordered>();
                                orderedListTable.add(new Ordered("Ga",25000,1));


                                bundleTable.putString("idTable",idTable);
                                bundleTable .putString("statusTable",statusTable);
                                bundleTable.putSerializable("orderedListTable", (Serializable) orderedListTable);

                                intent = new Intent(MainActivity.this, MenuTabsActivity.class);

                                intent.putExtra("bundleTable",bundleTable);

                                startActivity(intent);*/
                            }else if(getTable.getReceiptId().equals("1")){

                            intent = new Intent(MainActivity.this, PaymentActivity.class);
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
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
