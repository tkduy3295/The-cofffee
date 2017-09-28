package com.echessa.designdemo;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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


import com.echessa.designdemo.DBUtils.Position;
import com.echessa.designdemo.DBUtils.SizeTable;
import com.echessa.designdemo.DBUtils.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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


        new ReadJsonTable().execute("https://cappuccino-hello.herokuapp.com/api/table/");


    }



    private class ReadJsonTable extends AsyncTask<String,Void,String>{

        StringBuilder content = new StringBuilder();

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);

                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());

                BufferedReader bufferedReader =  new BufferedReader(inputStreamReader);

                String line="";
                while((line = bufferedReader.readLine())!= null){
                    content.append(line);
                }
                bufferedReader.close();



            } catch (Exception e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObjectTable = new JSONObject(s);

                tableList = new ArrayList<Table>();


                JSONArray response = jsonObjectTable.getJSONArray("response");


                for (int i = 0 ; i < response.length() ; i++){
                    JSONObject objectItem = response.getJSONObject(i);

                    String id = objectItem.getString("id");

                    String name = objectItem.getString("name");
                    String receiptId = objectItem.getString("receiptId");
                    String createAt = objectItem.getString("createAt");


                    JSONObject position = objectItem.getJSONObject("position");


                    int x = position.getInt("x");
                    int y = position.getInt("y");


                    JSONObject size = objectItem.getJSONObject("size");
                    int w = size.getInt("w");
                    int h = size.getInt("h");

                    Table table = new Table(id,name,receiptId,createAt,new Position(x,y),new SizeTable(w,h));


                    tableList.add(table);

                }
                for (int i = 0 ; i < tableList.size() ; i++){

                    root = (ViewGroup) findViewById(R.id.root);

                    creatTable = new Button(getBaseContext());

                    final Table getTable = tableList.get(i);

//                    creatTable.setId(Integer.parseInt(getTable.getId()));
                    creatTable.setText(getTable.getName());
                    creatTable.setTextColor(R.color.highlight);

                    int widthTable = getTable.getSizeTable().getWidth()*200;
                    int heightTable = getTable.getSizeTable().getHeight()*200;

                    int positionX = getTable.getPosition().getX()*200;
                    int positionY = getTable.getPosition().getY()*200;
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(widthTable,heightTable);
                    layoutParams.leftMargin = positionX;
                    layoutParams.topMargin = positionY;
//            layoutParams.bottomMargin = 0;
//            layoutParams.rightMargin = 0;

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
                            String statusTableCurent = getTable.getReceiptId();
                            Intent intent;
                            if(statusTableCurent.equals("")){
                                intent = new Intent(MainActivity.this, MenuTabsActivity.class);
                                startActivity(intent);
                            }else if(statusTableCurent.equals("1")){
                                intent = new Intent(MainActivity.this, PaymentActivity.class);
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

                                    /*layoutParams.rightMargin = -250;
                                    layoutParams.bottomMargin = -250;*/
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
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void toast(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
    }
}
